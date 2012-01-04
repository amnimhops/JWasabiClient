/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mimolibros.wasabi.client;

import com.mimolibros.wasabi.client.entities.Autor;
import com.mimolibros.wasabi.client.entities.Ejemplar;
import com.mimolibros.wasabi.client.entities.Materia;
import com.mimolibros.wasabi.client.request.AltaEjemplarRequest;
import com.mimolibros.wasabi.client.request.Login;
import com.mimolibros.wasabi.client.request.Request;
import com.mimolibros.wasabi.client.request.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.exolab.castor.xml.XMLContext;

/**
 *
 * @author Amnimhops
 */
public class WasabiNetClient {
	private XMLContext context;
	private Marshaller marshaller;
	private Unmarshaller unmarshaller;
	private String serviceUrl;
	
	private final static int MAX_READ = 4096;
	
	public WasabiNetClient(String url){
		Mapping mapping = new Mapping();
		try {
			mapping.loadMapping(getClass().getResource("/META-INF/castor-mapping.xml").toURI().toURL());
			context = new XMLContext();
			context.addMapping(mapping);
			
			this.marshaller=context.createMarshaller();
			this.unmarshaller=context.createUnmarshaller();
			
			this.serviceUrl=url;
		} catch (URISyntaxException ex){
			Logger.getLogger(WasabiNetClient.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(WasabiNetClient.class.getName()).log(Level.SEVERE, null, ex);
		} catch (MappingException ex) {
			Logger.getLogger(WasabiNetClient.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	private String getData(String url,String data) throws WasabiNetClientException{
		
		StringBuffer sb = new StringBuffer();
		
		try {
			URL dest = new URL(url);
			URLConnection conn = dest.openConnection();
			
			conn.setDoOutput(true);
			
			OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(),"UTF8");
			System.out.println(writer.getEncoding());
			writer.write(data);
			writer.flush();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			char[] buf = new char[MAX_READ];
			int readed=0;
			while((readed=reader.read(buf))>0){
				sb.append(buf, 0, readed);
			}
			
		} catch (MalformedURLException ex) {
			Logger.getLogger(WasabiNetClient.class.getName()).log(Level.SEVERE, null, ex);
			throw new WasabiNetClientException("Error al conectar con la url <"+url+">:"+ex.getMessage());
		} catch (IOException ex){
			Logger.getLogger(WasabiNetClient.class.getName()).log(Level.SEVERE, null, ex);
			throw new WasabiNetClientException("Error de lectura/escritura:"+ex.getMessage());
		}
		
		return sb.toString();
	}
	
	public Object sendRequest(Object request) throws WasabiNetClientException{
		StringWriter sw = new StringWriter();
		String convertida = null;
		String received = null;
		try {
			marshaller.setWriter(sw);
			marshaller.marshal(request);
			
			convertida = new String(sw.getBuffer().toString().getBytes("UTF8"),"UTF8");
			System.out.println(convertida);
			
			received=getData(serviceUrl, convertida);
			
			System.out.println("---"+received+"---");

			return unmarshaller.unmarshal(new StringReader(received));
			
		} catch (IOException e) {
			throw new WasabiNetClientException("Error de lectura/escritura:"+e.getMessage());
		} catch (MarshalException e) {
			throw new WasabiNetClientException("Error al armar la respuesta:"+e.getMessage(),convertida,received);
		} catch (ValidationException e) {
			throw new WasabiNetClientException("Error en la validacion de la respuesta:"+e.getMessage(),convertida,received);
		} // catch WasabiNetClientException -> Esta directamente salta al metodo que ha llamado a sendrequest
	}

	public <T> T sendRequest(Class<T> cls, Object request) throws WasabiNetClientException{
		StringWriter sw = new StringWriter();
		String convertida = null;
		String received = null;
		try {
			marshaller.setWriter(sw);
			marshaller.marshal(request);
			
			convertida = new String(sw.getBuffer().toString().getBytes("UTF8"),"UTF8");
			System.out.println(convertida);
			
			received=getData(serviceUrl, convertida);
			
			System.out.println("---"+received+"---");
			
			unmarshaller.setClass(cls);
			
			return (T) unmarshaller.unmarshal(new StringReader(received));
			
		} catch (IOException e) {
			throw new WasabiNetClientException("Error de lectura/escritura:"+e.getMessage());
		} catch (MarshalException e) {
			e.printStackTrace();
			throw new WasabiNetClientException("Error al armar la respuesta:"+e.getMessage(),convertida,received);
		} catch (ValidationException e) {
			throw new WasabiNetClientException("Error en la validacion de la respuesta:"+e.getMessage(),convertida,received);
		} // catch WasabiNetClientException -> Esta directamente salta al metodo que ha llamado a sendrequest
	}

	public static void main(String[] args) throws IOException, MarshalException, ValidationException{
		WasabiNetClient ws = new WasabiNetClient("");
		AltaEjemplarRequest request = new AltaEjemplarRequest();
		request.setLogin(new Login());
		request.getLogin().setClave("miclave");
		request.getLogin().setUsuario("miusuario");
		Ejemplar ejemplar=new Ejemplar();
		ejemplar.setId(25);
		ejemplar.setDescripcion("descripcion");
		ejemplar.setEdicion("primera");
		ejemplar.setEditorial("Canaya");
		ejemplar.setFechaAlta(Calendar.getInstance().getTime());
		ejemplar.setFechaModificacion(Calendar.getInstance().getTime());
		ejemplar.setFechaPublicacion(Calendar.getInstance().getTime());
		ejemplar.setObservaciones("nueva observacion");
		ejemplar.setPrecio(66.54f);
		ejemplar.setReferencia("RF-43");
		ejemplar.setTitulo("El kijotillo");
		
		ejemplar.setAutores(new ArrayList<Autor>());
		ejemplar.getAutores().add(new Autor(1,"Pepe juarez"));
		ejemplar.getAutores().add(new Autor(2,"Marí juarez"));
		
		ejemplar.setMaterias(new ArrayList<Materia>());
		ejemplar.getMaterias().add(new Materia(1,"Geografia"));
		ejemplar.getMaterias().add(new Materia(2,"Lietratura fantastica"));
		
		request.setEjemplar(ejemplar);
		
		StringWriter sw = new StringWriter();
		ws.marshaller.setWriter(sw);
		ws.marshaller.marshal(request);
		
		System.out.println(sw.toString());
		
		String received=null;
		
		try {
			received = ws.getData("http://localhost/Wasabi/invoke.php", sw.getBuffer().toString());
		} catch (WasabiNetClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(received);
		AltaEjemplarRequest response = (AltaEjemplarRequest) ws.unmarshaller.unmarshal(new StringReader(received));
		
		System.out.println(response);
	}
}
