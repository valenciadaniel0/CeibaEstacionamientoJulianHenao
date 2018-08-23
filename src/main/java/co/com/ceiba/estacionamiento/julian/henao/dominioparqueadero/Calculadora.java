package co.com.ceiba.estacionamiento.julian.henao.dominioparqueadero;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloParqueaderoRegistro;
import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloParqueaderoTarifa;
import co.com.ceiba.estacionamiento.julian.henao.servicio.ServicioParqueaderoTarifa;

/**
 * Esta clase tiene como funciones: 1. Calcular dias y horas entre las fechas de
 * ingreso y salida del vehiculo 2. Calcular costoTotal del parqueo
 * 
 * @author julian.henao
 *
 */
@Component("calculadora")
public class Calculadora {

	private static final int HORASDELDIA = 24;
	private static final double MILISEGUNDOSAHORAS = 3600000.0;
	@Autowired
	@Qualifier("servicioParqueaderoTarifa")
	private ServicioParqueaderoTarifa servicioParqueaderoTarifa;

	public void calcularCostoParqueadero(ModeloParqueaderoRegistro modeloParqueaderoRegistro, int horasACalcular) {

		// Obtengo las tarifas según el tipo de vehiculo
		ModeloParqueaderoTarifa modeloParqueaderoTarifa = servicioParqueaderoTarifa
				.obtenerTarifasPorTipoVehiculo(modeloParqueaderoRegistro.getVehiculo().getTipoVehiculo().getId());

		long costohora = modeloParqueaderoTarifa.getCostoHora();
		long costodia = modeloParqueaderoTarifa.getCostoDia();
		int inicioCobroDia = modeloParqueaderoTarifa.getHorasCobroDia();
		long costoTotal;

		int diasEnParqueadero = (horasACalcular / HORASDELDIA);
		int horasRestantesParqueo = (horasACalcular % HORASDELDIA);

		if (inicioCobroDia <= horasRestantesParqueo && horasRestantesParqueo <= HORASDELDIA) {

			costoTotal = costodia * (diasEnParqueadero + 1);
			modeloParqueaderoRegistro.setDiasParqueadero(diasEnParqueadero + 1);
			// Las horas de Parqueadero no se asigan, dado que en la bd
			// inicializa en cero

		} else {
			costoTotal = diasEnParqueadero * costodia + horasRestantesParqueo * costohora;
			modeloParqueaderoRegistro.setDiasParqueadero(diasEnParqueadero);
			modeloParqueaderoRegistro.setHorasParqueadero(horasRestantesParqueo);
		}

		modeloParqueaderoRegistro.setHorasParqueo(horasACalcular);
		modeloParqueaderoRegistro.setCostoTotal(costoTotal);

	}

	public int calcularHorasParqueadero(LocalDateTime fechaEntrada, LocalDateTime fechaSalida) {
		long millis = Duration.between(fechaEntrada, fechaSalida).toMillis();
		return (int) Math.ceil(millis / MILISEGUNDOSAHORAS);
	}
}
