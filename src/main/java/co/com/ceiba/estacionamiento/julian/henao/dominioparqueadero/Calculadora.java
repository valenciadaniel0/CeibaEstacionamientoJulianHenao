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

	@Autowired
	@Qualifier("servicioParqueaderoTarifa")
	private ServicioParqueaderoTarifa servicioParqueaderoTarifa;

	public void calcularCostoParqueadero(ModeloParqueaderoRegistro modeloParqueaderoRegistro, int horasACalcular) {

		ModeloParqueaderoTarifa modeloParqueaderoTarifa = servicioParqueaderoTarifa
				.obtenerTarifasPorTipoVehiculo(modeloParqueaderoRegistro.getVehiculo().getTipoVehiculo().getId());

		long costohora = modeloParqueaderoTarifa.getCostoHora();
		long costodia = modeloParqueaderoTarifa.getCostoDia();
		int inicioCobroDia = modeloParqueaderoTarifa.getHorasCobroDia();
		long costoTotal;

		int cuantosDiasFueron = (horasACalcular / 24);
		int horas = (horasACalcular % 24);

		int horasRestantesMenoresDia = 0;
		int c = 0;

		if (horas >= inicioCobroDia && horas <= 24) {
			costoTotal = costodia * (cuantosDiasFueron + 1);
			modeloParqueaderoRegistro.setDiasParqueadero(cuantosDiasFueron+1);
			
		} else {
			horasRestantesMenoresDia = (horas / inicioCobroDia);
			c = (horas % inicioCobroDia);
			costoTotal = (cuantosDiasFueron + horasRestantesMenoresDia) * costodia + c * costohora;
			modeloParqueaderoRegistro.setDiasParqueadero(cuantosDiasFueron+horasRestantesMenoresDia);
			modeloParqueaderoRegistro.setHorasParqueadero(c);
			
		}
		
		modeloParqueaderoRegistro.setHorasParqueo(horasACalcular);
		modeloParqueaderoRegistro.setCostoTotal(costoTotal);

	}

	public int calcularHorasParqueadero(LocalDateTime fechaEntrada, LocalDateTime fechaSalida) {
		long millis = Duration.between(fechaEntrada, fechaSalida).toMillis();
		return (int) Math.ceil(millis/3600000.0);
	}
}
