/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mimolibros.wasabi.client.request;

import com.mimolibros.wasabi.client.entities.Ejemplar;

/**
 *
 * @author Amnimhops
 */
public class AltaEjemplarRequest extends Request{
	Ejemplar ejemplar;

	public Ejemplar getEjemplar() {
		return ejemplar;
	}

	public void setEjemplar(Ejemplar ejemplar) {
		this.ejemplar = ejemplar;
	}

}
