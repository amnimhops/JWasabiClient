package com.mimolibros.wasabi.desktop.forms;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mimolibros.wasabi.client.entities.Autor;
import com.mimolibros.wasabi.services.ServicioLibreria;

import net.miginfocom.swing.MigLayout;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JList;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;

public class AutorSelector extends ModalBase<Autor> {
	private DefaultListModel dlm;
	private JList lstResultados;
	private final JPanel contentPanel = new JPanel();
	private JTextField txNombre;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AutorSelector dialog = new AutorSelector();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			System.out.println(dialog.getModel().getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AutorSelector() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AutorSelector.class.getResource("/resources/icon/small/emblem-people.png")));
		setTitle("Buscar autor");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(this.contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[][grow]", "[][grow]"));
		{
			JLabel lblNombre = new JLabel("Nombre");
			contentPanel.add(lblNombre, "cell 0 0,alignx left");
		}
		{
			this.txNombre = new JTextField();
			this.txNombre.setVerifyInputWhenFocusTarget(false);
			this.txNombre.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent arg0) {
					if(arg0.getKeyCode()==KeyEvent.VK_ENTER){
						buscarAutor(txNombre.getText());
					}
				}
			});
			contentPanel.add(this.txNombre, "cell 1 0,growx");
			this.txNombre.setColumns(10);
		}
		{
			JLabel lblResultados = new JLabel("Resultados");
			contentPanel.add(lblResultados, "cell 0 1,alignx left,aligny top");
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, "cell 1 1,grow");
			{
				lstResultados = new JList();
				this.lstResultados.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						System.out.println(arg0.getClickCount());
						if(arg0.getClickCount()==2){
							ok();
						}
					}
				});
				scrollPane.setViewportView(lstResultados);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						ok();
					}
				});
				{
					JButton btnNewButton = new JButton("Nuevo");
					btnNewButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							nuevoAutor();
						}
					});
					btnNewButton.setIcon(new ImageIcon(AutorSelector.class.getResource("/resources/icon/small/document-new.png")));
					btnNewButton.setHorizontalAlignment(SwingConstants.LEFT);
					buttonPane.add(btnNewButton);
				}
				okButton.setIcon(new ImageIcon(AutorSelector.class.getResource("/resources/icon/small/ok.png")));
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				//getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						cancel();
					}
				});
				cancelButton.setIcon(new ImageIcon(AutorSelector.class.getResource("/resources/icon/small/gtk-quit.png")));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		this.dlm=new DefaultListModel();
		this.lstResultados.setModel(dlm);
		this.model=null;
	}

	@Override
	public void fillForm() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fillModel() throws FillModelException {
	}

	public void ok(){
		if(lstResultados.isSelectionEmpty()){
			JOptionPane.showMessageDialog(this, "No se ha seleccionado ningún elemento");
		}else{
			this.model=(Autor) dlm.get(lstResultados.getSelectedIndex());
			try {
				super.ok();
			} catch (FillModelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private void buscarAutor(String nombre){
		List<Autor> resultados = ServicioLibreria.buscarAutoresPorNombre(nombre+"%");
		
		if(resultados!=null && resultados.size()>0){
			dlm.clear();
			for(Autor element:resultados){
				dlm.addElement(element);
			}
			
		}else{
			JOptionPane.showMessageDialog(this, "No se han encontrado resultados");
		}
	}
	private void nuevoAutor(){
		AutorEditor editor = new AutorEditor(new Autor());
		editor.setVisible(true);
		
		if(editor.getStatus()==ModalBase.EXIT_OK){
			Autor nuevo = ServicioLibreria.nuevoAutor(editor.getModel());
			dlm.clear();
			dlm.addElement(nuevo);
		}
	}
}
