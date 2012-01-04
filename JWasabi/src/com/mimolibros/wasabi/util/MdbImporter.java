package com.mimolibros.wasabi.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import com.mimolibros.wasabi.client.entities.Autor;
import com.mimolibros.wasabi.client.entities.Ejemplar;
import com.mimolibros.wasabi.client.entities.Materia;

public class MdbImporter {
	public static HashMap<Integer,Autor> autores=new HashMap<Integer, Autor>();
	public static HashMap<Integer,Materia> materias=new HashMap<Integer, Materia>();
	
	public static void main(String[] args) {
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			Connection con = DriverManager.getConnection("jdbc:odbc:MimoMdb-3.0.0");
			
			ResultSet rs = con.createStatement().executeQuery("SELECT * FROM autores");
			
			int c=0;
			
			while(rs.next()){
				Autor autor = new Autor();
				
				autor.setId(rs.getInt("IDAUTOR"));
				autor.setNombre(rs.getString("NOMBRE"));
				
				autores.put(autor.getId(), autor);
			}
			rs.close();
			
			rs = con.createStatement().executeQuery("SELECT * FROM materias");
			
			c=0;

			while(rs.next()){
				Materia materia = new Materia();
				
				materia.setId(rs.getInt("IDMATERIA"));
				materia.setNombre(rs.getString("NOMBRE"));
				
				materias.put(materia.getId(), materia);
			}
			
			rs.close();
			
			rs=con.createStatement().executeQuery("SELECT * FROM libros");
			for(c=0;c<rs.getMetaData().getColumnCount();c++){
				System.out.println(rs.getMetaData().getColumnName(1+c));
			}
			while(rs.next()){
				Ejemplar ejemplar = new Ejemplar();
				
				ejemplar.setId(rs.getInt("IDLIBRO"));
				ejemplar.setTitulo(rs.getString("TITULO"));
				rs.getInt("CANTIDAD");
				ejemplar.setPrecio(rs.getFloat("PRECIO"));
				ejemplar.setEditorial(rs.getString("EDITORIAL"));
				ejemplar.setEdicion(rs.getString("EDICION"));
				ejemplar.setFechaPublicacion(Util.stringToDate(rs.getString("FECHA_PUBLICACION")+"0101","yyyyMMdd"));
				ejemplar.setFechaAlta(Util.stringToDate(rs.getString("FECHA_ALTA"),"yyyyMMdd"));
				ejemplar.setFechaModificacion(Util.stringToDate(rs.getString("FECHA_MODIFICACION"),"yyyyMMdd"));
				rs.getInt("ESTADO");
				rs.getString("PROPIEDADES");
				ejemplar.setObservaciones(rs.getString("OBSERVACIONES"));
				
			}
			System.out.println(con.getMetaData().getCatalogs());
			System.out.println(c);
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		
		
	}
}
