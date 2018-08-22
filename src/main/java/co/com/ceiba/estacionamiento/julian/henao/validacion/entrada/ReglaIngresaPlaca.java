package co.com.ceiba.estacionamiento.julian.henao.validacion.entrada;


import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.julian.henao.excepcion.ExcepcionParametroInvalido;
import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloVehiculo;


@Component("reglaIngresaPlaca")
public class ReglaIngresaPlaca implements ValidacionEntrada{

	
	@Override
	public void validar(ModeloVehiculo modelVehiculo) {
						
		if(modelVehiculo.getPlaca() == null){
			throw new ExcepcionParametroInvalido("La Placa es obligatoria");
		}		
		
	}


}
