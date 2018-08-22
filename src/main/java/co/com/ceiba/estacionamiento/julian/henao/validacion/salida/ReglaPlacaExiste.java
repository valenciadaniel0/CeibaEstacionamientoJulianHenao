package co.com.ceiba.estacionamiento.julian.henao.validacion.salida;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.julian.henao.excepcion.ExcepcionParametroInvalido;
import co.com.ceiba.estacionamiento.julian.henao.servicio.ServicioVehiculo;

@Component("reglaPlacaExiste")
public class ReglaPlacaExiste implements ValidacionSalida{
	
	@Autowired
	@Qualifier("servicioVehiculo")
	private ServicioVehiculo servicioVehiculo;
	
	@Override
	public void validar(int idRegistro, String placa) {
				
		if(servicioVehiculo.obtenerPorPlaca(placa) == null){
			throw new ExcepcionParametroInvalido("La Placa ingresada para salir no se encuentra registrada");
		}
	}

}
