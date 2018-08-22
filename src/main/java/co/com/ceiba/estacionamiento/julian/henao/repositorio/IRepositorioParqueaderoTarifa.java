package co.com.ceiba.estacionamiento.julian.henao.repositorio;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.com.ceiba.estacionamiento.julian.henao.entidad.EntidadParqueaderoTarifa;



@Repository("repositorioParqueaderoTarifa")
public interface IRepositorioParqueaderoTarifa extends JpaRepository<EntidadParqueaderoTarifa, Serializable> {

	public abstract EntidadParqueaderoTarifa findById(int id);
	public abstract EntidadParqueaderoTarifa findByTipoVehiculoId(int id);

}
