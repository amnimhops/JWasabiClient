/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mimolibros.wasabi.util;

import com.mimolibros.wasabi.client.request.AltaEjemplarRequest;
import com.mimolibros.wasabi.client.request.Login;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URISyntaxException;
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
public class XmlUtil {
	private static XMLContext context;
	private static Marshaller marshaller;
	private static Unmarshaller unmarshaller;
	private static Mapping mapping;
	
	static{
		mapping = new Mapping();
		
		try {
			XmlUtil.mapping.loadMapping(XmlUtil.class.getResource("/META-INF/castor-mapping.xml").toURI().toURL());
			XmlUtil.context = new XMLContext();
			XmlUtil.context.addMapping(XmlUtil.mapping);
			
			XmlUtil.marshaller=XmlUtil.context.createMarshaller();
			XmlUtil.unmarshaller=XmlUtil.context.createUnmarshaller();
			
		} catch (URISyntaxException ex){
			Logger.getLogger(XmlUtil.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(XmlUtil.class.getName()).log(Level.SEVERE, null, ex);
		} catch (MappingException ex) {
			Logger.getLogger(XmlUtil.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	public static Object parseXml(String xml) throws Exception{
		try {
			return XmlUtil.unmarshaller.unmarshal(new StringReader(xml));
		} catch (MarshalException ex) {
			Logger.getLogger(XmlUtil.class.getName()).log(Level.SEVERE, null, ex);
			throw new Exception("Error al recuperar el xml:"+ex.getMessage());
		} catch (ValidationException ex) {
			Logger.getLogger(XmlUtil.class.getName()).log(Level.SEVERE, null, ex);
			throw new Exception("Error al validar el xml:"+ex.getMessage());
		}
	}
	
	public static Object parseXml(InputStream stream) throws Exception{
		try {
			return XmlUtil.unmarshaller.unmarshal(new InputStreamReader(stream));
		} catch (MarshalException ex) {
			Logger.getLogger(XmlUtil.class.getName()).log(Level.SEVERE, null, ex);
			throw new Exception("Error al recuperar el xml:"+ex.getMessage());
		} catch (ValidationException ex) {
			Logger.getLogger(XmlUtil.class.getName()).log(Level.SEVERE, null, ex);
			throw new Exception("Error al validar el xml:"+ex.getMessage());
		}
	}
	
	public static void main(String[] args) throws Exception{
		Login login =(Login) XmlUtil.parseXml(XmlUtil.class.getResourceAsStream("/META-INF/pruebaconf.xml"));
		System.out.println(login.getUsuario());
	}
}
