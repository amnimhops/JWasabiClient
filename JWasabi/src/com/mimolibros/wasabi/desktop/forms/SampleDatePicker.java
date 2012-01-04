package com.mimolibros.wasabi.desktop.forms;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;

import com.qt.datapicker.DatePicker;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

public class SampleDatePicker extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField textField_1;

	class XX extends DatePicker{

		public XX(Observer observer) {
			super(observer);
			// TODO Auto-generated constructor stub
		}
		
		public Calendar getCalendar(){
			return this.calendar;
		}
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			SampleDatePicker dialog = new SampleDatePicker();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public SampleDatePicker() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		this.contentPanel.setLayout(new FlowLayout());
		this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(this.contentPanel, BorderLayout.CENTER);
		{
			this.textField = new JTextField();
			this.textField.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent arg0) {
					
					System.out.println("yaja");
					//Date d = Calendario.getDate(null);
					
					//System.out.println(d);
					
				}
			});
			{
				JButton btnNewButton = new JButton("New button");
				btnNewButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						XX dd=new XX(null);
						dd.run();
						System.out.println(dd.getCalendar().getTime());
						
				}});
				contentPanel.add(btnNewButton);
			}
			contentPanel.add(this.textField);
			this.textField.setColumns(10);
		}
		{
			this.textField_1 = new JTextField();
			contentPanel.add(this.textField_1);
			this.textField_1.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
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
	}

}
