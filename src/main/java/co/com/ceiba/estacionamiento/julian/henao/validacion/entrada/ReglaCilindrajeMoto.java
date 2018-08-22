package co.com.ceiba.estacionamiento.julian.henao.validacion.entrada;


import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.julian.henao.excepcion.ExcepcionParametroInvalido;
import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloVehiculo;

@Component("reglaCilindrajeMoto")
public class ReglaCilindrajeMoto implements ValidacionEntrada{

	@Override
	public void validar(ModeloVehiculo modelVehiculo) {
		if(modelVehiculo.getTipoVehiculo().getId() == 2 && modelVehiculo.getCilindraje() <= 0){
			throw new ExcepcionParametroInvalido("Valor de cilindraje invalido para la Moto");
		}		
	}

}
