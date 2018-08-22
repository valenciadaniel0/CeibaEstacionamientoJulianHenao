package co.com.ceiba.estacionamiento.julian.henao.validacion.entrada;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.julian.henao.excepcion.ExcepcionParametroInvalido;
import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloTipoVehiculo;
import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloVehiculo;
import co.com.ceiba.estacionamiento.julian.henao.servicio.ServicioTipoVehiculo;

@Component("reglaTipoVehiculo")
public class ReglaTipoVehiculo implements ValidacionEntrada{

	@Autowired
	@Qualifier("servicioTipoVehiculo")
	private ServicioTipoVehiculo servicioTipoVehiculo;
	
	/**
	 *  El metodo recorre la lista de tipo de vehiculos registrados en la BD
	 *  si no encuentra algun vehiculoValido que consisa con el ingresado, lanzará la excepción
	 */
	
	@Override
	public void validar(ModeloVehiculo modelVehiculo) {
		
		List<ModeloTipoVehiculo> modeloTipoVehiculos = servicioTipoVehiculo.obtenerTipoVehiculos();
		boolean vehiculoValido = false;
		StringBuilder tiposVehiculos = new StringBuilder(); 
		
		for(ModeloTipoVehiculo modeloTipoVehiculo: modeloTipoVehiculos){
			tiposVehiculos.append(" {("+ modeloTipoVehiculo.getId() +")," + modeloTipoVehiculo.getDescripcion() + "}");
			if( modeloTipoVehiculo.getId() == modelVehiculo.getTipoVehiculo().getId()){
				vehiculoValido = true;
				break;
			}
		}
						
		/* Si no encuentra algún tipo de vehiculo que coincida con el ingresado vehiculoValido = false */
		if(!vehiculoValido){
			throw new ExcepcionParametroInvalido("El tipo de vehiculo ingresado no es Valido - Los tipos aceptados son: "+ tiposVehiculos.toString() );
		}		
		
	}


}
