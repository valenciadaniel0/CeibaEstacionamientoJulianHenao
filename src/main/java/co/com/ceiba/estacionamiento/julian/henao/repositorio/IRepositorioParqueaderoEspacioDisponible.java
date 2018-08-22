package co.com.ceiba.estacionamiento.julian.henao.repositorio;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.com.ceiba.estacionamiento.julian.henao.entidad.EntidadParqueaderoEspacioDisponible;



@Repository("repositorioParqueaderoEspacioDisponible")
public interface IRepositorioParqueaderoEspacioDisponible extends JpaRepository<EntidadParqueaderoEspacioDisponible, Serializable>{
	
	public abstract EntidadParqueaderoEspacioDisponible findById(int id);
	public abstract EntidadParqueaderoEspacioDisponible findByTipoVehiculoId(int id);
				
}
