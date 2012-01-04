/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mimolibros.wasabi.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import com.mimolibros.wasabi.client.WasabiNetClient;
import com.mimolibros.wasabi.client.WasabiNetClientException;
import com.mimolibros.wasabi.client.entities.Autor;
import com.mimolibros.wasabi.client.entities.Ejemplar;
import com.mimolibros.wasabi.client.entities.Materia;
import com.mimolibros.wasabi.client.entities.CriterioBusquedaEjemplar;
import com.mimolibros.wasabi.client.entities.ParametrosBusquedaEjemplar;
import com.mimolibros.wasabi.client.request.AltaEjemplarRequest;
import com.mimolibros.wasabi.client.request.AltaEjemplarResponse;
import com.mimolibros.wasabi.client.request.AutorResponse;
import com.mimolibros.wasabi.client.request.BuscarAutoresRequest;
import com.mimolibros.wasabi.client.request.BuscarAutoresResponse;
import com.mimolibros.wasabi.client.request.BuscarEjemplaresRequest;
import com.mimolibros.wasabi.client.request.BuscarEjemplaresResponse;
import com.mimolibros.wasabi.client.request.BuscarMateriasRequest;
import com.mimolibros.wasabi.client.request.BuscarMateriasResponse;
import com.mimolibros.wasabi.client.request.EjemplarResponse;
import com.mimolibros.wasabi.client.request.Login;
import com.mimolibros.wasabi.client.request.MateriaResponse;
import com.mimolibros.wasabi.client.request.ModificarAutorRequest;
import com.mimolibros.wasabi.client.request.ModificarEjemplarRequest;
import com.mimolibros.wasabi.client.request.ModificarMateriaRequest;
import com.mimolibros.wasabi.client.request.NuevaMateriaRequest;
import com.mimolibros.wasabi.client.request.NuevoAutorRequest;
import com.mimolibros.wasabi.client.request.ResultadoBusquedaEjemplares;

/**
 *
 * @author Amnimhops
 */
public class ServicioLibreria {
	static WasabiNetClient client;
	
	static{
		client = new WasabiNetClient("http://localhost/Wasabi/invoke.php");
	}
	
	private static Login getLogin(){
		Login login = new Login();
		login.setClave("asdf");
		login.setUsuario("usuario");
		
		return login;
	}
	
	public static Ejemplar altaEjemplar(Ejemplar e){
		AltaEjemplarRequest request = new AltaEjemplarRequest();
		
		Ejemplar nuevo = null;
		
		request.setLogin(getLogin());
		request.setEjemplar(e);
		
		EjemplarResponse response=null;

		try {
			response = client.sendRequest(EjemplarResponse.class,request);
		
			if(response.getEstado().isOk()){
				nuevo = response.getEjemplar();
			}
		} catch (WasabiNetClientException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return nuevo;
	}
	
	public static Ejemplar modificarEjemplar(Ejemplar e){
		ModificarEjemplarRequest request = new ModificarEjemplarRequest();
		
		Ejemplar modificado = null;
		
		request.setLogin(getLogin());
		request.setEjemplar(e);
		
		EjemplarResponse response=null;

		try {
			response = client.sendRequest(EjemplarResponse.class,request);
		
			if(response.getEstado().isOk()){
				modificado = response.getEjemplar();
			}
		} catch (WasabiNetClientException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return modificado;
	}
	
	/* MAterias */
	public static Materia nuevaMateria(Materia materia){
		NuevaMateriaRequest request = new NuevaMateriaRequest();

		Materia nueva = null;
		
		request.setLogin(getLogin());
		request.setMateria(materia);
		
		MateriaResponse response;
		try {
			response = client.sendRequest(MateriaResponse.class,request);
			
			if(response.getEstado().isOk()){
				nueva = response.getMateria();	
			}
		} catch (WasabiNetClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return nueva;
		
	}
	
	public static Materia modificarMateria(Materia materia){
		ModificarMateriaRequest request=new ModificarMateriaRequest();
		Materia modificada = null;
		
		request.setLogin(getLogin());
		request.setMateria(materia);
		
		MateriaResponse response=null;
		try {
			response = client.sendRequest(MateriaResponse.class,request);
			
			if(response.getEstado().isOk()){
				modificada=response.getMateria();
			}
		} catch (WasabiNetClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return modificada;
	}
	
	public static List<Materia> buscarMateriasPorNombre(String nombre){
		BuscarMateriasRequest request = new BuscarMateriasRequest();
		List<Materia> materias = null;
		
		request.setNombre(nombre);
		request.setLogin(getLogin());
		
		BuscarMateriasResponse response=null;
		
		try {
			response = client.sendRequest(BuscarMateriasResponse.class,request);
			
			if(response.getEstado().isOk()){
				materias=response.getLista();
			}else{
				materias=new ArrayList<Materia>();
			}
		} catch (WasabiNetClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return materias;
		
	}
	
	public static List<Materia> buscarMateriasPorIdEjemplar(int idEjemplar){
		BuscarMateriasRequest request = new BuscarMateriasRequest();
		List<Materia> materias = null;
		
		request.setIdEjemplar(idEjemplar);
		request.setLogin(getLogin());
		
		BuscarMateriasResponse response=null;
		
		try {
			response = client.sendRequest(BuscarMateriasResponse.class,request);
			
			if(response.getEstado().isOk()){
				materias=response.getLista();
			}else{
				materias=new ArrayList<Materia>();
			}
		} catch (WasabiNetClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return materias;
	}
	
	public static Materia buscarMateriasPorId(int id){
		BuscarMateriasRequest request = new BuscarMateriasRequest();
		Materia buscada = null;
		
		request.setId(id);
		request.setLogin(getLogin());
		
		BuscarMateriasResponse response=null;
		try {
			response = client.sendRequest(BuscarMateriasResponse.class,request);
			
			if(response.getEstado().isOk()){
				buscada = response.getLista().get(0);
			}
		} catch (WasabiNetClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return buscada;
	}
	
	/* Autores */
	public static Autor nuevoAutor(Autor autor){
		NuevoAutorRequest request=new NuevoAutorRequest();
		Autor nuevo = null;
		
		request.setLogin(getLogin());
		request.setAutor(autor);
		
		AutorResponse response;
		try {
			response = client.sendRequest(AutorResponse.class,request);
			
			if(response.getEstado().isOk()){
				nuevo = response.getAutor();	
			}
		} catch (WasabiNetClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return nuevo;
		
	}
	
	public static Autor modificarAutor(Autor autor){
		ModificarAutorRequest request=new ModificarAutorRequest();
		Autor modificado = null;
		
		request.setLogin(getLogin());
		request.setAutor(autor);
		
		AutorResponse response=null;
		try {
			response = client.sendRequest(AutorResponse.class,request);
			
			if(response.getEstado().isOk()){
				modificado=response.getAutor();
			}
		} catch (WasabiNetClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return modificado;
	}
	
	public static List<Autor> buscarAutoresPorNombre(String nombre){
		BuscarAutoresRequest request = new BuscarAutoresRequest();
		List<Autor> autores = null;
		
		request.setNombre(nombre);
		request.setLogin(getLogin());
		
		BuscarAutoresResponse response=null;
		
		try {
			response = client.sendRequest(BuscarAutoresResponse.class,request);
			
			if(response.getEstado().isOk()){
				autores=response.getLista();
			}else{
				autores=new ArrayList<Autor>();
			}
		} catch (WasabiNetClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return autores;
	}
	
	public static List<Autor> buscarAutoresPorIdEjemplar(int idEjemplar){
		BuscarAutoresRequest request = new BuscarAutoresRequest();
		List<Autor> autores = null;
		
		request.setIdEjemplar(idEjemplar);
		request.setLogin(getLogin());
		
		BuscarAutoresResponse response;
		try {
			response = client.sendRequest(BuscarAutoresResponse.class,request);
			
			if(response.getEstado().isOk()){
				autores=response.getLista();
			}else{
				autores=new ArrayList<Autor>();
			}
			
		} catch (WasabiNetClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return autores;
	}
	
	public static Autor buscarAutoresPorId(int id){
		BuscarAutoresRequest request = new BuscarAutoresRequest();
		Autor buscado=null;
		
		request.setId(id);
		request.setLogin(getLogin());
		
		BuscarAutoresResponse response;
		try {
			response = client.sendRequest(BuscarAutoresResponse.class,request);
			
			if(response.getEstado().isOk()){
				buscado = response.getLista().get(0);
			}
		} catch (WasabiNetClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return buscado;
		
	}
	
	public static Ejemplar buscarEjemplarPorId(int id){
		ParametrosBusquedaEjemplar param = new ParametrosBusquedaEjemplar();
		param.setId(id);
		
		ResultadoBusquedaEjemplares resultado = ServicioLibreria.buscarEjemplares(param);
		
		if(resultado.getEjemplares()!=null && resultado.getEjemplares().size()==1){
			return resultado.getEjemplares().get(0);
		}else{
			return null;
		}
	}
	
	public static ResultadoBusquedaEjemplares buscarEjemplares(ParametrosBusquedaEjemplar parametros){
		BuscarEjemplaresRequest request = new BuscarEjemplaresRequest();
		ResultadoBusquedaEjemplares resultado = null;
		
		request.setLogin(getLogin());
		request.setParametros(parametros);
		
		try {
			BuscarEjemplaresResponse response = client.sendRequest(BuscarEjemplaresResponse.class,request);
			if(response.getEstado().isOk()){
				resultado = response.getResultado();
			}else{
				resultado=null;
			}
		} catch (WasabiNetClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultado;
	}

	public static void main(String[] args){
		ParametrosBusquedaEjemplar param = new ParametrosBusquedaEjemplar();
		param.setId(1);
		//param.setFechaAltaDesde(new Date());
		System.out.println(new Date());
		//param.getCriterios().add(new CriterioBusquedaEjemplar("fechaAltaDesde",null,null,false));
		ResultadoBusquedaEjemplares r = ServicioLibreria.buscarEjemplares(param);
		System.out.println(r.getEjemplares().get(0).getFechaAlta());
		
	}
}
