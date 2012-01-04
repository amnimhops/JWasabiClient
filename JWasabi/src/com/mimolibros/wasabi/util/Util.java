package com.mimolibros.wasabi.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Util {
	private final static int MAX_BUF_READ=4096;
	
	public static <T> String implode(List<T> list, String glue){
		if(list==null){
			return "";
		}else if(list.size()==1){
			return list.get(0).toString();
		}
		StringBuffer sb = new StringBuffer();
		for(int c=0;c<list.size()-1;c++){
			sb.append(list.get(c).toString()+glue);
		}
		sb.append(list.get(list.size()-1));
		return sb.toString();
	}
	
	public static String dateToString(Date date){
		if(date!=null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			return calendar.get(Calendar.DAY_OF_MONTH)+"/"+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.YEAR);
		}else{
			return "";
		}
	}
	
	public static Date stringToDate(String date){
		return stringToDate(date, "yy-MM-dd");
	}
	
	public static Date stringToDate(String date, String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}
	
	public static String getResourceContent(String resource){
		BufferedReader br = new BufferedReader(new InputStreamReader(Util.class.getResourceAsStream(resource)));
		
		char[] buffer = new char[MAX_BUF_READ];
		int bRead = 0;
		StringBuffer sb = new StringBuffer();
		
		try {
			while((bRead=br.read(buffer))>0){
				sb.append(buffer,0,bRead);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sb.toString();
		
	}
	
	public static void main(String[] args){
		System.out.println(dateToString(stringToDate("2010-12-13")));
	}
}
