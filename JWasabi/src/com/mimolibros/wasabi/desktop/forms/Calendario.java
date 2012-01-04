package com.mimolibros.wasabi.desktop.forms;

import java.util.Date;
import java.util.Observer;

import com.qt.datapicker.DatePicker;

public class Calendario extends DatePicker{

	private Calendario(Observer observer) {
		super(observer);
		// TODO Auto-generated constructor stub
	}
	
	private Date getDate(){
		return calendar.getTime();
	}
	
	public static Date getDate(Date date){
		Calendario cal = new Calendario(null);
		cal.run();
		return cal.getDate();
	}
	
}
