package co.com.ceiba.estacionamiento.julian.henao.convertidor;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.julian.henao.entidad.EntidadVehiculo;
import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloVehiculo;

import java.util.List;

@Component("convertidorVehiculo")
public class ConvertidorVehiculo {
		
	public List<ModeloVehiculo> convertirLista(List<EntidadVehiculo> entidadVehiculos) {
		List<ModeloVehiculo> modeloVehiculos = new ArrayList<>();
		for (EntidadVehiculo entidadVehiculo : entidadVehiculos) {
			modeloVehiculos.add(convertirEntidadAModelo(entidadVehiculo));
		}
		return modeloVehiculos;
	}

	public ModeloVehiculo convertirEntidadAModelo(EntidadVehiculo entidadVehiculo) {
		ModeloVehiculo modeloVehiculo = new ModeloVehiculo();
		ConvertidorTipoVehiculo convertidorTipoVehiculo = new ConvertidorTipoVehiculo();
		modeloVehiculo.setCilindraje(entidadVehiculo.getCilindraje());
		modeloVehiculo.setId(entidadVehiculo.getId());
		modeloVehiculo.setPlaca(entidadVehiculo.getPlaca());
		modeloVehiculo.setTipoVehiculo(convertidorTipoVehiculo.convertirEntidadAModelo(entidadVehiculo.getTipoVehiculo()));
		return modeloVehiculo;
	}

	public EntidadVehiculo convertirModeloAEntidad(ModeloVehiculo modeloVehiculo) {
		EntidadVehiculo entidadVehiculo = new EntidadVehiculo();
		ConvertidorTipoVehiculo convertidorTipoVehiculo = new ConvertidorTipoVehiculo();
		entidadVehiculo.setCilindraje(modeloVehiculo.getCilindraje());
		entidadVehiculo.setId(modeloVehiculo.getId());
		entidadVehiculo.setPlaca(modeloVehiculo.getPlaca());
		entidadVehiculo.setTipoVehiculo(convertidorTipoVehiculo.convertirModeloAEntidad(modeloVehiculo.getTipoVehiculo()));
		return entidadVehiculo;
	}

}
