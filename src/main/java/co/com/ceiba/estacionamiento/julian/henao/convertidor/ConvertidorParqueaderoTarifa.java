package co.com.ceiba.estacionamiento.julian.henao.convertidor;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.julian.henao.entidad.EntidadParqueaderoTarifa;
import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloParqueaderoTarifa;

import java.util.List;

@Component("convertidorParqueaderoTarifa")
public class ConvertidorParqueaderoTarifa {

	
	public List<ModeloParqueaderoTarifa> convertirLista(List<EntidadParqueaderoTarifa> entidadParquederoTarifas) {
		List<ModeloParqueaderoTarifa> parquederoTarifas = new ArrayList<>();
		for (EntidadParqueaderoTarifa entidadParqueaderoTarifa : entidadParquederoTarifas) {
			parquederoTarifas.add(convertirEntidadAModelo( entidadParqueaderoTarifa));
		}
		return parquederoTarifas;
	}	

	public ModeloParqueaderoTarifa convertirEntidadAModelo(EntidadParqueaderoTarifa entidadParqueaderoTarifa) {
		ModeloParqueaderoTarifa modeloParqueaderoTarifa = new ModeloParqueaderoTarifa();
		ConvertidorTipoVehiculo convertidorTipoVehiculo = new ConvertidorTipoVehiculo();		
		modeloParqueaderoTarifa.setId(entidadParqueaderoTarifa.getId());		
		modeloParqueaderoTarifa.setTipoVehiculo(convertidorTipoVehiculo.convertirEntidadAModelo(entidadParqueaderoTarifa.getTipoVehiculo()));
		modeloParqueaderoTarifa.setCostoHora(entidadParqueaderoTarifa.getCostoHora());
		modeloParqueaderoTarifa.setCostoDia(entidadParqueaderoTarifa.getCostoDia());
		modeloParqueaderoTarifa.setHorasCobroDia(entidadParqueaderoTarifa.getHorasCobroDia());
		return modeloParqueaderoTarifa;
	}

	public EntidadParqueaderoTarifa convertirModeloAEntidad(ModeloParqueaderoTarifa modeloParqueaderoTarifa) {
		EntidadParqueaderoTarifa entidadParqueaderoTarifa = new EntidadParqueaderoTarifa();
		ConvertidorTipoVehiculo convertidorTipoVehiculo = new ConvertidorTipoVehiculo();		
		entidadParqueaderoTarifa.setId(modeloParqueaderoTarifa.getId());		
		entidadParqueaderoTarifa.setTipoVehiculo(convertidorTipoVehiculo.convertirModeloAEntidad(modeloParqueaderoTarifa.getTipoVehiculo()));
		entidadParqueaderoTarifa.setCostoHora(modeloParqueaderoTarifa.getCostoHora());
		entidadParqueaderoTarifa.setCostoDia(modeloParqueaderoTarifa.getCostoDia());
		entidadParqueaderoTarifa.setHorasCobroDia(modeloParqueaderoTarifa.getHorasCobroDia());
		return entidadParqueaderoTarifa;
	}
	
}
