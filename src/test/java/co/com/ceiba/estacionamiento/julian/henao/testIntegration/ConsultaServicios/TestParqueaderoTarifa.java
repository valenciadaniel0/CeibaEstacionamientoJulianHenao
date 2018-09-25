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

import co.com.ceiba.estacionamiento.julian.henao.builderstest.ParqueaderoTarifaBuilder;
import co.com.ceiba.estacionamiento.julian.henao.builderstest.TipoVehiculoBuilder;
import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloParqueaderoTarifa;
import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloTipoVehiculo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestParqueaderoTarifa {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void crearTarifaExitoso() {
		
		ModeloTipoVehiculo tipoVehiculoAuto = new TipoVehiculoBuilder().conId(1).conDescripcion("Automovil").build();
		ModeloParqueaderoTarifa tarifa = new ParqueaderoTarifaBuilder().conId(1).conTipoVehiculo(tipoVehiculoAuto).conCostoDia(8000).conCostoHora(1000).conHorasCobroDia(9).build();

		ResponseEntity<String> responseEntity = restTemplate.postForEntity("/estacionamiento/tarifa",
				tarifa, String.class);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals("Tarifa Ingresada exitosamente", responseEntity.getBody());
	}

	@Test
	public void crearTarifaNulo() {
		ResponseEntity<String> responseEntity = restTemplate.postForEntity("/estacionamiento/tarifa", null,
				String.class);
		assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, responseEntity.getStatusCode());
	}

	@Test
	@SqlGroup(@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:iniciandoBD.sql"))
	public void obtenerTarifas() {		
		int cantidadTarifasEnBD = 3;
		ResponseEntity<List<ModeloParqueaderoTarifa>> response = 
				restTemplate.exchange(
						  "/estacionamiento/tipo_vehiculo",
						  HttpMethod.GET,
						  null,
						  new ParameterizedTypeReference<List<ModeloParqueaderoTarifa>>(){});				
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(cantidadTarifasEnBD, response.getBody().size());
	}
}
