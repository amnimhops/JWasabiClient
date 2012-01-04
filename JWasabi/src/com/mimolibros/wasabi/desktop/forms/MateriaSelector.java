package com.mimolibros.wasabi.desktop.forms;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.mimolibros.wasabi.client.entities.Materia;
import com.mimolibros.wasabi.services.ServicioLibreria;

import net.miginfocom.swing.MigLayout;

public class MateriaSelector extends ModalBase<Materia> {
	private DefaultListModel dlm;
	private JList lstResultados;
	private final JPanel contentPanel = new JPanel();
	private JTextField txNombre;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			MateriaSelector dialog = new MateriaSelector();
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
	public MateriaSelector() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(MateriaSelector.class.getResource("/resources/icon/small/subject.png")));
		setTitle("Buscar materia");
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
						buscarMateria(txNombre.getText());
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
					JButton btnNewButton = new JButton("Nueva");
					btnNewButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							nuevoMateria();
						}
					});
					btnNewButton.setIcon(new ImageIcon(MateriaSelector.class.getResource("/resources/icon/small/document-new.png")));
					btnNewButton.setHorizontalAlignment(SwingConstants.LEFT);
					buttonPane.add(btnNewButton);
				}
				okButton.setIcon(new ImageIcon(MateriaSelector.class.getResource("/resources/icon/small/ok.png")));
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
				cancelButton.setIcon(new ImageIcon(MateriaSelector.class.getResource("/resources/icon/small/gtk-quit.png")));
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
			this.model=(Materia) dlm.get(lstResultados.getSelectedIndex());
			try {
				super.ok();
			} catch (FillModelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private void buscarMateria(String nombre){
		List<Materia> resultados = ServicioLibreria.buscarMateriasPorNombre(nombre+"%");
		
		if(resultados!=null && resultados.size()>0){
			dlm.clear();
			for(Materia element:resultados){
				dlm.addElement(element);
			}
			
		}else{
			JOptionPane.showMessageDialog(this, "No se han encontrado resultados");
		}
	}
	private void nuevoMateria(){
		MateriaEditor editor = new MateriaEditor(new Materia());
		editor.setVisible(true);
		
		if(editor.getStatus()==ModalBase.EXIT_OK){
			Materia nueva = ServicioLibreria.nuevaMateria(editor.getModel());
			dlm.clear();
			dlm.addElement(nueva);
		}
	}
}
