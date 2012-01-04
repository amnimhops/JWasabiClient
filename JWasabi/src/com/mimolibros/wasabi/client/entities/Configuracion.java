/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mimolibros.wasabi.client.entities;


import java.util.HashMap;
import java.util.List;

import javax.management.RuntimeErrorException;

import com.mimolibros.wasabi.util.Util;
import com.mimolibros.wasabi.util.XmlUtil;

/**
 *
 * @author Amnimhops
 */
public class Configuracion {
	static HashMap<String,HashMap<String,Parametro>> mapCfg;

	List<Seccion> secciones;

	static{
		// Carga del mapa de configuracion
		Configuracion.mapCfg=new HashMap<String, HashMap<String, Parametro>>();
		try {
			Configuracion cfg = (Configuracion) XmlUtil.parseXml(Util.getResourceContent("/META-INF/pruebaconf.xml"));
			
			for(Seccion seccion : cfg.getSecciones()){
				HashMap<String,Parametro> hsParametros = new HashMap<String, Parametro>();
				for(Parametro parametro:seccion.getParametros()){
					hsParametros.put(parametro.getNombre(), parametro);
				}
				Configuracion.mapCfg.put(seccion.getNombre(), hsParametros);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<Seccion> getSecciones() {
		return secciones;
	}

	public void setSecciones(List<Seccion> secciones) {
		this.secciones = secciones;
	}
	
	public static String get(String key){
		String sectionKey = key.substring(0,key.indexOf("."));
		String paramKey = key.substring(key.indexOf(".")+1);
		try{
			return mapCfg.get(sectionKey).get(paramKey).getValor();
		}catch(Exception e){
			throw new Error("El parametro "+key+" no existe");
		}
	}
	
	
	
	public static void main(String[] args){
		new Configuracion();
		
		System.out.println(Configuracion.get("OtraSeccion.otroParametro"));
	}
	
}
