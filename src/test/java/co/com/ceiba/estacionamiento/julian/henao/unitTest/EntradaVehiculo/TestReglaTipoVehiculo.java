package co.com.ceiba.estacionamiento.julian.henao.unitTest.EntradaVehiculo;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import co.com.ceiba.estacionamiento.julian.henao.builderstest.TipoVehiculoBuilder;
import co.com.ceiba.estacionamiento.julian.henao.builderstest.VehiculoBuilder;
import co.com.ceiba.estacionamiento.julian.henao.excepcion.ExcepcionParametroInvalido;
import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloTipoVehiculo;
import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloVehiculo;
import co.com.ceiba.estacionamiento.julian.henao.servicio.ServicioTipoVehiculo;
import co.com.ceiba.estacionamiento.julian.henao.validacion.entrada.ReglaTipoVehiculo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestReglaTipoVehiculo {

	@MockBean
	ServicioTipoVehiculo servicioTipoVehiculo;

	@Autowired
	ReglaTipoVehiculo reglaTipoVehiculo;

	List<ModeloTipoVehiculo> tiposVehiculo;

	@Before
	public void listaTiposVehiculo() {
		tiposVehiculo = new ArrayList<>();
		tiposVehiculo.add(new TipoVehiculoBuilder().conId(1).conDescripcion("Automovil").build());
		tiposVehiculo.add(new TipoVehiculoBuilder().conId(2).conDescripcion("Moto").build());
	}

	@Test
	public void ReglaEntradaTipoVehiculoMotoPuedeEntrar() {

		ModeloTipoVehiculo tipoVehiculoMoto = new TipoVehiculoBuilder().conId(2).conDescripcion("Moto").build();

		ModeloVehiculo vehiculo = new VehiculoBuilder().conPlaca("ABC123").conTipoVehiculo(tipoVehiculoMoto)
				.conCilindraje(100).build();

		when(servicioTipoVehiculo.obtenerTipoVehiculos()).thenReturn(tiposVehiculo);

		try {
			reglaTipoVehiculo.validar(vehiculo);
		} catch (ExcepcionParametroInvalido e) {
			fail();
		}
	}

	@Test(expected = Test.None.class)
	public void ReglaEntradaTipoVehiculoAutoPuedeEntrar() {

		ModeloTipoVehiculo tipoVehiculoAuto = new TipoVehiculoBuilder().conId(1).conDescripcion("Automovil").build();
		ModeloVehiculo vehiculo = new VehiculoBuilder().conPlaca("ABC123").conTipoVehiculo(tipoVehiculoAuto)
				.conCilindraje(100).build();

		when(servicioTipoVehiculo.obtenerTipoVehiculos()).thenReturn(tiposVehiculo);

		reglaTipoVehiculo.validar(vehiculo);
	}

	@Test(expected = ExcepcionParametroInvalido.class)
	public void ReglaEntradaTipoVehiculoDiferenteNOPuedeEntrar() {

		ModeloTipoVehiculo tipoVehiculoCualquiera = new TipoVehiculoBuilder().conId(3).conDescripcion("Bicicleta")
				.build();
		ModeloVehiculo vehiculo = new VehiculoBuilder().conPlaca("ABC123").conTipoVehiculo(tipoVehiculoCualquiera)
				.conCilindraje(100).build();

		when(servicioTipoVehiculo.obtenerTipoVehiculos()).thenReturn(tiposVehiculo);

		reglaTipoVehiculo.validar(vehiculo);

	}

}
