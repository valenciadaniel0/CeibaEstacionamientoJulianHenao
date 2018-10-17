package co.com.ceiba.estacionamiento.julian.henao.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.stereotype.Service;

import co.com.ceiba.estacionamiento.julian.henao.convertidor.ConvertidorParqueaderoEspacioDisponible;
import co.com.ceiba.estacionamiento.julian.henao.excepcion.ExcepcionParametroInvalido;
import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloParqueaderoEspacioDisponible;
import co.com.ceiba.estacionamiento.julian.henao.repositorio.IRepositorioParqueaderoEspacioDisponible;

@Service("servicioParqueaderoEspacioDisponible")
public class ServicioParqueaderoEspacioDisponible {
	
	@Autowired
	@Qualifier("repositorioParqueaderoEspacioDisponible")
	private IRepositorioParqueaderoEspacioDisponible irepositorioParquederoEspacioDisponible;

	@Autowired
	@Qualifier("convertidorParqueaderoEspacioDisponible") 
	private ConvertidorParqueaderoEspacioDisponible convertidorParquederoEspacioDisponible;

	public void crear(ModeloParqueaderoEspacioDisponible modeloParqueaderoEspacioDisponible) {
		irepositorioParquederoEspacioDisponible
				.save(convertidorParquederoEspacioDisponible.convertirModeloAEntidad(modeloParqueaderoEspacioDisponible));
	}

	
	public void actualizar(ModeloParqueaderoEspacioDisponible modeloParqueaderoEspacioDisponible) {
		if (irepositorioParquederoEspacioDisponible.findById(modeloParqueaderoEspacioDisponible.getId()) != null) {
			irepositorioParquederoEspacioDisponible.save(
					convertidorParquederoEspacioDisponible.convertirModeloAEntidad(modeloParqueaderoEspacioDisponible));			
		}else{
			throw new ExcepcionParametroInvalido("El id del espacio Disponible no se encuentra registrado");
		}		
	}


	public ModeloParqueaderoEspacioDisponible obtenerEspacioDisponiblePorTipoVehiculo(int idTipoVehiculo) {
		return convertidorParquederoEspacioDisponible
				.convertirEntidadAModelo(irepositorioParquederoEspacioDisponible.findByTipoVehiculoId(idTipoVehiculo));
	}

}
