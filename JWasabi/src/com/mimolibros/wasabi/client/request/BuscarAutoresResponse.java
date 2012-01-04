package com.mimolibros.wasabi.client.request;

import java.util.List;

import com.mimolibros.wasabi.client.entities.Autor;

public class BuscarAutoresResponse extends Response {
	List<Autor> lista;

	public List<Autor> getLista() {
		return lista;
	}

	public void setLista(List<Autor> lista) {
		this.lista = lista;
	}
	
	
}
