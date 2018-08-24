package co.com.ceiba.estacionamiento.julian.henao.testUnitarioEntradaVehiculo;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import co.com.ceiba.estacionamiento.julian.henao.builderstest.TipoVehiculoBuilder;
import co.com.ceiba.estacionamiento.julian.henao.builderstest.VehiculoBuilder;
import co.com.ceiba.estacionamiento.julian.henao.dominioparqueadero.Calendario;
import co.com.ceiba.estacionamiento.julian.henao.excepcion.ExcepcionParametroInvalido;
import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloTipoVehiculo;
import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloVehiculo;
import co.com.ceiba.estacionamiento.julian.henao.validacion.entrada.ReglaDiaHabil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestReglaDiaHabil {

	@MockBean
	Calendario calendario;

	@Autowired
	ReglaDiaHabil reglaDiaHabil;

	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();

	@Test(expected = Test.None.class)
	public void ReglaEntradaVehiculoPlacaAPuedeEntrarLunes() {
		int lunes = 1;
		String placaIniciaEnA = "ABC123";
			
		ModeloTipoVehiculo tipoVehiculoMoto = new TipoVehiculoBuilder().conId(2).conDescripcion("Moto").build();
		ModeloVehiculo vehiculo = new VehiculoBuilder().conPlaca(placaIniciaEnA).conTipoVehiculo(tipoVehiculoMoto)
				.conCilindraje(100).build();

		when(calendario.obtenerDiaActual()).thenReturn(lunes);

		reglaDiaHabil.validar(vehiculo);

	}

	@Test(expected = Test.None.class)
	public void ReglaEntradaVehiculoPlacaAPuedeEntrarDomingo() {
		int domingo = 7;
		String placaIniciaEnA = "AFC123";
		ModeloTipoVehiculo tipoVehiculoMoto = new TipoVehiculoBuilder().conId(2).conDescripcion("Moto").build();
		ModeloVehiculo vehiculo = new VehiculoBuilder().conPlaca(placaIniciaEnA).conTipoVehiculo(tipoVehiculoMoto)
				.conCilindraje(100).build();
		when(calendario.obtenerDiaActual()).thenReturn(domingo);
		reglaDiaHabil.validar(vehiculo);
	}

	@Test(expected = Test.None.class)
	public void ReglaEntradaVehiculoPlacaDiferenteDeASiPuedeEntrarDomingo() {
		int domingo = 7;
		String placaNoIniciaEnA = "BBC123";

		ModeloTipoVehiculo tipoVehiculoMoto = new TipoVehiculoBuilder().conId(2).conDescripcion("Moto").build();
		ModeloVehiculo vehiculo = new VehiculoBuilder().conPlaca(placaNoIniciaEnA).conTipoVehiculo(tipoVehiculoMoto)
				.conCilindraje(100).build();

		when(calendario.obtenerDiaActual()).thenReturn(domingo);
		
		reglaDiaHabil.validar(vehiculo);		
	}
	
	
	@Test(expected = ExcepcionParametroInvalido.class)
	public void ReglaEntradaVehiculoPlacaANOPuedeEntrarDiaDiferente() {
		int martes = 2;
		String placaNoIniciaEnA = "ABC123";

		ModeloTipoVehiculo tipoVehiculoMoto = new TipoVehiculoBuilder().conId(2).conDescripcion("Moto").build();
		ModeloVehiculo vehiculo = new VehiculoBuilder().conPlaca(placaNoIniciaEnA).conTipoVehiculo(tipoVehiculoMoto)
				.conCilindraje(100).build();

		when(calendario.obtenerDiaActual()).thenReturn(martes);
		
		reglaDiaHabil.validar(vehiculo);		
	}
	
	@Test(expected = Test.None.class)
	public void ReglaEntradaVehiculoPlacaYDiaDiferente() {
		int miercoles = 3;
		String placaNoIniciaEnA = "TTC123";

		ModeloTipoVehiculo tipoVehiculoMoto = new TipoVehiculoBuilder().conId(2).conDescripcion("Moto").build();
		ModeloVehiculo vehiculo = new VehiculoBuilder().conPlaca(placaNoIniciaEnA).conTipoVehiculo(tipoVehiculoMoto)
				.conCilindraje(100).build();

		when(calendario.obtenerDiaActual()).thenReturn(miercoles);
		
		reglaDiaHabil.validar(vehiculo);		
	}
	
	@Test
	public void ReglaEntradaVehiculoPlacaDiferenteDeASiPuedeEntrarLunes() {
		int lunes = 1;
		String placaNoIniciaEnA = "WBC123";

		ModeloTipoVehiculo tipoVehiculoMoto = new TipoVehiculoBuilder().conId(2).conDescripcion("Moto").build();
		ModeloVehiculo vehiculo = new VehiculoBuilder().conPlaca(placaNoIniciaEnA).conTipoVehiculo(tipoVehiculoMoto)
				.conCilindraje(100).build();

		when(calendario.obtenerDiaActual()).thenReturn(lunes);

		try {
			reglaDiaHabil.validar(vehiculo);
		} catch (ExcepcionParametroInvalido e) {
			fail();
		}
	}

}
