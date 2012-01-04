package com.mimolibros.wasabi.desktop.forms;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.KeyStroke;

public abstract class ModalBase<T> extends JDialog{
	public final static int EXIT_OK=0;
	public final static int EXIT_CANCEL=1;
	
	T model;
	int status;
	boolean modified;

	public ModalBase(){
		super((JDialog)null,"hola",true);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				cancel();
			}
		});

		// Si se pulsa escape en cualquier parte, salimos
		ActionListener escapeListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Escape?");
				cancel();
			}
		};
		
		this.getRootPane().registerKeyboardAction(escapeListener,KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0),JComponent.WHEN_IN_FOCUSED_WINDOW);

	}
	public T getModel() {
		return model;
	}
	public void setModel(T model) {
		this.model = model;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public boolean isModified() {
		return modified;
	}
	public void setModified(boolean modified) {
		this.modified = modified;
	}
	
	public void ok() throws FillModelException{
		fillModel();
		this.status=EXIT_OK;
		this.dispose();
	}
	
	public void cancel(){
		this.status=EXIT_CANCEL;
		this.dispose();
	}
	
	public abstract void fillForm();
	public abstract void fillModel() throws FillModelException;

	
}
