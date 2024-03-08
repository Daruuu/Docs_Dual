
## Partes de un Test en un projecto con MVC

### Anotaciones necesarioas en testing basico

Partes del proyecto:
- ObjectServiceImpl();
- ObjectServiceImplTest();

``` java
public class ViolenciaGeneroSocServiceImpl implements ViolenciaGeneroSocService{

	// private static final long serialVersionUID = -7974899279914725973L;

	@Autowired
	private ViolenciaGeneroSocRepository violenciaGeneroSocRepositoryImpl;

	@Override
	public ViolenciaGeneroSoc getViolenciaGeneroSocById(Long violenciaGeneroSocid) {
		return (violenciaGeneroSocid != null) ? violenciaGeneroSocRepositoryImpl.findById(violenciaGeneroSocid).orElse(null) : null;
	}

	@Override
	public void saveViolenciaGeneroSoc(ViolenciaGeneroSoc violenciaGeneroSoc) {
		violenciaGeneroSocRepositoryImpl.save(violenciaGeneroSoc);
	}
}
```

Parte del test de este proyecto:

Un test ha de tener las siguientes anotaciones:
- **@RunWith()**
    - Se utiliza en pruebas de JUnit para especificar qué clase debe ejecutar las pruebas en lugar de la clase por defecto de JUnit.
    - Esto permite la integración de diferentes marcos y extensiones de pruebas en JUnit.

- **@RunWith(MockitoJUnitRunner.class)**
    - Este corredor permite la inicialización automática de los mocks y los objetos anotados
    con @Mock, @Spy, @InjectMocks, etc.
    - Esto evita tener que llamar a MockitoAnnotations.initMocks() explícitamente en el método setUp().

- **@Mock**
    - se utiliza para crear un mock de una clase o interfaz.
    - se puede crear objetos simulados que se comporten como las clases o interfaces reales
    pero con un comportamiento controlado por ti en tus pruebas.
- **@InjectMocks**
    - se utiliza para inyectar automáticamente las dependencias de un objeto
    mock o spy dentro del objeto que estás probando.
    - son las dependencias que tiene un objeto para poder utilizarlo.

``` java
@RunWith(MockitoJUnitRunner.class)
public class ViolenciaGeneroSocServiceImplTest {
	
	@Mock
	ViolenciaGeneroSocRepository	violenciaGeneroSocRepositoryImpl;
	
	@InjectMocks
	ViolenciaGeneroSocServiceImpl	violenciaGeneroSocServiceImpl = new ViolenciaGeneroSocServiceImpl();

/**
 * {@link ViolenciaGeneroSocServiceImpl#getViolenciaGeneroSocById(Long)}
 */
	@Test
	public void	getViolenciaGeneroSocById_Test() {
		// given
		Long	violenciaGeneroSocid = 12L;
		ViolenciaGeneroSoc	violenciaGeneroSocEsperado = new ViolenciaGeneroSoc();
		violenciaGeneroSocEsperado.setViolenciaId(violenciaGeneroSocid);
		
		// when
		when(violenciaGeneroSocRepositoryImpl.findById(anyLong())).thenReturn(Optional.of(violenciaGeneroSocEsperado));
		ViolenciaGeneroSoc violenciaGeneroSocResult= violenciaGeneroSocServiceImpl.getViolenciaGeneroSocById(violenciaGeneroSocid);
		
		// then
		verify(violenciaGeneroSocRepositoryImpl, atLeast(1)).findById(violenciaGeneroSocid);
		assertEquals(violenciaGeneroSocEsperado, violenciaGeneroSocResult);
	}

/**
 * {@link ViolenciaGeneroSocServiceImpl#getViolenciaGeneroSocById(Long)}
 */
	@Test
	public void	saveViolenciaGeneroSoc_Test() {
		// given
		ViolenciaGeneroSoc	violenciaGeneroSoc = new ViolenciaGeneroSoc();
		violenciaGeneroSoc.setViolenciaId(123L);
		
		// when
		violenciaGeneroSocServiceImpl.saveViolenciaGeneroSoc(violenciaGeneroSoc);		
		
		//then
		verify(violenciaGeneroSocRepositoryImpl, atLeast(1)).save(violenciaGeneroSoc);
	}
}
```

