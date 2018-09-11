package co.com.ceiba.estacionamiento.julian.henao.validacion.entrada;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.julian.henao.dominioparqueadero.Calendario;
import co.com.ceiba.estacionamiento.julian.henao.excepcion.ExcepcionParametroInvalido;
import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloVehiculo;

@Component
public class ReglaDiaHabil implements ValidacionEntrada{

	
	private static final int LUNES = 1;
	private static final int DOMINGO = 7;
	
	@Autowired
	@Qualifier("calendario")
	Calendario calendario;
	
	@Override
	public void validar(ModeloVehiculo modelVehiculo) {
		
		int diaActual = calendario.obtenerDiaActual();

		if(modelVehiculo.getPlaca().toUpperCase().charAt(0) == 'A' && !(diaHabilDomingoPlacaA(diaActual) || diaHabilLunesPlacaA(diaActual))){
			throw new ExcepcionParametroInvalido("La placa que inicia por 'A' SOLO puede ingresar los Lunes y los Domingos");
		}
		
	}
			
	
	/**
	 * En el formato LocalDateTime manejado en el calendario Lunes = 1, Martes = 2...
	 * @param diaActual
	 * @return
	 */
	private boolean diaHabilLunesPlacaA(int diaActual){
		return diaActual == LUNES;		
	}
	
	/**
	 * En el formato LocalDateTime manejado en el calendario Sabado = 6, Domingo = 7, Lunes = 1, Martes = 2...
	 * @param diaActual
	 * @return
	 */
	private boolean diaHabilDomingoPlacaA(int diaActual){
		return diaActual == DOMINGO;
	}

}
