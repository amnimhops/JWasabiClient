package com.mimolibros.wasabi.desktop.forms;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JToolBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.mimolibros.wasabi.client.entities.Ejemplar;
import com.mimolibros.wasabi.client.entities.CriterioBusquedaEjemplar;
import com.mimolibros.wasabi.client.entities.GrupoCriteriosBusquedaEjemplar;
import com.mimolibros.wasabi.client.entities.ParametrosBusquedaEjemplar;
import com.mimolibros.wasabi.client.request.ResultadoBusquedaEjemplares;
import com.mimolibros.wasabi.services.ServicioLibreria;

public class ListaEjemplares extends JInternalFrame {
	private final JPanel panel = new JPanel();
	private final JToolBar toolBar = new JToolBar();
	private final JScrollPane scrollPane = new JScrollPane();
	private final JTable table = new JTable();

	private DefaultTableModel tableModel;
	private ResultadoBusquedaEjemplares resultados;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ParametrosBusquedaEjemplar param = new ParametrosBusquedaEjemplar();
					param.setGrupos(new ArrayList<GrupoCriteriosBusquedaEjemplar>());
					param.getGrupos().add(new GrupoCriteriosBusquedaEjemplar());
					param.getGrupos().get(0).setCriterios(new ArrayList<CriterioBusquedaEjemplar>());
					param.getGrupos().get(0).getCriterios().add(new CriterioBusquedaEjemplar("referencia","%","contiene",false));
					param.setPagina(0);
					param.setResultadosPorPagina(2);
					ResultadoBusquedaEjemplares resultado = ServicioLibreria.buscarEjemplares(param);
					
					ListaEjemplares frame = new ListaEjemplares(resultado);
					
					JDialog dlg= new JDialog();
					JDesktopPane pane = new JDesktopPane();
					dlg.getContentPane().add(pane);
					pane.add(frame);
					
					frame.setVisible(true);
					dlg.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ListaEjemplares(ResultadoBusquedaEjemplares resultados) {
		setResizable(true);
		setMaximizable(true);
		setIconifiable(true);

		setBounds(100, 100, 450, 300);
		
		getContentPane().add(panel, BorderLayout.SOUTH);
		
		getContentPane().add(toolBar, BorderLayout.WEST);
		
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		scrollPane.setViewportView(table);

		
		this.resultados=resultados;
		
		refresh();
	}
	
	private void refresh(){
		tableModel=new DefaultTableModel(
				new String[]{"Referencia","Título","Autor","Precio"},0
		);
		System.out.println(tableModel.getRowCount());
		for(Ejemplar e : resultados.getEjemplares()){
			tableModel.addRow(
					new String[]{
						e.getReferencia(),
						e.getTitulo(),
						e.getAutores().get(0).toString(),
						String.valueOf(e.getPrecio())
			});
		}
		table.setModel(tableModel);		
		table.setAutoCreateRowSorter(true);
	}

}
