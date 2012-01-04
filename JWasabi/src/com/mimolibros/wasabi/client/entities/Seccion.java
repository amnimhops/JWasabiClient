/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mimolibros.wasabi.client.entities;

import java.util.List;

/**
 *
 * @author Amnimhops
 */
public class Seccion {
	String nombre;
	List<Parametro> parametros;

	public List<Parametro> getParametros() {
		return parametros;
	}

	public void setParametros(List<Parametro> parametros) {
		this.parametros = parametros;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
}
