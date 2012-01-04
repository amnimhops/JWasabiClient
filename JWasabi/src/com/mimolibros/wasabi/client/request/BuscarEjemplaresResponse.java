package com.mimolibros.wasabi.client.request;

import java.util.List;

import com.mimolibros.wasabi.client.entities.Ejemplar;

public class BuscarEjemplaresResponse extends Response {
	/*List<Ejemplar> lista;

	public List<Ejemplar> getLista() {
		return lista;
	}

	public void setLista(List<Ejemplar> lista) {
		this.lista = lista;
	}*/
	ResultadoBusquedaEjemplares resultado;

	public ResultadoBusquedaEjemplares getResultado() {
		return resultado;
	}

	public void setResultado(ResultadoBusquedaEjemplares resultado) {
		this.resultado = resultado;
	}
	
	
}
