package co.com.ceiba.estacionamiento.julian.henao.testUnit.SalidaVehiculo;

import static org.mockito.Mockito.when;
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
import co.com.ceiba.estacionamiento.julian.henao.servicio.ServicioVehiculo;
import co.com.ceiba.estacionamiento.julian.henao.validacion.salida.ReglaPlacaExiste;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestReglaPlacaExiste {

	@MockBean
	ServicioVehiculo servicioVehiculo;

	@Autowired
	ReglaPlacaExiste reglaPlacaExiste;
	
	@Test(expected = ExcepcionParametroInvalido.class)
	public void ReglaPlacaIngresadaNOExiste() {
		int cualquierIdRegistro = 1;
		String placaNOExiste = "ABC123";
		when(servicioVehiculo.obtenerPorPlaca(placaNOExiste)).thenReturn(null);
		reglaPlacaExiste.validar(cualquierIdRegistro, placaNOExiste);
	}

	//@Test(expected = Test.None.class)
	@Test(expected = ExcepcionParametroInvalido.class)
	public void ReglaConCilindrajeMENOR500Moto() {				
		
		int cualquierIdRegistro = 1;
		String placaExiste = "ABC123";
		ModeloTipoVehiculo tipoVehiculoMoto = new TipoVehiculoBuilder().conId(2).conDescripcion("Moto").build();		
		ModeloVehiculo vehiculo = new VehiculoBuilder().conPlaca(placaExiste).conTipoVehiculo(tipoVehiculoMoto).conCilindraje(100).build();				

		when(servicioVehiculo.obtenerPorPlaca(placaExiste)).thenReturn(vehiculo);
		reglaPlacaExiste.validar(cualquierIdRegistro, placaExiste);			
	}

}
