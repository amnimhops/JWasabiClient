package com.mimolibros.wasabi.client.entities;

import java.util.List;

public class GrupoCriteriosBusquedaEjemplar {
	public final static int COMBINAR_Y=0;
	public final static int COMBINAR_O=1;
	
	boolean invertir;
	boolean opcional;

	List<CriterioBusquedaEjemplar> criterios;
	
	public boolean isInvertir() {
		return invertir;
	}
	public void setInvertir(boolean invertir) {
		this.invertir = invertir;
	}
	
	public List<CriterioBusquedaEjemplar> getCriterios() {
		return criterios;
	}
	public void setCriterios(List<CriterioBusquedaEjemplar> criterios) {
		this.criterios = criterios;
	}
	public boolean isOpcional() {
		return opcional;
	}
	public void setOpcional(boolean opcional) {
		this.opcional = opcional;
	}
}
