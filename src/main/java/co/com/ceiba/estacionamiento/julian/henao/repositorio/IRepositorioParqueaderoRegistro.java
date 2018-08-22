package co.com.ceiba.estacionamiento.julian.henao.repositorio;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.com.ceiba.estacionamiento.julian.henao.entidad.EntidadParqueaderoRegistro;

/**
 * Se encarga de las peticiones en la base de datos
 * @author julian.henao
 *
 */
@Repository("repositorioParqueaderoRegistro")
public interface IRepositorioParqueaderoRegistro extends JpaRepository<EntidadParqueaderoRegistro, Serializable>{ 
	
	public abstract EntidadParqueaderoRegistro findById(int id); //
	public abstract List<EntidadParqueaderoRegistro> findByVehiculoId(int id);	
	
	public abstract EntidadParqueaderoRegistro findByVehiculoIdAndFechaSalidaIsNull(int id);
	public abstract List<EntidadParqueaderoRegistro> findByFechaSalidaIsNotNull(); //
	public abstract List<EntidadParqueaderoRegistro> findByVehiculoTipoVehiculoIdAndFechaSalidaIsNull(int id); //
	
	public abstract EntidadParqueaderoRegistro findByIdAndVehiculoPlacaAndFechaSalidaIsNull(int id, String placa);
	
}
