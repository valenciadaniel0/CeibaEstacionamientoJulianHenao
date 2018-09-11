package co.com.ceiba.estacionamiento.julian.henao.validacion.entrada;


import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.julian.henao.excepcion.ExcepcionParametroInvalido;
import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloVehiculo;

@Component("reglaCilindrajeMoto")
public class ReglaCilindrajeMoto implements ValidacionEntrada{

	private static final int IDMOTO = 2;
	private static final int CILINDRAJEINCORRETO = 0;
	@Override
	public void validar(ModeloVehiculo modelVehiculo) {
		if(modelVehiculo.getTipoVehiculo().getId() == IDMOTO && modelVehiculo.getCilindraje() <= CILINDRAJEINCORRETO){
			throw new ExcepcionParametroInvalido("Valor de cilindraje invalido para la Moto");
		}		
	}

}
