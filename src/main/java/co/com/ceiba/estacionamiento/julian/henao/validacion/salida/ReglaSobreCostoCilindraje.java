package co.com.ceiba.estacionamiento.julian.henao.validacion.salida;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.julian.henao.excepcion.ExcepcionSobreCosto;
import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloVehiculo;
import co.com.ceiba.estacionamiento.julian.henao.servicio.ServicioVehiculo;

@Component("reglaSobreCostoCilindraje")
public class ReglaSobreCostoCilindraje implements ValidacionSalida {

	@Autowired
	@Qualifier("reglaPlacaExiste")
	private ReglaPlacaExiste reglaPlacaExiste;

	@Autowired
	@Qualifier("servicioVehiculo")
	private ServicioVehiculo servicioVehiculo;

	private static final int MOTO = 2;
	private static final int CILINDRAJE = 500;
	
	@Override
	public void validar(int idRegistro, String placa) {
		reglaPlacaExiste.validar(idRegistro, placa);
		ModeloVehiculo modelVehiculo = servicioVehiculo.obtenerPorPlaca(placa);

		if (modelVehiculo.getTipoVehiculo().getId() == MOTO && modelVehiculo.getCilindraje() > CILINDRAJE) {
			throw new ExcepcionSobreCosto("Valor de cilindraje supera los " + CILINDRAJE
					+ " CC, esto genera un sobre costo de 2000 sobre el valor total");
		}
	}
}
