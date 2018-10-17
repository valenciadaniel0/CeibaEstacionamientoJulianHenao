package co.com.ceiba.estacionamiento.julian.henao.integrationTest.ConsultaServicios;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

import co.com.ceiba.estacionamiento.julian.henao.builderstest.ParqueaderoEspacioDisponibleBuilder;
import co.com.ceiba.estacionamiento.julian.henao.builderstest.TipoVehiculoBuilder;
import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloParqueaderoEspacioDisponible;
import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloTipoVehiculo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestParqueaderoEspacioDisponible {

	@Autowired
	private TestRestTemplate restTemplate;

	private String url;
	
	@Before
	public void inicializandoUrl(){
		url = "/estacionamiento/espacio";
	}
	
	@Test
	public void crearEspacioExitoso() {
		ModeloTipoVehiculo tipoVehiculoAuto = new TipoVehiculoBuilder().conId(1).conDescripcion("Automovil").build();
		ModeloParqueaderoEspacioDisponible espacio =  new ParqueaderoEspacioDisponibleBuilder().conId(1).conTipoVehiculo(tipoVehiculoAuto).conEspacioActual(0).conLimiteEspacio(20).build();

		ResponseEntity<String> responseEntity = restTemplate.postForEntity(url,
				espacio, String.class);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals("Espacio Ingresado exitosamente", responseEntity.getBody());
	}

	@Test
	public void crearEspacioNulo() {
		ResponseEntity<String> responseEntity = restTemplate.postForEntity("/estacionamiento/espacio", null,
				String.class);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		//assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, responseEntity.getStatusCode());
	}
	
	@Test
	@SqlGroup(@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:iniciandoBD.sql"))
	public void actualizarEspacioAutomovil() {
		int id = 1;
		int nuevoEspacioLimite = 100;
		ModeloTipoVehiculo tipoVehiculoAuto = new TipoVehiculoBuilder().conId(id).conDescripcion("Automovil").build();
		ModeloParqueaderoEspacioDisponible espacio =  new ParqueaderoEspacioDisponibleBuilder().conId(id).conTipoVehiculo(tipoVehiculoAuto).conEspacioActual(3).conLimiteEspacio(nuevoEspacioLimite).build();
		
		
		HttpEntity<ModeloParqueaderoEspacioDisponible> actualizarEspacio = new HttpEntity<ModeloParqueaderoEspacioDisponible>(espacio);
		
		ResponseEntity<String> responseEntity = restTemplate.exchange(
				  url,
				  HttpMethod.PUT,
				  actualizarEspacio, String.class); 
		
		assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
		
		ResponseEntity<ModeloParqueaderoEspacioDisponible> response = 
				restTemplate.exchange(
						  url+"/"+id,
						  HttpMethod.GET,
						  null,
						  new ParameterizedTypeReference<ModeloParqueaderoEspacioDisponible>(){});				
				
		assertEquals(nuevoEspacioLimite, response.getBody().getLimiteEspacio());
		
	}
	

	@Test
	@SqlGroup(@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:iniciandoBD.sql"))
	public void obtenerEspacios() {		
		int id = 2;
		int espacioLimite = 10;
		ResponseEntity<ModeloParqueaderoEspacioDisponible> response = 
				restTemplate.exchange(
						  url+"/"+id,
						  HttpMethod.GET,
						  null,
						  new ParameterizedTypeReference<ModeloParqueaderoEspacioDisponible>(){});				
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(espacioLimite, response.getBody().getLimiteEspacio());
	}
}
