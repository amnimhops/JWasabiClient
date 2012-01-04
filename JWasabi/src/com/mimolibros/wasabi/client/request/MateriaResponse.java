package com.mimolibros.wasabi.client.request;

import com.mimolibros.wasabi.client.entities.Materia;

public class MateriaResponse extends Response {
	Materia materia;

	public Materia getMateria() {
		return materia;
	}

	public void setMateria(Materia materia) {
		this.materia = materia;
	}
}
