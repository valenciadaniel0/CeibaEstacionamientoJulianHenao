package co.com.ceiba.estacionamiento.julian.henao.convertidor;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.julian.henao.entidad.EntidadParqueaderoRegistro;
import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloParqueaderoRegistro;

import java.util.List;

@Component("convertidorParqueaderoRegistro")
public class ConvertidorParqueaderoRegistro {
		
	public List<ModeloParqueaderoRegistro> convertirLista(List<EntidadParqueaderoRegistro> entidadParqueaderoRegistros) {
		List<ModeloParqueaderoRegistro> modeloParqueaderoRegistros = new ArrayList<>();
		for (EntidadParqueaderoRegistro entidadParqueaderoRegistro : entidadParqueaderoRegistros) {
			modeloParqueaderoRegistros.add(convertirEntidadAModelo(entidadParqueaderoRegistro));
		}
		return modeloParqueaderoRegistros;
	}

	public ModeloParqueaderoRegistro convertirEntidadAModelo(EntidadParqueaderoRegistro entidadParqueaderoRegistro) {
		ModeloParqueaderoRegistro modeloParqueaderoRegistro = new ModeloParqueaderoRegistro();		
		ConvertidorVehiculo convertidorVehiculo = new ConvertidorVehiculo();
	    modeloParqueaderoRegistro.setId(entidadParqueaderoRegistro.getId());
	    modeloParqueaderoRegistro.setVehiculo(convertidorVehiculo.convertirEntidadAModelo(entidadParqueaderoRegistro.getVehiculo()));
	    modeloParqueaderoRegistro.setFechaEntrada(entidadParqueaderoRegistro.getFechaEntrada());
	    modeloParqueaderoRegistro.setFechaSalida(entidadParqueaderoRegistro.getFechaSalida());
	    modeloParqueaderoRegistro.setHorasParqueo(entidadParqueaderoRegistro.getHorasParqueo());
	    modeloParqueaderoRegistro.setDiasParqueadero(entidadParqueaderoRegistro.getDiasParqueadero());
	    modeloParqueaderoRegistro.setHorasParqueadero(entidadParqueaderoRegistro.getHorasParqueadero());
	    modeloParqueaderoRegistro.setCostoTotal(entidadParqueaderoRegistro.getCostoTotal());	    
	    return modeloParqueaderoRegistro;
	}

	public EntidadParqueaderoRegistro convertirModeloAEntidad(ModeloParqueaderoRegistro modeloParqueaderoRegistro) {
		EntidadParqueaderoRegistro entidadParqueaderoRegistro = new EntidadParqueaderoRegistro();
		ConvertidorVehiculo convertidorVehiculo = new ConvertidorVehiculo();
		entidadParqueaderoRegistro.setId(modeloParqueaderoRegistro.getId());		
		entidadParqueaderoRegistro.setVehiculo(convertidorVehiculo.convertirModeloAEntidad(modeloParqueaderoRegistro.getVehiculo()));
		entidadParqueaderoRegistro.setFechaEntrada(modeloParqueaderoRegistro.getFechaEntrada());
		entidadParqueaderoRegistro.setFechaSalida(modeloParqueaderoRegistro.getFechaSalida());
		entidadParqueaderoRegistro.setHorasParqueo(modeloParqueaderoRegistro.getHorasParqueo());
		entidadParqueaderoRegistro.setDiasParqueadero(modeloParqueaderoRegistro.getDiasParqueadero());
		entidadParqueaderoRegistro.setHorasParqueadero(modeloParqueaderoRegistro.getHorasParqueadero());
		entidadParqueaderoRegistro.setCostoTotal(modeloParqueaderoRegistro.getCostoTotal());
		return entidadParqueaderoRegistro;
	}

}
