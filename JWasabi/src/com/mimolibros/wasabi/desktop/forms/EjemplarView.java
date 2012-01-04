package com.mimolibros.wasabi.desktop.forms;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JToolBar;
import javax.swing.JEditorPane;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.mimolibros.wasabi.client.entities.Ejemplar;
import com.mimolibros.wasabi.services.ServicioLibreria;
import com.mimolibros.wasabi.util.Util;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class EjemplarView extends JInternalFrame {
	private static String template;
	
	private final JPanel panel = new JPanel();
	private final JToolBar toolbar = new JToolBar();
	private final JButton button = new JButton("");
	private final JLabel lblNewLabel = new JLabel("New label");

	private Ejemplar ejemplar;
	private final JScrollPane scrollPane = new JScrollPane();
	private final JEditorPane editorPane = new JEditorPane();

	static{
		EjemplarView.template=Util.getResourceContent("/META-INF/EjemplarView.html");
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EjemplarView frame = new EjemplarView(ServicioLibreria.buscarEjemplarPorId(1));
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public EjemplarView(final Ejemplar ejemplar) {
		setResizable(true);
		setClosable(true);
		setMaximizable(true);
		setIconifiable(true);
		setTitle("EjemplarViewer");
		setBounds(100, 100, 450, 300);
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		
		getContentPane().add(panel, BorderLayout.SOUTH);
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		panel.add(lblNewLabel);
		toolbar.setOrientation(SwingConstants.VERTICAL);
		
		getContentPane().add(toolbar, BorderLayout.WEST);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				edit();
			}
		});
		button.setIcon(new ImageIcon(EjemplarView.class.getResource("/resources/icon/small/text-editor.png")));
		
		toolbar.add(button);
		
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		editorPane.setText("<html>\n\t<h1>{autor_principal}.<dynamic></h1>\n\t<h2>Por {autor_principal}{otros_autores}</h2>\n\t<h3>En {materias}</h3>\n\t<p>Editorial <dynamic></p>\n\t<p>Publicado el {fecha_publicacion}</p>\n\t<h2 style=\"color:red;width:100%;text-align:right\">0.0 &euro;</h2>\n\t<div><dynamic></div>\n</html>\n");
		editorPane.setEditable(false);
		editorPane.setContentType("text/html");
		
		scrollPane.setViewportView(editorPane);

		this.ejemplar=ejemplar;
		
		this.setTitle(ejemplar.getTitulo());
		refresh();
	}
	
	public void refresh(){
		String tpl = EjemplarView.template;
		tpl=tpl.replaceAll("\\{id\\}", String.valueOf(ejemplar.getId()));
		tpl=tpl.replaceAll("\\{referencia\\}", ejemplar.getReferencia());
		tpl=tpl.replaceAll("\\{titulo\\}", ejemplar.getTitulo());
		tpl=tpl.replaceAll("\\{edicion\\}", ejemplar.getEdicion());
		tpl=tpl.replaceAll("\\{editorial\\}", ejemplar.getEditorial());
		tpl=tpl.replaceAll("\\{precio\\}", String.valueOf(ejemplar.getPrecio()));
		tpl=tpl.replaceAll("\\{fechaPublicacion\\}", Util.dateToString(ejemplar.getFechaPublicacion()));
		tpl=tpl.replaceAll("\\{fechaModificacion\\}", Util.dateToString(ejemplar.getFechaModificacion()));
		tpl=tpl.replaceAll("\\{fechaAlta\\}", Util.dateToString(ejemplar.getFechaAlta()));
		tpl=tpl.replaceAll("\\{descripcion\\}", ejemplar.getDescripcion());
		tpl=tpl.replaceAll("\\{observaciones\\}", ejemplar.getObservaciones());
		
		if(ejemplar.getAutores()!=null && ejemplar.getAutores().size()>0){
			tpl=tpl.replaceAll("\\{autor_principal\\}", ejemplar.getAutores().get(0).toString());
		}
		
		if(ejemplar.getMaterias()!=null && ejemplar.getMaterias().size()>0){
			tpl=tpl.replaceAll("\\{materias\\}", Util.implode(ejemplar.getMaterias(),", "));
		}
		
		editorPane.setText(tpl);
	}
	
	private void edit(){
		EjemplarEditor editor = new EjemplarEditor(ejemplar);
		editor.setVisible(true);
		if(editor.getStatus()==editor.EXIT_OK){
			ejemplar=editor.getModel();
			ServicioLibreria.modificarEjemplar(ejemplar);
			refresh();
		}
	}

}
