package co.com.ceiba.estacionamiento.julian.henao.repositorio;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.com.ceiba.estacionamiento.julian.henao.entidad.EntidadTipoVehiculo;


@Repository("repositorioTipoVehiculo")
public interface IRepositorioTipoVehiculo extends JpaRepository<EntidadTipoVehiculo, Serializable>{
	
	public abstract List<EntidadTipoVehiculo> findAll();
	public abstract EntidadTipoVehiculo findById(int id);
	public abstract EntidadTipoVehiculo findByDescripcion(String descripcion);
	
}
