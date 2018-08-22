package co.com.ceiba.estacionamiento.julian.henao.servicio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.stereotype.Service;

import co.com.ceiba.estacionamiento.julian.henao.convertidor.ConvertidorParqueaderoTarifa;
import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloParqueaderoTarifa;
import co.com.ceiba.estacionamiento.julian.henao.repositorio.IRepositorioParqueaderoTarifa;

@Service("servicioParqueaderoTarifa")
public class ServicioParqueaderoTarifa {

	@Autowired
	@Qualifier("repositorioParqueaderoTarifa") 
	private IRepositorioParqueaderoTarifa repositorioParquederoTarifa;

	@Autowired
	@Qualifier("convertidorParqueaderoTarifa") 
	private ConvertidorParqueaderoTarifa converterParquederoTarifa;

	public void crear(ModeloParqueaderoTarifa modeloParqueaderoTarifa) {
		repositorioParquederoTarifa.save(converterParquederoTarifa.convertirModeloAEntidad(modeloParqueaderoTarifa));
	}

	public List<ModeloParqueaderoTarifa> obtenerTarifas() {
		return converterParquederoTarifa.convertirLista(repositorioParquederoTarifa.findAll());
	}

	public ModeloParqueaderoTarifa obtenerporId(int id) {
		return converterParquederoTarifa.convertirEntidadAModelo(repositorioParquederoTarifa.findById(id));
	}

	public ModeloParqueaderoTarifa obtenerTarifasPorTipoVehiculo(int idTipoVehiculo) {
		return converterParquederoTarifa
				.convertirEntidadAModelo(repositorioParquederoTarifa.findByTipoVehiculoId(idTipoVehiculo));
	}

}
