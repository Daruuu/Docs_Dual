package es.ici.cstic.tindaya.logic.impl;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import es.ici.cstic.tindaya.data.jpa.UsuarioRepository;
import es.ici.cstic.tindaya.model.Usuario;

@RunWith(MockitoJUnitRunner.class)
public class UsuarioServiceImplTest {
	
	@Mock
	UsuarioRepository usuarioRepositoryImpl;
	
	@InjectMocks
	UsuarioServiceImpl usuarioServiceImpl = new UsuarioServiceImpl();

	/**
	 * {@link UsuarioServiceImpl#getUsuarioById(Long)}
	 */
	@Test
	public void getUsuarioById_Test() {
		// given
		Long	usuarioId = 1L;
		Usuario mockUsuario01 = new Usuario();
		mockUsuario01.setUsuarioId(usuarioId);

		// when
		Mockito.when(usuarioRepositoryImpl.findById(any())).thenReturn(Optional.of(mockUsuario01));
		usuarioServiceImpl.getUsuarioById(usuarioId);
		
		// then
		verify(usuarioRepositoryImpl, atLeast(1)).findById(usuarioId);
	}
	
	/**
	 * {@link UsuarioServiceImpl#getUsuarioById(Long)}
	 */
	@Test
	public void getUsuarioById_ID_NULL_Test() {
		// given
		Usuario mockUsuario01 = new Usuario();
		mockUsuario01.setUsuarioId(null);

		// when
		Usuario usuarioReturn = usuarioServiceImpl.getUsuarioById(null);
		
		// then
		assertEquals(null, usuarioReturn);
	}

	/**
	 * {@link UsuarioServiceImpl#getUsuarioByUsername(String)}
	 */
	@Test
	public void	getUsuarioByUsername_Test() {
		// given
		String	username = "first user";
		Usuario mockUsuario01 = new Usuario();
		mockUsuario01.setNombreUsuario(username);
		
		// when
		Mockito.when(usuarioRepositoryImpl.getUsuarioByUsername(any())).thenReturn(mockUsuario01);
		usuarioServiceImpl.getUsuarioByUsername(username);
		
		// then
		verify(usuarioRepositoryImpl, atLeast(1)).getUsuarioByUsername(username);
		
	}
	
	/**
	 * {@link UsuarioServiceImpl#saveUsuario(Usuario)}
	 */
	@Test
	public void	saveUsuario_Test() {
		// given
		Usuario usuario01Mock = new Usuario();
		usuario01Mock.setUsuarioId(123L);
		
		// when
		usuarioServiceImpl.saveUsuario(usuario01Mock);
		
		// then
		verify(usuarioRepositoryImpl, atLeast(1)).saveAndFlush(usuario01Mock);
		
	}
	
	/**
	 * {@link UsuarioServiceImpl#getUsuariosBusqueda(String, String, String, String, java.util.Date, java.util.Date, Long, Long, String)}
	 */
	@Test
	public void	getUsuariosBusqueda_Test() {
		// given
		Map<String, Object> mapaParametros = new HashMap<String, Object>();
		String	queryString = "select usr from Usuario usr left outer join fetch usr.catAreaUsuario ";
		queryString += " where ";
		String	and = " and ";
		String	queryNif =
				" TRANSLATE(UPPER(usr.nif), 'ÁÉÍÓÚÄËÏÖÜÀÈÌÒÙÂÊÎÔÛ', 'AEIOUAEIOUAEIOUAEIOU') like TRANSLATE(UPPER(('%'||:nif||'%')), 'ÁÉÍÓÚÄËÏÖÜÀÈÌÒÙÂÊÎÔÛ', 'AEIOUAEIOUAEIOUAEIOU') ";
		String	queryNombreUsuario =
				" TRANSLATE(UPPER(usr.nombreUsuario), 'ÁÉÍÓÚÄËÏÖÜÀÈÌÒÙÂÊÎÔÛ', 'AEIOUAEIOUAEIOUAEIOU') like TRANSLATE(UPPER(('%'||:nombreUsuario||'%')), 'ÁÉÍÓÚÄËÏÖÜÀÈÌÒÙÂÊÎÔÛ', 'AEIOUAEIOUAEIOUAEIOU') ";
		String	queryNombre =
				" TRANSLATE(UPPER(usr.nombre), 'ÁÉÍÓÚÄËÏÖÜÀÈÌÒÙÂÊÎÔÛ', 'AEIOUAEIOUAEIOUAEIOU') like TRANSLATE(UPPER(('%'||:nombre||'%')), 'ÁÉÍÓÚÄËÏÖÜÀÈÌÒÙÂÊÎÔÛ', 'AEIOUAEIOUAEIOUAEIOU') ";
		String	parte2 = " and  TRANSLATE(UPPER(usr.apellidos), 'ÁÉÍÓÚÄËÏÖÜÀÈÌÒÙÂÊÎÔÛ', 'AEIOUAEIOUAEIOUAEIOU') like TRANSLATE(UPPER(('%'||:apellidos||'%')), "
				+ "'ÁÉÍÓÚÄËÏÖÜÀÈÌÒÙÂÊÎÔÛ', 'AEIOUAEIOUAEIOUAEIOU')  and  usr.fechaCreacion between :fechaCreacionInicio and :fechaCreacionFin  and  usr.catAreaUsuario.catalogoId ="
				+ " :areaUsuarioId  and  exists(select p.id from Perfil p where p.usuario.id = usr.id and p.tipoPerfil.id = :tipoPerfil)  "
				+ "and  exists(select p.id from Perfil p where p.usuario.id = usr.id and TRANSLATE(UPPER(p.recurso.nombre), 'ÁÉÍÓÚÄËÏÖÜÀÈÌÒÙÂÊÎÔÛ', 'AEIOUAEIOUAEIOUAEIOU') "
				+ "like TRANSLATE(UPPER(('%'||:nombreRecurso||'%')), 'ÁÉÍÓÚÄËÏÖÜÀÈÌÒÙÂÊÎÔÛ', 'AEIOUAEIOUAEIOUAEIOU')) ";
		String	queryFinal = queryString + queryNif + and + queryNombreUsuario + and + queryNombre + parte2;
		
		String	nif = "abcdef";
		String	nombreUsuario = "user01";
		String	nombre  = "hello";
		String	apellidos = "surname01";
		Date	fechaCreacionInicio = new Date();
		Date	fechaCreacionFin = new Date();
		Long	areaUsuarioId = 123L;
		Long	tipoPerfil = 1L;
		String	nombreRecurso = "nameRecurso01";

		mapaParametros.put("nif", nif);
		mapaParametros.put("nombreUsuario", nombreUsuario);
		mapaParametros.put("nombre", nombre);
		mapaParametros.put("apellidos", apellidos);
		mapaParametros.put("fechaCreacionInicio", fechaCreacionInicio);
		mapaParametros.put("fechaCreacionFin", fechaCreacionFin);
		mapaParametros.put("areaUsuarioId", areaUsuarioId);
		mapaParametros.put("tipoPerfil", tipoPerfil);
		mapaParametros.put("nombreRecurso", nombreRecurso);

		Mockito.when(usuarioRepositoryImpl.getListGenericoByQueryAndMap(anyString(), anyMap())).thenReturn(new ArrayList<Usuario>());

		// when
		usuarioServiceImpl.getUsuariosBusqueda(nif, nombreUsuario, nombre, apellidos,
				fechaCreacionInicio, fechaCreacionFin,
				areaUsuarioId, tipoPerfil, nombreRecurso);
		
		// then
		ArgumentCaptor<String>	queryCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<Map<String, Object>> paramsCaptor = ArgumentCaptor.forClass(Map.class);

		verify(usuarioRepositoryImpl, atLeast(1)).getListGenericoByQueryAndMap(queryCaptor.capture(), paramsCaptor.capture());

		String capturedQuery = queryFinal;
		Map<String, Object> capturedParams = paramsCaptor.getValue();

		// verify
		assertTrue(capturedQuery.contains("where"));
		assertEquals(mapaParametros, capturedParams);
	}
	
	/**
	 * {@link UsuarioServiceImpl#getUsuarioByNif(String)}
	 */
	@Test
	public void	getUsuariosByNif_Test() {
		// given
		String nif = "nif01";
		Usuario user01 = new Usuario();
		user01.setNif(nif);

		List<Usuario> listaUsuariosMock = new ArrayList<Usuario>();
		listaUsuariosMock.add(user01);
		
		// when
		Mockito.when(usuarioRepositoryImpl.getUsuarioByNif(anyString())).thenReturn(listaUsuariosMock);
		usuarioServiceImpl.getUsuarioByNif(nif);
		
		// then
		verify(usuarioRepositoryImpl, atLeast(1)).getUsuarioByNif(nif);
	}
	
	/**
	 * {@link UsuarioServiceImpl#getAllUsuarios()}
	 */
	@Test
	public void	getAllUsuarios_Test() {
		// given
		List<Usuario> listaUsuarios = new ArrayList<Usuario>();
		Usuario user01 = new Usuario();
		user01.setUsuarioId(1L);
		Usuario user02 = new Usuario();
		user02.setUsuarioId(12L);
		when(usuarioRepositoryImpl.getAllUsuarios()).thenReturn(listaUsuarios);

		// when
		List<Usuario> usuariosObtenidos = usuarioServiceImpl.getAllUsuarios();
		
		// then
		assertNotNull(usuariosObtenidos);
	}
	
	/**
	 * {@link UsuarioServiceImpl#txCompruebaBloqueoTiempo(Usuario, Integer)}
	 */
	@Test
	public void	txCompruebaBloqueoTiempo() {
		// given
		Usuario	usuario01 = new Usuario();
		Integer	dias = 2;
		
		Date	currentDate = new Date();
		Long	currentDateInMilliseconds = currentDate.getTime();
		
		// when
//		Mockito.when(usuarioRepositoryImpl. )
		// then
	}
	

	/**
	 * {@link UsuarioServiceImpl#txBloquearUsuarioBatch(Integer)}
	 */
	
	
	/**
	 * {@link UsuarioServiceImpl#getUsuariosBusquedaEnvioComunicacion(org.springframework.data.domain.Pageable, Map)}
	 */
	@Test
	public void	getUsuariosBusquedaEnvioComunicacion_Test() {
		// given
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		parameters.put("fehcaCreacionInicio", parameters);
		parameters.put("fechaCreacionFin", parameters);
		// when
		
		// then
		
	}
	
	
}
