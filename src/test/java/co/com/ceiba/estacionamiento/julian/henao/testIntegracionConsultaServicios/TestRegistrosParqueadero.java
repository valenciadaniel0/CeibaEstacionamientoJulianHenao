package co.com.ceiba.estacionamiento.julian.henao.testIntegracionConsultaServicios;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
public class TestRegistrosParqueadero {

	@Autowired
	ControladorParqueaderoRegistro controladorParqueaderoRegistro;
	
	@Test
	@SqlGroup(@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:iniciandoBD.sql"))
	public void RealizaConsultaRegistros() {		
		ResponseEntity<List<ModeloParqueaderoRegistro>> responseEntity = controladorParqueaderoRegistro.obtenerRegistrosAnteriores();		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());		
	}

}
