package co.com.ceiba.estacionamiento.julian.henao.testUnit.EntradaVehiculo;

import static org.mockito.Mockito.when;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import co.com.ceiba.estacionamiento.julian.henao.builderstest.ParqueaderoEspacioDisponibleBuilder;
import co.com.ceiba.estacionamiento.julian.henao.builderstest.TipoVehiculoBuilder;
import co.com.ceiba.estacionamiento.julian.henao.builderstest.VehiculoBuilder;
import co.com.ceiba.estacionamiento.julian.henao.excepcion.ExcepcionConflicto;
import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloParqueaderoEspacioDisponible;
import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloTipoVehiculo;
import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloVehiculo;
import co.com.ceiba.estacionamiento.julian.henao.servicio.ServicioParqueaderoEspacioDisponible;
import co.com.ceiba.estacionamiento.julian.henao.validacion.entrada.ReglaDisponibilidadEspacio;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestReglaDisponibilidadEspacio {

	
	@MockBean
	ServicioParqueaderoEspacioDisponible servicioParqueaderoEspacioDisponible;
	
	@Autowired
	ReglaDisponibilidadEspacio ReglaDisponibilidadEspacio; 
	
	
	@Test(expected = Test.None.class)
	public void ReglaSIHayDisponibilidadEspacioParaVehiculo(){
		ModeloTipoVehiculo tipoVehiculoMoto = new TipoVehiculoBuilder().conId(2).conDescripcion("Moto").build();
		ModeloVehiculo vehiculo = new VehiculoBuilder().conPlaca("ABC123").conTipoVehiculo(tipoVehiculoMoto).conCilindraje(100).build();	 
		int espacioActual = 9;
		int limiteEspacio = 10;		
		ModeloParqueaderoEspacioDisponible modeloParqueaderoEspacioDisponible = new ParqueaderoEspacioDisponibleBuilder().conId(1).conTipoVehiculo(tipoVehiculoMoto).conEspacioActual(espacioActual).conLimiteEspacio(limiteEspacio).build();
	
		when(servicioParqueaderoEspacioDisponible.obtenerEspacioDisponiblePorTipoVehiculo(tipoVehiculoMoto.getId())).thenReturn(modeloParqueaderoEspacioDisponible);
		ReglaDisponibilidadEspacio.validar(vehiculo);
	}
	
	
	@Test(expected = ExcepcionConflicto.class)
	public void ReglaNOHayDisponibilidadEspacioParaVehiculo(){
		ModeloTipoVehiculo tipoVehiculoAuto = new TipoVehiculoBuilder().conId(1).conDescripcion("Automovil").build();
		
		ModeloVehiculo vehiculo = new VehiculoBuilder().conPlaca("ABC123").conTipoVehiculo(tipoVehiculoAuto).conCilindraje(100).build();	 
		int espacioActual = 20;
		int limiteEspacio = 20;
		ModeloParqueaderoEspacioDisponible modeloParqueaderoEspacioDisponible = new ParqueaderoEspacioDisponibleBuilder().conId(1).conTipoVehiculo(tipoVehiculoAuto).conEspacioActual(espacioActual).conLimiteEspacio(limiteEspacio).build();	
		
		when(servicioParqueaderoEspacioDisponible.obtenerEspacioDisponiblePorTipoVehiculo(tipoVehiculoAuto.getId())).thenReturn(modeloParqueaderoEspacioDisponible);
		ReglaDisponibilidadEspacio.validar(vehiculo);
	}
	
	
	
	
	
}
