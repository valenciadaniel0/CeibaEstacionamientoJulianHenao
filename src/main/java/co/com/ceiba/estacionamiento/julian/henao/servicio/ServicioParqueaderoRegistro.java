package co.com.ceiba.estacionamiento.julian.henao.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import co.com.ceiba.estacionamiento.julian.henao.convertidor.ConvertidorParqueaderoRegistro;
import co.com.ceiba.estacionamiento.julian.henao.entidad.EntidadParqueaderoRegistro;
import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloParqueaderoRegistro;
import co.com.ceiba.estacionamiento.julian.henao.repositorio.IRepositorioParqueaderoRegistro;

@Service("servicioParqueaderoRegistro")
public class ServicioParqueaderoRegistro {
	 
	@Autowired
	@Qualifier("repositorioParqueaderoRegistro")
	private IRepositorioParqueaderoRegistro iRepositorioParqueaderoRegistro;

	@Autowired
	private ConvertidorParqueaderoRegistro convertidorParqueaderoRegistro;	

	public void insertar(ModeloParqueaderoRegistro modeloParqueaderoRegistro) {
		iRepositorioParqueaderoRegistro.save(convertidorParqueaderoRegistro.convertirModeloAEntidad(modeloParqueaderoRegistro));				
	}

	public void actualizar(ModeloParqueaderoRegistro modeloParqueaderoRegistro) {		
		if (iRepositorioParqueaderoRegistro.findById(modeloParqueaderoRegistro.getId()) != null) {
			iRepositorioParqueaderoRegistro.save(convertidorParqueaderoRegistro.convertirModeloAEntidad(modeloParqueaderoRegistro));		
		}		
	}

	public void borrar(int idParqueaderoRegistro) {		
		EntidadParqueaderoRegistro entidadParqueaderoRegistro = iRepositorioParqueaderoRegistro.findById(idParqueaderoRegistro);
		iRepositorioParqueaderoRegistro.delete(entidadParqueaderoRegistro);		
	}

	public ModeloParqueaderoRegistro obtenerRegistroPorId(int id) { 
		return convertidorParqueaderoRegistro.convertirEntidadAModelo(iRepositorioParqueaderoRegistro.findById(id));
	}
	
	public List<ModeloParqueaderoRegistro> obtenerRegistrosAnteriores() {
		return convertidorParqueaderoRegistro.convertirLista(iRepositorioParqueaderoRegistro.findByFechaSalidaIsNotNull());
	}
	
	public List<ModeloParqueaderoRegistro> obtenerRegistrosPorTipoVehiculoSinSalir(int idTipoVehiculo) {
		return convertidorParqueaderoRegistro.convertirLista(iRepositorioParqueaderoRegistro.findByVehiculoTipoVehiculoIdAndFechaSalidaIsNull(idTipoVehiculo));
	}
	
	public List<ModeloParqueaderoRegistro> obtenerRegistrosPorVehiculo(int idVehiculo) {
		return convertidorParqueaderoRegistro.convertirLista(iRepositorioParqueaderoRegistro.findByVehiculoId(idVehiculo));
	}		

	public ModeloParqueaderoRegistro obtenerRegistrosPorVehiculosPorIdSinSalir(int id) {
		EntidadParqueaderoRegistro entidadParqueaderoRegistro = iRepositorioParqueaderoRegistro.findByVehiculoIdAndFechaSalidaIsNull(id);
		if(entidadParqueaderoRegistro == null){
			return null;
		}else{
			return convertidorParqueaderoRegistro.convertirEntidadAModelo(entidadParqueaderoRegistro);
		}
	}
	
	public ModeloParqueaderoRegistro obtenerRegistroPorIdYPorPlacaSinSalir(int id, String placa) {	
		EntidadParqueaderoRegistro entidadParqueaderoRegistro = iRepositorioParqueaderoRegistro.findByIdAndVehiculoPlacaAndFechaSalidaIsNull(id, placa);
		if(entidadParqueaderoRegistro == null){
			return null;
		}else{
			return convertidorParqueaderoRegistro.convertirEntidadAModelo(entidadParqueaderoRegistro);
		}
	}
		
}
