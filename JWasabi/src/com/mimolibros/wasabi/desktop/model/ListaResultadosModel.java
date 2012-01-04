package com.mimolibros.wasabi.desktop.model;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

public abstract class ListaResultadosModel{
	DefaultTableModel data;
	DefaultTableCellRenderer renderer;
	
	public abstract void onClick();
	public abstract void onDblClick();
	public abstract void onNextPage();
	public abstract void onPrevPage();
	
	public TableModel getModel(){
		return data;
	}
	
	
}
