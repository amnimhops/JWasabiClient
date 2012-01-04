/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mimolibros.wasabi.client.entities;

/**
 *
 * @author Amnimhops
 */
public class Autor {
	Integer id;
	String nombre;

	public Autor(Integer id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	public Autor(){
		
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
    
	public String toString(){
		return this.nombre;
	}

	public boolean equals(Object o){
		return (o != null && o instanceof Autor && ((Autor)o).id.equals(id));
	}
    
}
