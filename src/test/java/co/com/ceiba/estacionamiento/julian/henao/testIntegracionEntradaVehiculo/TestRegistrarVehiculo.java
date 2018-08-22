package co.com.ceiba.estacionamiento.julian.henao.testIntegracionEntradaVehiculo;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

import co.com.ceiba.estacionamiento.julian.henao.builderstest.TipoVehiculoBuilder;
import co.com.ceiba.estacionamiento.julian.henao.builderstest.VehiculoBuilder;
import co.com.ceiba.estacionamiento.julian.henao.excepcion.ExcepcionConflicto;
import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloTipoVehiculo;
import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloVehiculo;
import co.com.ceiba.estacionamiento.julian.henao.servicio.ServicioVehiculo;

/**
 * Se realiza la prueba de ingreso para los siguientes casos: 1. Un vehiculo en la BD con
 * registro Inactivo (Ya sali贸 del parqueadero):'ZXC234' 2. Un vehiculo en la BD
 * con registro Activo (Sin salir del parqueadero):'JKL925' 3. Un vehiculo que no se encuentra registra en la BD 'FUT405'
 * 
 * Cuando se ingresa la placa de un vehiculo matriculado en la base de datos se
 * ignoran los nuevos parametros y se mantienen los anteriores Esto debido a que
 * ese planteamiento no fue se馻lado en las especificaciones del problema
 * 
 * @author julian.henao
 *
 */

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestRegistrarVehiculo {

	@Autowired
	ServicioVehiculo servicioVehiculo;
	
	String placaExistenteSinSalir;
	String placaExistenteYaSalio;
	String placaInexistente;
	
	@Before
	public void datosPrueba() {
		placaExistenteYaSalio = "ZXC234";
		placaExistenteSinSalir = "JKL925";
		placaInexistente = "FUT405";
	}

	/**
	 * Debe lanzarse una excepci贸n de Conflicto, dado que no se ha sacado el
	 * vehiculo y se esta intentando ingresarlo
	 */
	@Test(expected = ExcepcionConflicto.class)
	@SqlGroup(@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:iniciandoBD.sql"))
	public void InsertarPlacaExistenteSinSalir() {
		ModeloTipoVehiculo tipoVehiculoMoto = new TipoVehiculoBuilder().conId(2).conDescripcion("Moto").build();
		ModeloVehiculo vehiculo = new VehiculoBuilder().conPlaca(placaExistenteSinSalir)
				.conTipoVehiculo(tipoVehiculoMoto).conCilindraje(100).build();
		servicioVehiculo.crear(vehiculo);
	}

	
	@Test(expected = Test.None.class)
	@SqlGroup(@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:iniciandoBD.sql"))
	public void InsertarPlacaInexistente() {
		ModeloTipoVehiculo tipoVehiculoMoto = new TipoVehiculoBuilder().conId(2).conDescripcion("Moto").build();
		ModeloVehiculo vehiculo = new VehiculoBuilder().conPlaca(placaInexistente)
				.conTipoVehiculo(tipoVehiculoMoto).conCilindraje(100).build();
		servicioVehiculo.crear(vehiculo);
	}

		
	/**
	 * Un vehiculo que existe en la BD, pero no tiene registros activos en el
	 * parqueadero puede volver a ingresar, la desventaja es que en este
	 * proyecto no se esta considerando la actualizaci贸n de los datos ingresados
	 * nuevos. Como se mencion贸 anteriormente, no hacia parte de las historia de
	 * usuario
	 */
	@Test(expected = Test.None.class)
	@SqlGroup(@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:iniciandoBD.sql"))
	public void InsertarPlacaExistenteYaSalio() {
		ModeloTipoVehiculo tipoVehiculoAuto = new TipoVehiculoBuilder().conId(1).conDescripcion("Automovil").build();
		ModeloVehiculo vehiculo = new VehiculoBuilder().conPlaca(placaExistenteYaSalio)
				.conTipoVehiculo(tipoVehiculoAuto).conCilindraje(100).build();
		servicioVehiculo.crear(vehiculo);
	}
	

}

