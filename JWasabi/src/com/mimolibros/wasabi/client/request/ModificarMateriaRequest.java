package com.mimolibros.wasabi.client.request;

import com.mimolibros.wasabi.client.entities.Materia;

public class ModificarMateriaRequest extends Request {
	Materia materia;

	public Materia getMateria() {
		return materia;
	}

	public void setMateria(Materia materia) {
		this.materia = materia;
	}
}
