package co.com.ceiba.estacionamiento.julian.henao.validacion.entrada;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.julian.henao.excepcion.ExcepcionConflicto;
import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloVehiculo;
import co.com.ceiba.estacionamiento.julian.henao.servicio.ServicioParqueaderoRegistro;
import co.com.ceiba.estacionamiento.julian.henao.servicio.ServicioVehiculo;

@Component("reglaVehiculoSinSalirVuelveAEntrar")
public class ReglaVehiculoSinSalirVuelveAEntrar implements ValidacionEntrada {

	@Autowired
	private ServicioVehiculo servicioVehiculo;

	@Autowired
	@Qualifier("servicioParqueaderoRegistro")
	private ServicioParqueaderoRegistro servicioParqueaderoRegistro;

	@Override
	public void validar(ModeloVehiculo modeloVehiculo) {
		ModeloVehiculo modeloVehiculoAux = servicioVehiculo.obtenerPorPlaca(modeloVehiculo.getPlaca());
		if (modeloVehiculoAux != null && servicioParqueaderoRegistro
				.obtenerRegistrosPorVehiculosPorIdSinSalir(modeloVehiculoAux.getId()) != null) {
			throw new ExcepcionConflicto("El vehiculo con la PLACA ingresada se encuentra en el parqueadero y NO ha salido");
		}
	}

}
