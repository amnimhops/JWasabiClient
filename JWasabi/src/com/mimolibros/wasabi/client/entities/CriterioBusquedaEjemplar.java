package com.mimolibros.wasabi.client.entities;

public class CriterioBusquedaEjemplar {
	public static final String CRT_IGUAL="igual";
	public static final String CRT_MENOR_QUE="menor que";
	public static final String CRT_MAYOR_QUE="mayor que";
	public static final String CRT_CONTIENE="contiene";
	
	
	String campo;
	String valor;
	String criterio;
	boolean invertido;
	boolean opcional;
	
	public CriterioBusquedaEjemplar(){
		
	}
	
	public CriterioBusquedaEjemplar(String campo,String valor,String criterio,boolean invertido){
		this.campo=campo;
		this.valor=valor;
		this.criterio=criterio;
		this.invertido=invertido;
	}
	public String getCampo() {
		return campo;
	}
	public void setCampo(String campo) {
		this.campo = campo;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getCriterio() {
		return criterio;
	}
	public void setCriterio(String criterio) {
		this.criterio = criterio;
	}
	public boolean isInvertido() {
		return invertido;
	}
	public void setInvertido(boolean invertido) {
		this.invertido = invertido;
	}

	public boolean isOpcional() {
		return opcional;
	}

	public void setOpcional(boolean opcional) {
		this.opcional = opcional;
	}
}
