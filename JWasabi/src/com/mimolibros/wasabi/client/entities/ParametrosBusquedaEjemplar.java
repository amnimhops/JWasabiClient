package com.mimolibros.wasabi.client.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ParametrosBusquedaEjemplar {
	Integer id;
	int resultadosPorPagina;
	int pagina;
	
	List<GrupoCriteriosBusquedaEjemplar> grupos;

	
	public ParametrosBusquedaEjemplar(){
		this.grupos=new ArrayList<GrupoCriteriosBusquedaEjemplar>();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<GrupoCriteriosBusquedaEjemplar> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<GrupoCriteriosBusquedaEjemplar> campos) {
		this.grupos = campos;
	}

	public int getResultadosPorPagina() {
		return resultadosPorPagina;
	}

	public void setResultadosPorPagina(int resultadosPorPagina) {
		this.resultadosPorPagina = resultadosPorPagina;
	}

	public int getPagina() {
		return pagina;
	}

	public void setPagina(int pagina) {
		this.pagina = pagina;
	}
	
	
	
}
