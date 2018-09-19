package co.com.ceiba.estacionamiento.julian.henao.testIntegration.ConsultaServicios;

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

import co.com.ceiba.estacionamiento.julian.henao.controlador.ControladorParqueaderoRegistro;
import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloParqueaderoRegistro;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestParqueaderoRegistro {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	ControladorParqueaderoRegistro controladorParqueaderoRegistro;
	
	private String url ;
	
	@Before
	public void datosServicio(){
		url = "/estacionamiento/registros";
	}
	
	
	@Test
	@SqlGroup(@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:iniciandoBD.sql"))
	public void RealizaConsultaRegistros() {		
		ResponseEntity<List<ModeloParqueaderoRegistro>> responseEntity = controladorParqueaderoRegistro.obtenerRegistrosAnteriores();		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());		
	}


	@Test
	public void crearRegistroNulo() {
		ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, null,
				String.class);
		assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, responseEntity.getStatusCode());
	}

	@Test
	@SqlGroup(@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:iniciandoBD.sql"))
	public void obtenerRegistro() {		
		int id = 1;
		String placaVehiculo = "ABC123";
		ResponseEntity<ModeloParqueaderoRegistro> response = 
				restTemplate.exchange(
						  url+"/reciente/"+id,
						  HttpMethod.GET,
						  null,
						  new ParameterizedTypeReference<ModeloParqueaderoRegistro>(){});				
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(placaVehiculo, response.getBody().getVehiculo().getPlaca());
	}
	
}
