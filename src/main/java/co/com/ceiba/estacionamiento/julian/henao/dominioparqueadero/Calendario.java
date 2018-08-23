package co.com.ceiba.estacionamiento.julian.henao.dominioparqueadero;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.stereotype.Component;

/**
 * Esta clase tiene como funciones: 
 * 1. Obtener fecha actual 
 * 2. Obtener dia de la semana
 * 
 * @author julian.henao
 *
 */
@Component("calendario")
public class Calendario {

	/**
	 * Lista de nombres de zonas horarias
	 * https://www.mkyong.com/java8/java-display-all-zoneid-and-its-utc-offset/
	 * "Pacific/Norfolk" "America/St_Johns" "Etc/GMT+12" "America/Bogota"
	 */
	public static final String ZONAHORARIA = "America/Bogota";

	/**
	 * La indicación de los números inicia en Lunes=1, Martes=2...Domingo =7;
	 * 
	 * @return el número de la semana
	 */
	public int obtenerDiaActual() {
		return obtenerFechaActual().getDayOfWeek().getValue();
	}

	public LocalDateTime obtenerFechaActual() {
		return LocalDateTime.now(ZoneId.of(ZONAHORARIA));
	}

}
