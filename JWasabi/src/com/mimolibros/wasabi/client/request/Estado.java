/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mimolibros.wasabi.client.request;

import java.util.List;

/**
 *
 * @author Amnimhops
 */
public class Estado {
	boolean ok;
	List<Error> errores;

	public List<Error> getErrores() {
		return errores;
	}

	public void setErrores(List<Error> errores) {
		this.errores = errores;
	}

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}
}
