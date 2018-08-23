package co.com.ceiba.estacionamiento.julian.henao.repositorio;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.com.ceiba.estacionamiento.julian.henao.entidad.EntidadVehiculo;


/**
 * Se encarga de las peticiones en la base de datos
 * @author julian.henao
 *
 */


@Repository("repositorioVehiculo")
public interface IRepositorioVehiculo extends JpaRepository<EntidadVehiculo, Serializable>{ 
	
	public abstract EntidadVehiculo findByPlaca(String placa);	
	public abstract List<EntidadVehiculo> findAll();
	
}
