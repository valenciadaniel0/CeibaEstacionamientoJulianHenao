package co.com.ceiba.estacionamiento.julian.henao.integrationTest.ConsultaServicios;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

import co.com.ceiba.estacionamiento.julian.henao.builderstest.TipoVehiculoBuilder;
import co.com.ceiba.estacionamiento.julian.henao.builderstest.VehiculoBuilder;
import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloTipoVehiculo;
import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloVehiculo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestVehiculo {

	@Autowired
	private TestRestTemplate restTemplate;

	String url;
	
	@Before
	public void datos(){
		url = "/estacionamiento/vehiculos";
	}
	
	@Test
	@SqlGroup(@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:iniciandoBD.sql"))
	public void crearVehiculoExitoso() {

		ModeloTipoVehiculo tipoVehiculoMoto = new TipoVehiculoBuilder().conId(2).conDescripcion("Moto").build();
		ModeloVehiculo vehiculo = new VehiculoBuilder().conPlaca("HH2").conTipoVehiculo(tipoVehiculoMoto)
				.conCilindraje(100).build();
		ResponseEntity<String> responseEntity = restTemplate.postForEntity(url,
				vehiculo, String.class);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals("Vehiculo Ingresado exitosamente", responseEntity.getBody());
	}

	@Test
	public void crearVehiculoNulo() {
		ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, null,
				String.class);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		//assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, responseEntity.getStatusCode());
	}

	@Test
	@SqlGroup(@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:iniciandoBD.sql"))
	public void obtenerVehiculos() {		
		int cantidadVehiculosEnBD = 6;
		ResponseEntity<List<ModeloVehiculo>> response = 
				restTemplate.exchange(
						  url,
						  HttpMethod.GET,
						  null,
						  new ParameterizedTypeReference<List<ModeloVehiculo>>(){});				
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(cantidadVehiculosEnBD, response.getBody().size());
	}
}
