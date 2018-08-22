package co.com.ceiba.estacionamiento.julian.henao.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.stereotype.Service;

import co.com.ceiba.estacionamiento.julian.henao.convertidor.ConvertidorTipoVehiculo;
import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloTipoVehiculo;
import co.com.ceiba.estacionamiento.julian.henao.repositorio.IRepositorioTipoVehiculo;

@Service("servicioTipoVehiculo")
public class ServicioTipoVehiculo {
	 
	@Autowired
	@Qualifier("repositorioTipoVehiculo")
	private IRepositorioTipoVehiculo iRepositorioTipoVehiculo;

	@Autowired
	@Qualifier("convertidorTipoVehiculo") 
	private ConvertidorTipoVehiculo convertidorTipoVehiculo;


	public void crear(ModeloTipoVehiculo modeloTipoVehiculo) {
		iRepositorioTipoVehiculo.save(convertidorTipoVehiculo.convertirModeloAEntidad(modeloTipoVehiculo));
	}

	public List<ModeloTipoVehiculo> obtenerTipoVehiculos() {
		return convertidorTipoVehiculo.convertirLista(iRepositorioTipoVehiculo.findAll());
	}

	public ModeloTipoVehiculo obtenerporId(int idTipoVehiculo) {
		return convertidorTipoVehiculo.convertirEntidadAModelo(iRepositorioTipoVehiculo.findById(idTipoVehiculo));
	}
}
