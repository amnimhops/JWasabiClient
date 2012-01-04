package com.mimolibros.wasabi.desktop.forms;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mimolibros.wasabi.client.entities.Autor;
import com.mimolibros.wasabi.client.entities.Ejemplar;
import com.mimolibros.wasabi.client.entities.Materia;
import com.mimolibros.wasabi.client.entities.ParametrosBusquedaEjemplar;
import com.mimolibros.wasabi.services.ServicioLibreria;
import com.mimolibros.wasabi.util.Util;

import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JTabbedPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.AbstractListModel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JEditorPane;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.ScrollPaneConstants;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EjemplarEditor extends ModalBase<Ejemplar> {
	private final JPanel panel = new JPanel();
	private final JLabel lblReferencia = new JLabel("Referencia");
	private final JLabel lblTtulo = new JLabel("Título");
	private final JTextField txTitulo = new JTextField();
	private final JLabel lblAutor = new JLabel("Autores");
	private final JLabel lblNewLabel = new JLabel("Materias");
	private final JTextField txReferencia = new JTextField();
	private final JLabel lbAutor = new JLabel("<html><b>autor1</b>, autor2, autor3, autor4, autor5,autor6,autor6,autor8,autor9,autor10</html>");
	private final JLabel lbMateria = new JLabel("<html>materia1, materia2, materia3, materia4</html>");
	private final JLabel lblPrecio = new JLabel("Precio");
	private final JLabel lblDescripcion = new JLabel("Descripcion");
	private final JScrollPane scrollPane = new JScrollPane();
	private final JEditorPane txDescripcion = new JEditorPane();
	private final JTextField txPrecio = new JTextField();
	private final JLabel lblPublicado = new JLabel("Editorial");
	private final JTextField txEditorial = new JTextField();
	private final JLabel lblEdicin = new JLabel("Edición");
	private final JLabel lblPublicado_1 = new JLabel("Publicado");
	private final JTextField txEdicion = new JTextField();
	private final JTextField txPublicado = new JTextField();
	private final JLabel lblInformacionAdicional = new JLabel("informacion adicional");

	private List<Autor> listaAutores;
	private List<Materia> listaMaterias;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			EjemplarEditor dialog = new EjemplarEditor(ServicioLibreria.buscarEjemplarPorId(1));
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public EjemplarEditor(Ejemplar ejemplar) {
		txReferencia.setColumns(10);
		txTitulo.setColumns(10);
		setTitle("EjemplarEditor");
		setBounds(100, 100, 753, 512);
		getContentPane().setLayout(new BorderLayout());
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
				okButton.setIcon(new ImageIcon(EjemplarEditor.class.getResource("/resources/icon/small/ok.png")));
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
				cancelButton.setIcon(new ImageIcon(EjemplarEditor.class.getResource("/resources/icon/small/gtk-quit.png")));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new MigLayout("", "[][150px:n][grow][100px:n][150px:n]", "[][][][][][][][][grow]"));
		
		panel.add(lblReferencia, "cell 0 0,alignx left");
		
		panel.add(txReferencia, "cell 1 0,growx");
		
		panel.add(lblInformacionAdicional, "cell 2 0");
		
		panel.add(lblTtulo, "cell 0 1");
		
		panel.add(txTitulo, "cell 1 1 4 1,growx");
		
		panel.add(lblAutor, "cell 0 2,aligny top");
		lbAutor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				cambiarAutores();
			}
		});
		lbAutor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lbAutor.setToolTipText("Click para cambiar");
		
		panel.add(lbAutor, "cell 1 2 4 1,growy");
		
		panel.add(lblNewLabel, "cell 0 3,aligny top");
		lbMateria.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				cambiarMaterias();
			}
		});
		lbMateria.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lbMateria.setToolTipText("Click para cambiar");
		
		panel.add(lbMateria, "cell 1 3 4 1");
		
		panel.add(lblDescripcion, "cell 0 4,aligny top");
		
		panel.add(lblPrecio, "cell 3 4,alignx left,aligny top");
		txPrecio.setColumns(10);
		
		panel.add(txPrecio, "cell 4 4,growx");
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		panel.add(scrollPane, "cell 1 4 2 5,grow");
		scrollPane.setViewportView(txDescripcion);
		
		panel.add(lblPublicado, "cell 3 5,alignx left");
		txEditorial.setColumns(10);
		
		panel.add(txEditorial, "cell 4 5,growx");
		
		panel.add(lblEdicin, "cell 3 6,alignx left");
		txEdicion.setColumns(10);
		
		panel.add(txEdicion, "cell 4 6,growx");
		
		panel.add(lblPublicado_1, "cell 3 7,alignx left");
		txPublicado.setColumns(10);
		
		panel.add(txPublicado, "cell 4 7,growx");
		
		this.model=ejemplar;
		
		this.listaAutores=new ArrayList<Autor>();
		this.listaMaterias=new ArrayList<Materia>();
		
		this.fillForm();
		this.refrescarAutoresMaterias();
	}

	@Override
	public void fillForm() {
		Calendar cal = Calendar.getInstance();
		listaAutores=new ArrayList<Autor>();
		
		if(model.getId()!=null){
			lblInformacionAdicional.setText("ID "+model.getId());
		}else{
			lblInformacionAdicional.setText("ID no asignada");
		}
		if(model.getFechaAlta()!=null){
			lblInformacionAdicional.setText(lblInformacionAdicional.getText()+", creado el " +Util.dateToString(model.getFechaAlta()));
		}
		if(model.getFechaModificacion()!=null){
			lblInformacionAdicional.setText(lblInformacionAdicional.getText()+", modificado el" +Util.dateToString(model.getFechaModificacion()));
		}
		
		if(model.getFechaPublicacion()!=null){
			cal.setTime(model.getFechaPublicacion());
			txPublicado.setText(String.valueOf(cal.get(Calendar.YEAR)));
		}
		
		txReferencia.setText(model.getReferencia());
		txEdicion.setText(model.getEdicion());
		txEditorial.setText(model.getEditorial());
		txPrecio.setText(String.valueOf(model.getPrecio()));
		txDescripcion.setText(model.getDescripcion());
		
		txTitulo.setText(model.getTitulo());
		
		for(Autor item:model.getAutores()){
			listaAutores.add(item);
		}
		
		for(Materia item:model.getMaterias()){
			listaMaterias.add(item);
		}
	}

	@Override
	public void fillModel() throws FillModelException{
		try{
			model.setAutores(listaAutores);
			model.setDescripcion(txDescripcion.getText());
			model.setEdicion(txEdicion.getText());
			model.setEditorial(txEditorial.getText());
			//model.setFechaAlta()
			model.setFechaModificacion(new Date());
			model.setFechaPublicacion(Util.stringToDate(txPublicado+"-01-01"));
			model.setObservaciones("Sin observaciones por el momento");
			model.setPrecio(Float.parseFloat(txPrecio.getText()));
			model.setReferencia(txReferencia.getText());
			model.setTitulo(txTitulo.getText());
		}catch(Exception ex){
			throw new FillModelException(ex.getMessage());
		}
	}
	
	public void ok(){
		
		try {
			super.ok();
		} catch (FillModelException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(this, "Error:"+e.getMessage());
		}
		
	}

	private void refrescarAutoresMaterias(){
		lbAutor.setText("<html>%s</html>");
		
		StringBuffer sb = new StringBuffer();
		
		if(listaAutores.size()>0){
			sb.append("<span style='color:blue;font-weight:bold;'>"+listaAutores.get(0)+"</span>");
			for(int c=1;c<listaAutores.size();c++){
				sb.append(", <span>"+listaAutores.get(c)+"</span>");
			}
		}else{
			sb.append("Ningún autor seleccionado");
		}
		
		lbAutor.setText(lbAutor.getText().replace("%s", sb.toString()));
		
		lbMateria.setText("<html>%s</html>");
		
		sb = new StringBuffer();
		
		if(listaMaterias.size()>0){
			sb.append("<span style='color:blue;font-weight:bold;'>"+listaMaterias.get(0)+"</span>");
			for(int c=1;c<listaMaterias.size();c++){
				sb.append(", <span>"+listaMaterias.get(c)+"</span>");
			}
		}else{
			sb.append("Ninguna materia seleccionada");
		}
		
		lbMateria.setText(lbMateria.getText().replace("%s", sb.toString()));
		
	}
	private void cambiarAutores(){
		GenericListEditor<Autor> editor = new GenericListEditor<Autor>(new AutorSelector(),model.getAutores());
		editor.setVisible(true);
		
		if(editor.getStatus()==GenericListEditor.EXIT_OK){
			listaAutores = editor.getModel();
			refrescarAutoresMaterias();
		}
	}
	private void cambiarMaterias(){
		GenericListEditor<Materia> editor = new GenericListEditor<Materia>(new MateriaSelector(),model.getMaterias());
		editor.setVisible(true);
		
		if(editor.getStatus()==GenericListEditor.EXIT_OK){
			listaMaterias = editor.getModel();
			refrescarAutoresMaterias();
		}
	}
}
