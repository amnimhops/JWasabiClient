package com.mimolibros.wasabi.desktop.forms;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

import com.mimolibros.wasabi.client.entities.Materia;
import com.mimolibros.wasabi.services.ServicioLibreria;

public class MateriaEditor extends ModalBase<Materia> {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtAutor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			MateriaEditor dialog = new MateriaEditor(new Materia(1,"materia chunga"));
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			
			if(dialog.getStatus()==dialog.EXIT_OK){
				ServicioLibreria.nuevaMateria(dialog.getModel());
			}
			System.out.println(dialog.getModel().getNombre());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public MateriaEditor(Materia materia) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AutorEditor.class.getResource("/resources/icon/small/emblem-people.png")));
		setTitle("Materia");
		setBounds(100, 100, 425, 125);
		getContentPane().setLayout(new BorderLayout());
		this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(this.contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[][grow]", "[]"));
		{
			JLabel lblNombre = new JLabel("Nombre");
			contentPanel.add(lblNombre, "cell 0 0,alignx trailing");
		}
		{
			this.txtAutor = new JTextField();
			this.txtAutor.setText("txAutor");
			contentPanel.add(this.txtAutor, "cell 1 0,growx");
			this.txtAutor.setColumns(10);
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
				okButton.setIcon(new ImageIcon(AutorEditor.class.getResource("/resources/icon/small/ok.png")));
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						cancel();
					}
				});
				cancelButton.setIcon(new ImageIcon(AutorEditor.class.getResource("/resources/icon/small/gtk-quit.png")));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		this.model=materia;
		fillForm();
	}

	@Override
	public void fillForm() {
		this.txtAutor.setText(model.getNombre());
		
	}

	@Override
	public void fillModel() {
		this.model.setNombre(this.txtAutor.getText());
	}
	
	public void ok(){
		try{
			super.ok();
		}catch(FillModelException e){
			JOptionPane.showMessageDialog(this, "Error en los datos");
		}
	}

}
