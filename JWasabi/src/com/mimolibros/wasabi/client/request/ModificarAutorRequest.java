package com.mimolibros.wasabi.client.request;

import com.mimolibros.wasabi.client.entities.Autor;

public class ModificarAutorRequest extends Request {
	Autor autor;

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}
	
	
}
