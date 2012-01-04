package com.mimolibros.wasabi.test.client;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import com.mimolibros.wasabi.client.WasabiNetClient;
import com.mimolibros.wasabi.client.entities.Autor;
import com.mimolibros.wasabi.client.entities.Ejemplar;
import com.mimolibros.wasabi.client.entities.GrupoCriteriosBusquedaEjemplar;
import com.mimolibros.wasabi.client.entities.Materia;
import com.mimolibros.wasabi.client.entities.CriterioBusquedaEjemplar;
import com.mimolibros.wasabi.client.entities.ParametrosBusquedaEjemplar;
import com.mimolibros.wasabi.client.request.TestSetupRequest;
import com.mimolibros.wasabi.client.request.TestSetupResponse;
import com.mimolibros.wasabi.services.ServicioLibreria;

public class ServicioLibreriaTest {

	/**
	 * Borramos el contenido de la base de datos
	 * Esta llamada solo funcionara si el servicio web esta en modo TESTENV=true
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUp() throws Exception {
		WasabiNetClient clientSetup = new WasabiNetClient("http://localhost/Wasabi/invoke.php");
		TestSetupRequest request = new TestSetupRequest();
		TestSetupResponse response = clientSetup.sendRequest(TestSetupResponse.class,request);
		assertNotNull(response);
		assertEquals(true,response.getEstado().isOk());
	}


	@Test
	public void testNuevaMateria() {
		Materia materia = new Materia();
		materia.setNombre("nueva materia");
		Materia nueva = ServicioLibreria.nuevaMateria(materia);
		assertNotNull(nueva.getId());
		assertEquals("nueva materia", materia.getNombre());
		assert(nueva.getId().intValue()>0);
	}

	@Test
	public void testModificarMateria() {
		Materia materia = ServicioLibreria.buscarMateriasPorId(1);
		
		assertNotNull(materia);
		materia.setNombre("materia modificada");
		Materia modificada = ServicioLibreria.modificarMateria(materia);
		
		assertEquals(materia.getId(), modificada.getId());
		assertEquals(materia.getNombre(), modificada.getNombre());
	}

	@Test
	public void testBuscarMateriasPorNombre() {
		List<Materia> materias = null;
		materias = ServicioLibreria.buscarMateriasPorNombre("%");
		assertNotNull(materias);
		Assert.assertTrue(materias.size()>0);
	}

	@Test
	public void testBuscarMateriasPorId() {
		Materia buscada = ServicioLibreria.buscarMateriasPorId(1);
		assertNotNull(buscada);
		assertEquals(1,buscada.getId().intValue());
	}

	@Test
	public void testNuevoAutor() {
		Autor object = new Autor();
		object.setNombre("nuevo autor");		
		Autor _new = ServicioLibreria.nuevoAutor(object);
		assertNotNull(_new);
		assertNotNull(_new.getId());
		Assert.assertTrue(_new.getId()>0);
	}

	@Test
	public void testModificarAutor() {
		Autor object = ServicioLibreria.buscarAutoresPorId(1);
		assertNotNull(object);
		object.setNombre("autor modificado");
		Autor _mod = ServicioLibreria.modificarAutor(object);
		assertNotNull(_mod);
		assertEquals(object.getId().intValue(), _mod.getId().intValue());
		assertEquals(object.getNombre(), _mod.getNombre());
	}

	@Test
	public void testBuscarAutoresPorNombre() {
		List<Autor> lista= null;
		lista = ServicioLibreria.buscarAutoresPorNombre("%");
		assertNotNull(lista);
		Assert.assertTrue(lista.size()>0);
	}

	@Test
	public void testBuscarAutoresPorId() {
		Autor object = ServicioLibreria.buscarAutoresPorId(1);
		assertNotNull(object);
		assertEquals(1,object.getId().intValue());
	}
	
	
	@Test
	public void testAltaEjemplar() {
		Ejemplar object = new Ejemplar();
		object.setTitulo("nuevo ejemplar");
		object.setReferencia("rf1");
		Autor autor = new Autor();
		autor.setId(1);
		Materia materia=new Materia();
		materia.setId(1);
		object.setAutores(new ArrayList<Autor>());
		object.getAutores().add(autor);
		object.setMaterias(new ArrayList<Materia>());
		object.getMaterias().add(materia);
		
		Ejemplar _new = ServicioLibreria.altaEjemplar(object);
		assertNotNull(_new);
		assertNotNull(_new.getId());
		Assert.assertTrue(_new.getId().intValue()>0);
	}
	
	@Test
	public void testModificarEjemplar() {
		ParametrosBusquedaEjemplar param = new ParametrosBusquedaEjemplar();
		param.setId(1);
		
		Ejemplar object = ServicioLibreria.buscarEjemplares(param).getEjemplares().get(0);
		
		assertNotNull(object);
		
		object.getAutores().clear();
		Autor autor=new Autor(3,"pepe");
		object.getAutores().add(autor);
		object.getMaterias().clear();
		Materia materia=new Materia(3,"pepa");
		object.getMaterias().add(materia);
		
		object.setTitulo("Modificado");
		
		Ejemplar _mod = ServicioLibreria.modificarEjemplar(object);
		
		assertNotNull(_mod);
		assertEquals("Modificado", _mod.getTitulo());
		assertEquals(1, _mod.getAutores().size());
		assertEquals(3, _mod.getAutores().get(0).getId().intValue());
		assertEquals(1, _mod.getMaterias().size());
		assertEquals(3, _mod.getMaterias().get(0).getId().intValue());
		
	}
	
	@Test
	public void testBuscarMateriasPorIdEjemplar() {
		List<Materia> materias = null;
		materias = ServicioLibreria.buscarMateriasPorIdEjemplar(1);
		assertNotNull(materias);
		Assert.assertTrue(materias.size()>0);
	}
	
	@Test
	public void testBuscarAutoresPorIdEjemplar() {
		List<Autor> autores = null;
		autores = ServicioLibreria.buscarAutoresPorIdEjemplar(1);
		assertNotNull(autores);
		Assert.assertTrue(autores.size()>0);
	}
	
	@Test
	public void testBuscarEjemplares(){
		ParametrosBusquedaEjemplar param = new ParametrosBusquedaEjemplar();
		param.setGrupos(new ArrayList<GrupoCriteriosBusquedaEjemplar>());
		param.getGrupos().add(new GrupoCriteriosBusquedaEjemplar());
		param.getGrupos().get(0).setCriterios(new ArrayList<CriterioBusquedaEjemplar>());
		param.getGrupos().get(0).getCriterios().add(new CriterioBusquedaEjemplar("referencia","%",CriterioBusquedaEjemplar.CRT_CONTIENE,false));
		param.getGrupos().get(0).setInvertir(false);
		param.setPagina(1);
		param.setResultadosPorPagina(2);
		
		List<Ejemplar> list = ServicioLibreria.buscarEjemplares(param).getEjemplares();
		
		assertNotNull(list);
		Assert.assertTrue(list.size()>0);
		Assert.assertTrue(list.get(0).getAutores().size()>0);
		Assert.assertTrue(list.get(0).getMaterias().size()>0);
	}
	
	@Test
	public void testBuscarEjemplarPorId(){
		Ejemplar ejemplar = ServicioLibreria.buscarEjemplarPorId(1);
		assertNotNull(ejemplar);
		assertEquals(1, ejemplar.getId().intValue());
	}
	
}
	
