package com.mimolibros.wasabi.desktop.forms;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mimolibros.wasabi.client.entities.Autor;
import net.miginfocom.swing.MigLayout;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GenericListEditor<T> extends ModalBase<List<T>> {
	private DefaultListModel dlm;
	private JList lstElements;
	private ModalBase<T> selector;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			List<Autor> lst = new ArrayList<Autor>();
			
			
			GenericListEditor dialog = new GenericListEditor<Autor>(new AutorSelector(), lst);
			
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public GenericListEditor(ModalBase<T> selector, List<T> model) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(GenericListEditor.class.getResource("/resources/icon/small/emblem-people.png")));
		setTitle("Lista de autores");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(this.contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[][grow][]", "[][][][][][grow]"));
		{
			JLabel lblAutoresAsignados = new JLabel("Autores asignados");
			contentPanel.add(lblAutoresAsignados, "cell 0 0");
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, "cell 0 1 2 5,grow");
			{
				lstElements = new JList();
				scrollPane.setViewportView(lstElements);
			}
		}
		{
			JButton bAdd = new JButton("Añadir");
			bAdd.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					buscar();
				}
			});
			bAdd.setHorizontalAlignment(SwingConstants.LEFT);
			bAdd.setIcon(new ImageIcon(GenericListEditor.class.getResource("/resources/icon/small/add.png")));
			contentPanel.add(bAdd, "cell 2 2,growx");
		}
		{
			JButton bRemove = new JButton("Quitar");
			bRemove.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					removeSelectedElement();
				}
			});
			bRemove.setHorizontalAlignment(SwingConstants.LEFT);
			bRemove.setIcon(new ImageIcon(GenericListEditor.class.getResource("/resources/icon/small/gtk-quit.png")));
			contentPanel.add(bRemove, "cell 2 3,growx");
		}
		{
			JButton bUp = new JButton("Subir");
			bUp.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					moveUpSelection();
				}
			});
			bUp.setHorizontalAlignment(SwingConstants.LEFT);
			bUp.setIcon(new ImageIcon(GenericListEditor.class.getResource("/resources/icon/small/up.png")));
			contentPanel.add(bUp, "cell 2 4,growx");
		}
		{
			JButton bDown = new JButton("Bajar");
			bDown.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					moveDownSelection();
				}
			});
			bDown.setHorizontalAlignment(SwingConstants.LEFT);
			bDown.setIcon(new ImageIcon(GenericListEditor.class.getResource("/resources/icon/small/down.png")));
			contentPanel.add(bDown, "cell 2 5,growx,aligny top");
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Aceptar");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						ok();
					}
				});
				okButton.setIcon(new ImageIcon(GenericListEditor.class.getResource("/resources/icon/small/ok.png")));
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						cancel();
					}
				});
				cancelButton.setIcon(new ImageIcon(GenericListEditor.class.getResource("/resources/icon/small/gtk-quit.png")));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		setModel(model);
		fillForm();
		
		this.selector = selector; 

	}

	@Override
	public void fillForm() {
		dlm = new DefaultListModel();
		
		for(T element:model){
			dlm.addElement(element);
		}
		
		lstElements.setModel(dlm);
		
	}

	@Override
	public void fillModel() throws FillModelException {
		model.clear();
		
		for(int c=0;c<dlm.getSize();c++){
			model.add((T) dlm.get(c));
		}
		
	}
	
	public void ok(){
		try{
			super.ok();
		}catch(FillModelException e){
			JOptionPane.showMessageDialog(this, "Error en los datos");
		}
	}
	
	private void removeSelectedElement(){
		if(lstElements.getSelectedIndex()>-1){
			while(lstElements.getSelectedIndex()>-1){
				dlm.remove(lstElements.getSelectedIndex());
			}
		}else{
			JOptionPane.showMessageDialog(this, "No hay nada seleccionado");
		}
	}
	
	private void moveUpSelection(){
		if(lstElements.isSelectionEmpty()){
			JOptionPane.showMessageDialog(this, "No hay nada seleccionado");
		}else{
			/*
			 * Se crea una matriz y se meten todos los elementos seleccionados
			 * Se busca el indice anterior al primer indice seleccionado
			 * Se eliminan los elementos seleccionados
			 * La nueva matriz se inserta antes del indice indicado anteriormente 
			 */
			int[] indexes = lstElements.getSelectedIndices();
			int previousIndex = indexes[0]-1;
			// Si el previo era el indice 0, lo dejamos como está
			if(previousIndex<0) previousIndex=0;
			
			List<T> lst = new ArrayList<T>();
			
			for(int index:indexes){
				lst.add((T) dlm.get(index));
			}
			
			removeSelectedElement();
			
			for(T element:lst){
				dlm.add(previousIndex++,element);
			}
			
			// Volvemos a seleccionar el elemento
			lstElements.setSelectedIndex(previousIndex-1);
			
		}
	}
	
	private void moveDownSelection(){
		if(lstElements.isSelectionEmpty()){
			JOptionPane.showMessageDialog(this, "No hay nada seleccionado");
		}else{
			/*
			 * Se crea una matriz y se meten todos los elementos seleccionados
			 * Se busca el indice siguiente al ultimo indice seleccionado
			 * Se eliminan los elementos seleccionados
			 * La nueva matriz se inserta despues del indice indicado anteriormente 
			 */
			int[] indexes = lstElements.getSelectedIndices();
			int nextIndex = indexes[indexes.length-1]+1;
			// Si el previo era el ultimo, lo dejamos como está
			if(nextIndex>=dlm.getSize()) nextIndex=indexes[indexes.length-1];
			List<T> lst = new ArrayList<T>();
			
			for(int index:indexes){
				lst.add((T) dlm.get(index));
			}
			
			for(T element:lst){
				dlm.add(++nextIndex,element);
			}
			
			removeSelectedElement();
			// Volvemos a seleccionar el elemento
			lstElements.setSelectedIndex(nextIndex-1);
			
		}
	}
	
	private void buscar(){
		selector.setVisible(true);
		
		if(selector.getStatus()==AutorSelector.EXIT_OK){
			// Existe en la lista?
			for(int c=0;c<dlm.getSize();c++){
				if(((T)dlm.get(c)).equals(selector.getModel())){
					JOptionPane.showMessageDialog(this, "El elemento ya está en la lista");
					return;
				}
			}
			dlm.addElement(selector.getModel());
			lstElements.setSelectedIndex(dlm.getSize()-1);
		}
	}
}
