package com.mimolibros.wasabi.desktop.forms;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import net.miginfocom.swing.MigLayout;
import javax.swing.JToolBar;
import javax.swing.JInternalFrame;
import javax.swing.JDesktopPane;
import javax.swing.JButton;
import java.awt.Toolkit;
import java.awt.SystemColor;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import com.mimolibros.wasabi.client.entities.Ejemplar;
import com.mimolibros.wasabi.client.entities.ParametrosBusquedaEjemplar;
import com.mimolibros.wasabi.client.request.ResultadoBusquedaEjemplares;
import com.mimolibros.wasabi.services.ServicioLibreria;

import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class MainWindow {

	private JFrame frmWasabi;
	private final JMenuBar menuBar = new JMenuBar();
	private final JMenu mnLibrera = new JMenu("Librería");
	private final JPanel panel = new JPanel();
	private final JDesktopPane desktopPane = new JDesktopPane();
	private final JProgressBar progressBar = new JProgressBar();
	private final JMenu mnNuevo = new JMenu("Nuevo");
	private final JMenuItem mntmEjemplar = new JMenuItem("Ejemplar");
	private final JMenuItem mntmAutor = new JMenuItem("Autor");
	private final JMenuItem mntmMateria = new JMenuItem("Materia");
	private final JMenuItem mntmAbrirEjemplar = new JMenuItem("Abrir ejemplar");
	private final JMenuItem mntmBuscarEjemplar = new JMenuItem("Buscar ejemplar...");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName());
					MainWindow window = new MainWindow();
					window.frmWasabi.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.frmWasabi = new JFrame();
		this.frmWasabi.setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/resources/icon/wasabi.png")));
		this.frmWasabi.setTitle("WaSaBI");
		this.frmWasabi.setBounds(100, 100, 561, 300);
		this.frmWasabi.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.frmWasabi.setJMenuBar(menuBar);
		
		menuBar.add(mnLibrera);
		mnNuevo.setIcon(new ImageIcon(MainWindow.class.getResource("/resources/icon/small/document-new.png")));
		
		mnLibrera.add(mnNuevo);
		mntmEjemplar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				nuevoEjemplar();
			}
		});
		mntmEjemplar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
		mntmEjemplar.setIcon(new ImageIcon(MainWindow.class.getResource("/resources/icon/small/text-editor.png")));
		
		mnNuevo.add(mntmEjemplar);
		mntmAutor.setIcon(new ImageIcon(MainWindow.class.getResource("/resources/icon/small/emblem-people.png")));
		
		mnNuevo.add(mntmAutor);
		mntmMateria.setIcon(new ImageIcon(MainWindow.class.getResource("/resources/icon/small/archive.png")));
		
		mnNuevo.add(mntmMateria);
		mntmAbrirEjemplar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirEjemplar();
			}
		});
		mntmAbrirEjemplar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
		mntmAbrirEjemplar.setIcon(new ImageIcon(MainWindow.class.getResource("/resources/icon/small/search.png")));
		
		mnLibrera.add(mntmAbrirEjemplar);
		mntmBuscarEjemplar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ParametrosBusquedaEjemplarEditor editor = new ParametrosBusquedaEjemplarEditor(new ParametrosBusquedaEjemplar());
				editor.setVisible(true);
				
				if(editor.getStatus()==editor.EXIT_OK){
					ParametrosBusquedaEjemplar param = editor.getModel();
					
					ResultadoBusquedaEjemplares result = ServicioLibreria.buscarEjemplares(param);
					
					abrirResultadosBusquedaEjemplares(result);
					
				}
			}
		});
		mntmBuscarEjemplar.setIcon(new ImageIcon(MainWindow.class.getResource("/resources/icon/small/search.png")));
		mntmBuscarEjemplar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));
		
		mnLibrera.add(mntmBuscarEjemplar);
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		
		this.frmWasabi.getContentPane().add(panel, BorderLayout.SOUTH);
		
		panel.add(progressBar);
		desktopPane.setBackground(SystemColor.windowBorder);
		
		this.frmWasabi.getContentPane().add(desktopPane, BorderLayout.CENTER);
	}
	
	
	public void nuevoEjemplar(){
		EjemplarEditor editor = new EjemplarEditor(new Ejemplar());
		editor.setVisible(true);
		
		if(editor.getStatus()==EjemplarEditor.EXIT_OK){
			Ejemplar nuevo = ServicioLibreria.altaEjemplar(editor.getModel());
			abrirEjemplar(nuevo);
		}
	}
	
	public void abrirEjemplar(){
		String id=JOptionPane.showInputDialog(this,"ID");
		if(id!=null){
			Ejemplar ejemplar = ServicioLibreria.buscarEjemplarPorId(Integer.parseInt(id));
			abrirEjemplar(ejemplar);
		}
	}
		
	public void abrirEjemplar(Ejemplar ejemplar){
		EjemplarView view = new EjemplarView(ejemplar);
		desktopPane.add(view);
		view.setVisible(true);
	}
	
	public void abrirResultadosBusquedaEjemplares(ResultadoBusquedaEjemplares resultado){
		ListaEjemplares lista = new ListaEjemplares(resultado);
		desktopPane.add(lista);
		lista.setVisible(true);
	}
}
