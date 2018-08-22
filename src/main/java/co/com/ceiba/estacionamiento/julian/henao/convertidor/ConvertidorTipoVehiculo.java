package co.com.ceiba.estacionamiento.julian.henao.convertidor;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.julian.henao.entidad.EntidadTipoVehiculo;
import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloTipoVehiculo;

import java.util.List;

@Component("convertidorTipoVehiculo")
public class ConvertidorTipoVehiculo {

	
	public List<ModeloTipoVehiculo> convertirLista(List<EntidadTipoVehiculo> entidadTipoVehiculos){
		List<ModeloTipoVehiculo> tipoVehiculos = new ArrayList<>();
		for(EntidadTipoVehiculo etv : entidadTipoVehiculos ){
			tipoVehiculos.add(convertirEntidadAModelo(etv));
		}
		return tipoVehiculos;			
	}	
	
	public ModeloTipoVehiculo convertirEntidadAModelo(EntidadTipoVehiculo entidadTipoVehiculo) {
		ModeloTipoVehiculo tv = new ModeloTipoVehiculo();
		tv.setId(entidadTipoVehiculo.getId());
		tv.setDescripcion(entidadTipoVehiculo.getDescripcion());
		return tv;
	}

	public EntidadTipoVehiculo convertirModeloAEntidad(ModeloTipoVehiculo vehiculoModel) {
		EntidadTipoVehiculo ev = new EntidadTipoVehiculo();
		ev.setId(vehiculoModel.getId());
		ev.setDescripcion(vehiculoModel.getDescripcion());
		return ev;
	}
}
