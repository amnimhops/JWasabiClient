package com.mimolibros.wasabi.desktop.forms;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mimolibros.wasabi.client.entities.Autor;
import com.mimolibros.wasabi.client.entities.Materia;
import com.mimolibros.wasabi.services.ServicioLibreria;

import javax.swing.BoxLayout;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.EtchedBorder;
import javax.swing.ImageIcon;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public abstract class BuscadorGenerico<T> extends ModalBase<T> {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtKey;
	private JList lstResultados;
	private List<T> resultados;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		/*try {
			BuscadorGenerico<Autor> dialog = new BuscadorGenerico<Autor>(){

				@Override
				public List<Autor> search(String key) {
					return ServicioLibreria.buscarAutoresPorNombre(key);
				}
				
			};
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			
			if(dialog.getStatus()==dialog.EXIT_OK){
				System.out.println("Se ha seleccionado "+dialog.getModel().getNombre());
			}else{
				System.out.println("Se ha pulsado cancel");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		try {
			BuscadorGenerico<Materia> dialog = new BuscadorGenerico<Materia>(){

				@Override
				public List<Materia> search(String key) {
					return ServicioLibreria.buscarMateriasPorNombre(key);
				}
				
			};
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			
			if(dialog.getStatus()==dialog.EXIT_OK){
				System.out.println("Se ha seleccionado "+dialog.getModel().getNombre());
			}else{
				System.out.println("Se ha pulsado cancel");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public BuscadorGenerico() {
		setBounds(100, 100, 521, 317);
		getContentPane().setLayout(new BorderLayout());
		
		this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(this.contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[][grow]", "[][grow][]"));
		{
			JLabel lblNombre = new JLabel("Nombre");
			contentPanel.add(lblNombre, "cell 0 0,alignx left");
		}
		{
			this.txtKey = new JTextField();
			this.txtKey.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode()==KeyEvent.VK_ENTER){
						resultados = search(txtKey.getText());
						fillForm();
					}
				}
			});
			contentPanel.add(this.txtKey, "cell 1 0,growx");
			this.txtKey.setColumns(10);
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
						if(arg0.getClickCount()==2){
							ok();
						}
					}
				});
				scrollPane.setViewportView(this.lstResultados);
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
				okButton.setIcon(new ImageIcon(BuscadorGenerico.class.getResource("/resources/icon/small/ok.png")));
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						cancel();
					}
				});
				cancelButton.setIcon(new ImageIcon(BuscadorGenerico.class.getResource("/resources/icon/small/gtk-quit.png")));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	public abstract List<T> search(String key);
	
	@Override
	public void fillForm() {
		if(this.resultados!=null){
			DefaultListModel dlm = new DefaultListModel();
			for(T item:resultados){
				dlm.addElement(item);
			}
			lstResultados.setSelectedIndex(0);
			lstResultados.setModel(dlm);
		}
		
	}

	@Override
	public void fillModel() throws FillModelException {
		if(lstResultados.getSelectedIndex()>=0){
			this.model=(T)lstResultados.getSelectedValue();
		}else{
			throw new FillModelException("No se ha seleccionado ningun elemento");
		}
	}
	
	@Override
	public void ok(){
		try{
			super.ok();
		}catch(FillModelException e){
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
	}

}
