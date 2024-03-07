## Tecnica Given, When, Then

La técnica de "Given, When, Then" (Dado, Cuando, Entonces) es una técnica 
popular para escribir pruebas unitarias y especificaciones de comportamiento.
Proporciona una estructura clara para organizar y escribir tus pruebas.
Aquí te explico cómo aplicar esta técnica en JUnit 4:

### Given (Dado): 
En esta sección, configuras el estado inicial necesario para tu prueba.
Esto puede incluir la creación de objetos, la inicialización de variables
y la configuración de mocks si estás utilizando librerías de mocking como Mockito.

### When (Cuando):
Esta sección contiene el código que estás probando, es decir, la acción que quieres probar.
Por lo general, esto implica llamar a un método o una función con ciertos parámetros o un cierto estado.

### Then (Entonces):
Aquí verificas el resultado de la acción realizada en la sección "When".
Puedes verificar el valor de retorno de un método, el estado de un 
objeto después de la acción, o los efectos secundarios de la acción 
(como cambios en el estado de otros objetos).

Veamos un ejemplo de cómo se aplicaría la técnica "Given, When, Then" en un test de JUnit 4:

## Que es un mock? y un Spy?
Tanto Mock como Spy son usados en testing para aislar el sistema a testear(SUT),
divide el codigo a ser testeado de las posibles dependecias que pueden existir (otra clase, una conexion a db, etc).

### Mock
Es un **simulador** o un doble de un objeto. Contiene todos los metodos del
objecto original. Pero son falsos(no contienen implementaciones).
Cuando se usa un Mock es obligatorio definir el comportamiento de sus metodos.

``` java
@Test
public void whenITryToCutACarrotThenTrue()
{
   Knife knife = mock( Knife.class );
   doReturn( true ).when( knife ).cut( "carrot" );

   assertEquals( knife.cut("carrot"), true );
}

@Test
public void	whenITryToCutWaterThenFalse()
{
   Knife knife = mock( Knife.class );
   doReturn( false ).when( knife ).cut( "water" );

   assertEquals( knife.cut("water", false);
}
```

Tenemos una clase Knife con un método: cut(), pero no estamos interesados
realmente en la funcionalidad que esté implementada en el método (si es que hay alguna),
así que usamos un "doReturn(...)" para simular la funcionalidad
y devolver el valor que deseemos.

### Spy

Spy es como un Mock. La diferencia es que podemos utilizarlo como un Objecto real
y que pueda llamar a las implementaciones de los emtodos reales.
Tambien tenemos la opcion de simular la funcionalidad de cual metodo, como con el _Mock_ .


Clase Knife:

``` java
public class Knife{
	...
public boolean cut( String something )
{
     return true;
}
	...
}
``` 
Los test podrian ser tal que asi:

En este caso, el objeto "knife" funciona como un objeto normal y
cuando llamamos al método "cut()",
estamos llamando al método real de la clase "Knife".
Sin embargo, debido a que este método siempre devuelve "true".
``` java
@Test
public void whenITryToCutACarrotThenTrue()
{
   Knife knife = spy( Knife.class );
   
   assertEquals( knife.cut("carrot"), true );
}
```

En el segundo test necesitamos simular su comportamiento para pasar el test,
ya que necesitamos un "false", así que usamos el "doReturn(...)".

``` java
@Test
public void whenITryToCutWaterThenFalse()
{
   Knife knife = spy( Knife.class );
   doReturn( false ).when( knife ).cut( "water" );

   assertEquals( knife.cut("water", false);
}
```

La diferencia es que con Mock es obligatorio simular los comportamientos de los métodos.
Los Mocks son muy usados cuando se programa haciendo TDD (Test Driven Development o Desarrollo basado en tests),
ya que no requieren de que haya ninguna funcionalidad implementada.
Sin embargo, Spy, aunque puede simular el comportamiento de los métodos al igual que Mock,
también nos da la oportunidad de llamar a la implementación real de los métodos del objeto.
Es usado cuando nos interesa mantener la consistencia de los métodos ya implementados
con la nueva parte que estemos desarrollando y realizando TDD.



---------------------------------------------------------------------
``` java
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class CalculatorTest {

    @Test
    public void testAdd() {
        // Given
        Calculator calculator = new Calculator();
        int a = 2;
        int b = 3;
        
        // When
        int result = calculator.add(a, b);
        
        // Then
        assertEquals(5, result);
    }
}
```

## Listado de diferentes casos de testing en Java utilizando JUnit 4 y Mockito.

Primero el metodo el cual queremos generar el test y despues el test de como se ha creado.

``` java
```
El siguiente test pertenece a un servicio usuarioServicioImpl();

``` java
public class UsuarioServiceImpl implements UsuarioService{


	private static final long serialVersionUID = 9166747878883420728L;
	private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioServiceImpl.class);
	private static final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000;

    @Override
	public Usuario getUsuarioById(Long usuarioId) {
		if(usuarioId != null){
			Usuario usr =  usuarioRepositoryImpl.findById(usuarioId).orElse(new Usuario());
			return usr;
		}else{
			return null;
		}
	}
```

``` java
@Test
	public void getUsuarioById_Test() {
		// given
		Long usuarioId = 1L;
		Usuario mockUsuario01 = new Usuario();
		mockUsuario01.setUsuarioId(usuarioId);

		// when
		Mockito.when(usuarioRepositoryImpl.findById(any())).thenReturn(Optional.of(mockUsuario01));
		usuarioServiceImpl.getUsuarioById(usuarioId);

		// then
		verify(usuarioRepositoryImpl, atLeast(1)).findById(usuarioId);
	}
```


Metodo que retorna un Usuario.

``` java
public Usuario getUsuarioByUsername(String username){
		return usuarioRepositoryImpl.getUsuarioByUsername(username);
	}
```

``` java
@Test
	public void getUsuarioByUsername_Test() {
		// given
		String username = "first user";
		Usuario mockUsuario01 = new Usuario();
		mockUsuario01.setNombreUsuario(username);

		// when
		Mockito.when(usuarioRepositoryImpl.getUsuarioByUsername(any())).thenReturn(mockUsuario01);
		usuarioServiceImpl.getUsuarioByUsername(username);

		// then
		verify(usuarioRepositoryImpl, atLeast(1)).getUsuarioByUsername(username);

	}
```



``` java

	@Override
	public List<Usuario> getUsuariosBusqueda(String nif, String nombreUsuario,
			String nombre, String apellidos, Date fechaCreacionInicio, Date fechaCreacionFin, Long areaUsuarioId, Long tipoPerfil, String nombreRecurso) {

		Map<String, Object> mapaParametros = new HashMap<String, Object>();
		String queryString = "select usr from Usuario usr left outer join fetch usr.catAreaUsuario ";
		String queryWhere = null;

		if (StringUtils.isNotBlank(nif)){
			queryWhere = " where ";

			queryWhere = queryWhere + " TRANSLATE(UPPER(usr.nif), 'ÁÉÍÓÚÄËÏÖÜÀÈÌÒÙÂÊÎÔÛ', 'AEIOUAEIOUAEIOUAEIOU') like TRANSLATE(UPPER(('%'||:nif||'%')), 'ÁÉÍÓÚÄËÏÖÜÀÈÌÒÙÂÊÎÔÛ', 'AEIOUAEIOUAEIOUAEIOU') ";
			mapaParametros.put("nif", nif);
		}
		if (StringUtils.isNotBlank(nombreUsuario)){
			if (queryWhere == null){
				queryWhere = " where ";
			}else{
				queryWhere = queryWhere + " and ";
			}
			queryWhere = queryWhere + " TRANSLATE(UPPER(usr.nombreUsuario), 'ÁÉÍÓÚÄËÏÖÜÀÈÌÒÙÂÊÎÔÛ', 'AEIOUAEIOUAEIOUAEIOU') like TRANSLATE(UPPER(('%'||:nombreUsuario||'%')), 'ÁÉÍÓÚÄËÏÖÜÀÈÌÒÙÂÊÎÔÛ', 'AEIOUAEIOUAEIOUAEIOU') ";
			mapaParametros.put("nombreUsuario", nombreUsuario);
		}
		if (StringUtils.isNotBlank(nombre)){
			if (queryWhere == null){
				queryWhere = " where ";
			}else{
				queryWhere = queryWhere + " and ";
			}
			queryWhere = queryWhere + " TRANSLATE(UPPER(usr.nombre), 'ÁÉÍÓÚÄËÏÖÜÀÈÌÒÙÂÊÎÔÛ', 'AEIOUAEIOUAEIOUAEIOU') like TRANSLATE(UPPER(('%'||:nombre||'%')), 'ÁÉÍÓÚÄËÏÖÜÀÈÌÒÙÂÊÎÔÛ', 'AEIOUAEIOUAEIOUAEIOU') ";
			mapaParametros.put("nombre", nombre);
		}
		if (StringUtils.isNotBlank(apellidos)){
			if (queryWhere == null){
				queryWhere = " where ";
			}else{
				queryWhere = queryWhere + " and ";
			}
			queryWhere = queryWhere + " TRANSLATE(UPPER(usr.apellidos), 'ÁÉÍÓÚÄËÏÖÜÀÈÌÒÙÂÊÎÔÛ', 'AEIOUAEIOUAEIOUAEIOU') like TRANSLATE(UPPER(('%'||:apellidos||'%')), 'ÁÉÍÓÚÄËÏÖÜÀÈÌÒÙÂÊÎÔÛ', 'AEIOUAEIOUAEIOUAEIOU') ";
			mapaParametros.put("apellidos", apellidos);
		}
		if (fechaCreacionInicio != null
				&& fechaCreacionFin != null){
			if (queryWhere == null){
				queryWhere = " where ";
			}else{
				queryWhere = queryWhere + " and ";
			}
			queryWhere = queryWhere + " usr.fechaCreacion between :fechaCreacionInicio and :fechaCreacionFin ";
			mapaParametros.put("fechaCreacionInicio", fechaCreacionInicio);
			mapaParametros.put("fechaCreacionFin", fechaCreacionFin);
		}
		if (areaUsuarioId != null){
			if (queryWhere == null){
				queryWhere = " where ";
			}else{
				queryWhere = queryWhere + " and ";
			}
			queryWhere = queryWhere + " usr.catAreaUsuario.catalogoId = :areaUsuarioId ";
			mapaParametros.put("areaUsuarioId", areaUsuarioId);
		}
		if (tipoPerfil != null){
			if (queryWhere == null){
				queryWhere = " where ";
			}else{
				queryWhere = queryWhere + " and ";
			}
			queryWhere = queryWhere + " exists(select p.id from Perfil p where p.usuario.id = usr.id and p.tipoPerfil.id = :tipoPerfil) ";
			mapaParametros.put("tipoPerfil", tipoPerfil);
		}
		if (nombreRecurso != null){
			if (queryWhere == null){
				queryWhere = " where ";
			}else{
				queryWhere = queryWhere + " and ";
			}
			queryWhere = queryWhere + " exists(select p.id from Perfil p where p.usuario.id = usr.id and TRANSLATE(UPPER(p.recurso.nombre), 'ÁÉÍÓÚÄËÏÖÜÀÈÌÒÙÂÊÎÔÛ', 'AEIOUAEIOUAEIOUAEIOU') like TRANSLATE(UPPER(('%'||:nombreRecurso||'%')), 'ÁÉÍÓÚÄËÏÖÜÀÈÌÒÙÂÊÎÔÛ', 'AEIOUAEIOUAEIOUAEIOU')) ";
			mapaParametros.put("nombreRecurso", nombreRecurso);
		}


		if (queryWhere != null){
			queryString = queryString + queryWhere;
		}

		return usuarioRepositoryImpl.getListGenericoByQueryAndMap(queryString, mapaParametros);
	}

```




en este test lo que hemos usado para verificar los datos que se correctos y existan ha sido utilizar un ArgumentCaptor, que se utiliza para capturar un tipo de dato y comprobarlo .

``` java
@Test
	public void getUsuariosBusqueda_Test() {
		// given
		Map<String, Object> mapaParametros = new HashMap<String, Object>();
		String queryString = "select usr from Usuario usr left outer join fetch usr.catAreaUsuario ";
		queryString += " where ";
		String and = " and ";
		String queryNif = " TRANSLATE(UPPER(usr.nif), 'ÁÉÍÓÚÄËÏÖÜÀÈÌÒÙÂÊÎÔÛ', 'AEIOUAEIOUAEIOUAEIOU') like TRANSLATE(UPPER(('%'||:nif||'%')), 'ÁÉÍÓÚÄËÏÖÜÀÈÌÒÙÂÊÎÔÛ', 'AEIOUAEIOUAEIOUAEIOU') ";
		String queryNombreUsuario = " TRANSLATE(UPPER(usr.nombreUsuario), 'ÁÉÍÓÚÄËÏÖÜÀÈÌÒÙÂÊÎÔÛ', 'AEIOUAEIOUAEIOUAEIOU') like TRANSLATE(UPPER(('%'||:nombreUsuario||'%')), 'ÁÉÍÓÚÄËÏÖÜÀÈÌÒÙÂÊÎÔÛ', 'AEIOUAEIOUAEIOUAEIOU') ";
		String queryNombre = " TRANSLATE(UPPER(usr.nombre), 'ÁÉÍÓÚÄËÏÖÜÀÈÌÒÙÂÊÎÔÛ', 'AEIOUAEIOUAEIOUAEIOU') like TRANSLATE(UPPER(('%'||:nombre||'%')), 'ÁÉÍÓÚÄËÏÖÜÀÈÌÒÙÂÊÎÔÛ', 'AEIOUAEIOUAEIOUAEIOU') ";
		String parte2 = " and  TRANSLATE(UPPER(usr.apellidos), 'ÁÉÍÓÚÄËÏÖÜÀÈÌÒÙÂÊÎÔÛ', 'AEIOUAEIOUAEIOUAEIOU') like TRANSLATE(UPPER(('%'||:apellidos||'%')), "
				+ "'ÁÉÍÓÚÄËÏÖÜÀÈÌÒÙÂÊÎÔÛ', 'AEIOUAEIOUAEIOUAEIOU')  and  usr.fechaCreacion between :fechaCreacionInicio and :fechaCreacionFin  and  usr.catAreaUsuario.catalogoId ="
				+ " :areaUsuarioId  and  exists(select p.id from Perfil p where p.usuario.id = usr.id and p.tipoPerfil.id = :tipoPerfil)  "
				+ "and  exists(select p.id from Perfil p where p.usuario.id = usr.id and TRANSLATE(UPPER(p.recurso.nombre), 'ÁÉÍÓÚÄËÏÖÜÀÈÌÒÙÂÊÎÔÛ', 'AEIOUAEIOUAEIOUAEIOU') "
				+ "like TRANSLATE(UPPER(('%'||:nombreRecurso||'%')), 'ÁÉÍÓÚÄËÏÖÜÀÈÌÒÙÂÊÎÔÛ', 'AEIOUAEIOUAEIOUAEIOU')) ";
		String queryFinal = queryString + queryNif + and + queryNombreUsuario + and + queryNombre + parte2;

		String nif = "abcdef";
		String nombreUsuario = "user01";
		String nombre = "hello";
		String apellidos = "surname01";
		Date fechaCreacionInicio = new Date();
		Date fechaCreacionFin = new Date();
		Long areaUsuarioId = 123L;
		Long tipoPerfil = 1L;
		String nombreRecurso = "nameRecurso01";

		mapaParametros.put("nif", nif);
		mapaParametros.put("nombreUsuario", nombreUsuario);
		mapaParametros.put("nombre", nombre);
		mapaParametros.put("apellidos", apellidos);
		mapaParametros.put("fechaCreacionInicio", fechaCreacionInicio);
		mapaParametros.put("fechaCreacionFin", fechaCreacionFin);
		mapaParametros.put("areaUsuarioId", areaUsuarioId);
		mapaParametros.put("tipoPerfil", tipoPerfil);
		mapaParametros.put("nombreRecurso", nombreRecurso);

		Mockito.when(usuarioRepositoryImpl.getListGenericoByQueryAndMap(anyString(), anyMap()))
				.thenReturn(new ArrayList<Usuario>());

		// when
		usuarioServiceImpl.getUsuariosBusqueda(nif, nombreUsuario, nombre, apellidos, fechaCreacionInicio,
				fechaCreacionFin, areaUsuarioId, tipoPerfil, nombreRecurso);

		// then
		ArgumentCaptor<String> queryCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<Map<String, Object>> paramsCaptor = ArgumentCaptor.forClass(Map.class);

		verify(usuarioRepositoryImpl, atLeast(1)).getListGenericoByQueryAndMap(queryCaptor.capture(),
				paramsCaptor.capture());

		String capturedQuery = queryFinal;
		Map<String, Object> capturedParams = paramsCaptor.getValue();

		// verify
		assertTrue(capturedQuery.contains("where"));
		assertEquals(mapaParametros, capturedParams);
	}
```

### Ejemplo 3:
En este ejemplo nos dan como parametros un Pageable y un mapa filtros,
en el testing cada bloque de un if-else normalmente suelo ser un 
test diferente para que el coverage abarque un 100 %.

``` java
 @Override
	public Page<Usuario> getPageBuscadorUsuarios(Pageable request, Map<ParametrosDatamodelEnum, Object> filtros) {
		Map<String,Object> mapaParametros = new HashMap<String, Object>();
		String queryString = "select usr from Usuario usr left outer join fetch usr.catAreaUsuario ";
		String queryWhere = null;

		if(filtros.containsKey(ParametrosDatamodelEnum.NOMBRE_USUARIO)
				&& filtros.get(ParametrosDatamodelEnum.NOMBRE_USUARIO) != null){
			if (queryWhere == null){
				queryWhere = " where ";
			}else{
				queryWhere = queryWhere + " and ";
			}
			queryWhere = queryWhere + " TRANSLATE(UPPER(usr.nombreUsuario), 'ÁÉÍÓÚÄËÏÖÜÀÈÌÒÙÂÊÎÔÛ', 'AEIOUAEIOUAEIOUAEIOU') like TRANSLATE(UPPER(('%'||:nombreUsuario||'%')), 'ÁÉÍÓÚÄËÏÖÜÀÈÌÒÙÂÊÎÔÛ', 'AEIOUAEIOUAEIOUAEIOU') ";
			mapaParametros.put("nombreUsuario", filtros.get(ParametrosDatamodelEnum.NOMBRE_USUARIO));
		}
		if (queryWhere != null){
			queryString = queryString + queryWhere;
		}

		Page<Usuario> usuarios = usuarioRepositoryImpl.getPageGenericoByQueryAndMap(queryString, mapaParametros, request);
		return usuarios;
	}
```

``` java
	/**
	 * {@link UsuarioServiceImpl#getPageBuscadorUsuarios(Pageable, Map)}
	 */
	@Test
	public void	getPageBuscadorUsuarios_USUARIO_NIF_Test01() {
		
		// given
		Pageable request = Pageable.ofSize(1);
		Map<ParametrosDatamodelEnum, Object> filtros = new HashMap<ParametrosDatamodelEnum, Object>();
		Map<String, Object> mapaParametros = new HashMap<String, Object>();

		String	queryString = "select usr from Usuario usr left outer join fetch usr.catAreaUsuario ";
		String	queryWhere = null;
		queryWhere = queryString + queryWhere + " TRANSLATE(UPPER(usr.nif), 'ÁÉÍÓÚÄËÏÖÜÀÈÌÒÙÂÊÎÔÛ', 'AEIOUAEIOUAEIOUAEIOU') like TRANSLATE(UPPER(('%'||:nif||'%')), 'ÁÉÍÓÚÄËÏÖÜÀÈÌÒÙÂÊÎÔÛ', 'AEIOUAEIOUAEIOUAEIOU') ";
		String	usuarioNIF = "1234G";
		
		filtros.put(ParametrosDatamodelEnum.USUARIO_NIF, usuarioNIF);
		mapaParametros.put("nif", filtros.get(ParametrosDatamodelEnum.USUARIO_NIF));

		// SIMULAR UNA LISTA DE USUARIOS QUE COINCIDA CON FILTROS
		Usuario user01 = new Usuario();
		user01.setUsuarioId(12L);

		Usuario user02 = new Usuario();
		user02.setUsuarioId(34L);

		List<Usuario> usuariosMock = new ArrayList<Usuario>();
		usuariosMock.add(user01);
		usuariosMock.add(user02);
		Page<Usuario> usuariosReturn = new PageImpl<Usuario>(usuariosMock);
		
		// when
		Mockito.when(usuarioRepositoryImpl.getPageGenericoByQueryAndMap(anyString(), anyMap(), any())).thenReturn(usuariosReturn);
		Page<Usuario> usuariosEncontrados = usuarioServiceImpl.getPageBuscadorUsuarios(request, filtros);
		
		// then
		assertEquals(2, usuariosEncontrados.getTotalElements());
		assertTrue(queryFinal.contains(" where "));
	}
```



