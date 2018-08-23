package co.com.ceiba.estacionamiento.julian.henao.validacion.entrada;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.julian.henao.excepcion.ExcepcionConflicto;
import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloParqueaderoEspacioDisponible;
import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloVehiculo;
import co.com.ceiba.estacionamiento.julian.henao.servicio.ServicioParqueaderoEspacioDisponible;

@Component("reglaDisponibilidadEspacio")
public class ReglaDisponibilidadEspacio implements ValidacionEntrada {

	@Autowired
	@Qualifier("servicioParqueaderoEspacioDisponible")
	private ServicioParqueaderoEspacioDisponible servicioParqueaderoEspacioDisponible;

	@Override
	public void validar(ModeloVehiculo modeloVehiculo) {

		ModeloParqueaderoEspacioDisponible modeloParqueaderoEspacioDisponible = servicioParqueaderoEspacioDisponible
				.obtenerEspacioDisponiblePorTipoVehiculo(modeloVehiculo.getTipoVehiculo().getId());

		if (modeloParqueaderoEspacioDisponible.getEspacioActual() == modeloParqueaderoEspacioDisponible
				.getLimiteEspacio()) {
			throw new ExcepcionConflicto(
					"No hay espacio disponible en el parqueadero para este tipo de Vehiculo \n Espacio Actual: "
							+ modeloParqueaderoEspacioDisponible.getEspacioActual() + "\n Espacio Limite: "
							+ modeloParqueaderoEspacioDisponible.getLimiteEspacio());
		}
	}

}
