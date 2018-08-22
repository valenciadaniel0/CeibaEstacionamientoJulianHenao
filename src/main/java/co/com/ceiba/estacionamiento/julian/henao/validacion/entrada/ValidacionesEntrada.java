package co.com.ceiba.estacionamiento.julian.henao.validacion.entrada;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;



@Component("validacionesEntrada")
public class ValidacionesEntrada {

	
	@Autowired
	@Qualifier("reglaCilindrajeMoto")
	private ReglaCilindrajeMoto reglaCilindrajeMoto;
	
	@Autowired	
	@Qualifier("reglaDiaHabil")
	private ReglaDiaHabil reglaDiaHabil;
	
	@Autowired
	@Qualifier("reglaDisponibilidadEspacio")
	private ReglaDisponibilidadEspacio reglaDisponibilidadEspacio;
		
	@Autowired
	@Qualifier("reglaTipoVehiculo")
	private ReglaTipoVehiculo reglaTipoVehiculo;
	
	@Autowired
	@Qualifier("reglaVehiculoSinSalirVuelveAEntrar")
	private ReglaVehiculoSinSalirVuelveAEntrar reglaVehiculoSinSalirVuelveAEntrar;
	
	
	@Autowired
	@Qualifier("reglaIngresaPlaca")
	private ReglaIngresaPlaca reglaIngresaPlaca;
	
	public List<ValidacionEntrada> validacionesEntrada() {
		List<ValidacionEntrada> validaciones = new ArrayList<>();		
		validaciones.add(reglaIngresaPlaca);
		validaciones.add(reglaTipoVehiculo);
		validaciones.add(reglaDiaHabil);
		validaciones.add(reglaVehiculoSinSalirVuelveAEntrar);
		validaciones.add(reglaDisponibilidadEspacio);
		validaciones.add(reglaCilindrajeMoto);		
		return validaciones;
	}	
	
}
