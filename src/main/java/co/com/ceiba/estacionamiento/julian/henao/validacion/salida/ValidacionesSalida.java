package co.com.ceiba.estacionamiento.julian.henao.validacion.salida;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("validacionesSalida")
public class ValidacionesSalida {
	
	@Autowired
	@Qualifier("reglaSobreCostoCilindraje")
	private ReglaSobreCostoCilindraje reglaSobreCostoCilindraje;	
		
	@Autowired
	@Qualifier("reglaPlacaYRegistroCoinciden")
	private ReglaPlacaYRegistroCoinciden registroCoincide;
		
	public List<ValidacionSalida> validacionesSalida() {
		List<ValidacionSalida> validaciones = new ArrayList<>();				
		validaciones.add(registroCoincide);
		validaciones.add(reglaSobreCostoCilindraje);			
		return validaciones;
	}	
}
