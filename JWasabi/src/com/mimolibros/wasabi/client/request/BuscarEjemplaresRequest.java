package com.mimolibros.wasabi.client.request;

import java.util.Date;

import com.mimolibros.wasabi.client.entities.ParametrosBusquedaEjemplar;

public class BuscarEjemplaresRequest extends Request{
	ParametrosBusquedaEjemplar parametros;

	public ParametrosBusquedaEjemplar getParametros() {
		return parametros;
	}

	public void setParametros(ParametrosBusquedaEjemplar parametros) {
		this.parametros = parametros;
	}
	
	
}
