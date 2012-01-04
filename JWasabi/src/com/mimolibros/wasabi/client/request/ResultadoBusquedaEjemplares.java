package com.mimolibros.wasabi.client.request;

import java.util.List;

import com.mimolibros.wasabi.client.entities.Ejemplar;

public class ResultadoBusquedaEjemplares {
	int pagina;
	int numeroResultados;
	int numeroPaginas;
	
	List<Ejemplar> ejemplares;

	public int getPagina() {
		return pagina;
	}

	public void setPagina(int pagina) {
		this.pagina = pagina;
	}

	public int getNumeroResultados() {
		return numeroResultados;
	}

	public void setNumeroResultados(int numeroResultados) {
		this.numeroResultados = numeroResultados;
	}

	public int getNumeroPaginas() {
		return numeroPaginas;
	}

	public void setNumeroPaginas(int numeroPaginas) {
		this.numeroPaginas = numeroPaginas;
	}

	public List<Ejemplar> getEjemplares() {
		return ejemplares;
	}

	public void setEjemplares(List<Ejemplar> ejemplares) {
		this.ejemplares = ejemplares;
	}
	
	
}
