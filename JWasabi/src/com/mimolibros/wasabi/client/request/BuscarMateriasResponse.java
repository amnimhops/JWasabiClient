package com.mimolibros.wasabi.client.request;

import java.util.List;

import com.mimolibros.wasabi.client.entities.Materia;

public class BuscarMateriasResponse extends Response {
	List<Materia> lista;

	public List<Materia> getLista() {
		return lista;
	}

	public void setLista(List<Materia> lista) {
		this.lista = lista;
	}
	
	
}
