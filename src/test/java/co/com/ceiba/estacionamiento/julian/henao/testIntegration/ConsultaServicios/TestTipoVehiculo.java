package co.com.ceiba.estacionamiento.julian.henao.testIntegration.ConsultaServicios;

import static org.junit.Assert.assertEquals;

import java.util.List;

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
import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloTipoVehiculo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestTipoVehiculo {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void crearTipoVehiculoExitoso() {

		ModeloTipoVehiculo tipoVehiculoMoto = new TipoVehiculoBuilder().conId(2).conDescripcion("Moto").build();

		ResponseEntity<String> responseEntity = restTemplate.postForEntity("/estacionamiento/tipo_vehiculo",
				tipoVehiculoMoto, String.class);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals("Tipo de vehiculo Ingresado exitosamente", responseEntity.getBody());
	}

	@Test
	public void crearTipoVehiculoNulo() {
		ResponseEntity<String> responseEntity = restTemplate.postForEntity("/estacionamiento/tipo_vehiculo", null,
				String.class);
		assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, responseEntity.getStatusCode());
	}

	@Test
	@SqlGroup(@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:iniciandoBD.sql"))
	public void obtenerVehiculo() {		
		int cantidadTipoVehiculoEnBD = 2; 
		ResponseEntity<List<ModeloTipoVehiculo>> response = 
				restTemplate.exchange(
						  "/estacionamiento/tipo_vehiculo",
						  HttpMethod.GET,
						  null,
						  new ParameterizedTypeReference<List<ModeloTipoVehiculo>>(){});				
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(cantidadTipoVehiculoEnBD, response.getBody().size());
	}
}
