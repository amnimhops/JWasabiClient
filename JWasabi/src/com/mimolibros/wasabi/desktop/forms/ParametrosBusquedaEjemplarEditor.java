package com.mimolibros.wasabi.desktop.forms;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.mimolibros.wasabi.client.entities.CriterioBusquedaEjemplar;
import com.mimolibros.wasabi.client.entities.Ejemplar;
import com.mimolibros.wasabi.client.entities.GrupoCriteriosBusquedaEjemplar;
import com.mimolibros.wasabi.client.entities.ParametrosBusquedaEjemplar;
import com.mimolibros.wasabi.client.request.ResultadoBusquedaEjemplares;
import com.mimolibros.wasabi.services.ServicioLibreria;
import com.mimolibros.wasabi.util.Util;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;

public class ParametrosBusquedaEjemplarEditor extends ModalBase<ParametrosBusquedaEjemplar>{

	private final static String CRIT_EQUAL = "Exacto";
	private final static String CRIT_EVERYWORD = "Todo";
	private final static String CRIT_SOMEWORD = "Algo";
	private final static String CRIT_RANGE = "Rango";

	private List<JCriterio> criterios;
	private JPanel panel;
	
	class JCriterio{
		JComboBox campo;
		JComboBox operador;
		JTextField valor;
		JCheckBox invertir;
		JCheckBox opcional;
		JButton eliminar;
		
		public JCriterio(){
			campo=new JComboBox(new String[]{"referencia","titulo","autor","materia","alta","precio","editorial","edicion"});
			operador = new JComboBox(new String[]{"igual","mayor que","menor que","contiene"});
			valor=new JTextField();
			invertir=new JCheckBox("Invertir");
			opcional=new JCheckBox("Opcional");
			eliminar=new JButton();
			eliminar.setIcon(new ImageIcon(ParametrosBusquedaEjemplarEditor.class.getResource("/resources/icon/small/gtk-quit.png")));
		}
		
		public JComboBox getCampo() {
			return campo;
		}
		public void setCampo(JComboBox campo) {
			this.campo = campo;
		}
		public JComboBox getOperador() {
			return operador;
		}
		public void setOperador(JComboBox operador) {
			this.operador = operador;
		}
		public JTextField getValor() {
			return valor;
		}
		public void setValor(JTextField valor) {
			this.valor = valor;
		}
		public JCheckBox getInvertir() {
			return invertir;
		}
		public void setInvertir(JCheckBox invertir) {
			this.invertir = invertir;
		}
		public JButton getEliminar() {
			return eliminar;
		}
		public void setEliminar(JButton eliminar) {
			this.eliminar = eliminar;
		}

		public JCheckBox getOpcional() {
			return opcional;
		}

		public void setOpcional(JCheckBox opcional) {
			this.opcional = opcional;
		}
		
		
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ParametrosBusquedaEjemplarEditor dialog = new ParametrosBusquedaEjemplarEditor(new ParametrosBusquedaEjemplar());
			ParametrosBusquedaEjemplar params = dialog.getModel();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			ResultadoBusquedaEjemplares rst = ServicioLibreria.buscarEjemplares(dialog.getModel());
			System.out.println(rst.getNumeroResultados());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Create the dialog.
	 */
	public ParametrosBusquedaEjemplarEditor(ParametrosBusquedaEjemplar parametros) {
		setTitle("Parámetros de búsqueda");
		setBounds(100, 100, 590, 537);
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
				{
					JButton bAddCriterio = new JButton("Añadir criterio");
					bAddCriterio.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							addRow();
						}
					});
					buttonPane.add(bAddCriterio);
				}
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			getContentPane().add(scrollPane, BorderLayout.CENTER);
			{
				panel = new JPanel();
				scrollPane.setViewportView(panel);
				panel.setLayout(new MigLayout("", "[grow][grow][grow][][][]", "[]"));
			}
		}
		
		this.model=parametros;
		this.criterios=new ArrayList<ParametrosBusquedaEjemplarEditor.JCriterio>();
		fillForm();
	}

	@Override
	public void fillForm() {
		/*HashMap<String,CriterioBusquedaEjemplar> hsCampos = new HashMap<String,CriterioBusquedaEjemplar>();
		
		for(CriterioBusquedaEjemplar param:model.getCriterios()){
			hsCampos.put(param.getCampo(), param);
		}
		hsCampos.get("").getValor();;
		*/
	}

	@Override
	public void fillModel() throws FillModelException {
		// TODO Auto-generated method stub
		model.setGrupos(new ArrayList<GrupoCriteriosBusquedaEjemplar>());
		model.getGrupos().add(new GrupoCriteriosBusquedaEjemplar());
		model.getGrupos().get(0).setCriterios(new ArrayList<CriterioBusquedaEjemplar>());
		
		for(JCriterio crt:criterios){
			CriterioBusquedaEjemplar param = new CriterioBusquedaEjemplar();
			param.setCampo((String)crt.getCampo().getSelectedItem());
			param.setCriterio((String)crt.getOperador().getSelectedItem());
			param.setInvertido(crt.getInvertir().isSelected());
			param.setOpcional(crt.getOpcional().isSelected());
			param.setValor(crt.getValor().getText());
			model.getGrupos().get(0).getCriterios().add(param);
		}
	}
	
	public void ok(){
		try{
			super.ok();
		}catch(FillModelException e){
			JOptionPane.showMessageDialog(this, "Error: "+e.getLocalizedMessage());
		}
	}
	
	private void addRow(){
		final JCriterio crit = new JCriterio();
		int index = criterios.size();
		
		criterios.add(crit);
		
		panel.add(crit.getCampo(), "cell 0 "+index+",growx");
		panel.add(crit.getOperador(), "cell 1 "+index+",growx");
		panel.add(crit.getValor(), "cell 2 "+index+",growx");
		panel.add(crit.getInvertir(), "cell 3 "+index);
		panel.add(crit.getOpcional(), "cell 4 "+index);
		panel.add(crit.getEliminar(), "cell 5 "+index);
		
		crit.getEliminar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeRow(crit);
			}
		});
		
		panel.revalidate();
		
	}

	private void removeRow(JCriterio crit){
		panel.remove(crit.getCampo());
		panel.remove(crit.getOperador());
		panel.remove(crit.getValor());
		panel.remove(crit.getInvertir());
		panel.remove(crit.getOpcional());
		panel.remove(crit.getEliminar());
		panel.revalidate();
		repaint();
		criterios.remove(crit);
				
	}
	protected JPanel getPanel() {
		return this.panel;
	}
}
