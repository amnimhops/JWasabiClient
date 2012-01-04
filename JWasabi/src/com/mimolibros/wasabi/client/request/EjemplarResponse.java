package com.mimolibros.wasabi.client.request;

import com.mimolibros.wasabi.client.entities.Ejemplar;

public class EjemplarResponse extends Response {
	Ejemplar ejemplar;

	public Ejemplar getEjemplar() {
		return ejemplar;
	}

	public void setEjemplar(Ejemplar ejemplar) {
		this.ejemplar = ejemplar;
	}
}
