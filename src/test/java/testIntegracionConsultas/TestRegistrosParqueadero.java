package testIntegracionConsultas;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import co.com.ceiba.estacionamiento.julian.henao.controlador.ControladorParqueaderoRegistro;
import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloParqueaderoRegistro;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestRegistrosParqueadero {

	@Autowired
	ControladorParqueaderoRegistro controladorParqueaderoRegistro;
	
	@Test
	public void test() {
		
		ResponseEntity<List<ModeloParqueaderoRegistro>> responseEntity;
		
	}

}
