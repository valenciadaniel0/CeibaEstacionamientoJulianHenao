package co.com.ceiba.estacionamiento.julian.henao.entidad;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.persistence.EntityResult;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloTipoVehiculo;
import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloVehiculo;

/**
 * Serializable ayuda a la conversión de Hibernate y jpa
 * 
 * @author julian.henao
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "vehiculo")
//@SqlResultSetMappings({
//	  @SqlResultSetMapping(
//	      name="ModeloVehiculo",
//	      entities={@EntityResult(entityClass=ModeloVehiculo.class),
//	                @EntityResult(entityClass=ModeloTipoVehiculo.class)}
//	  )
//	})
public class EntidadVehiculo implements Serializable {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@GeneratedValue
	@Id
	@Column(name = "id_vehiculo")
	private int id;

	@Column(name = "placa")
	private String placa;

	@JsonManagedReference
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tipo_vehiculo")
	private EntidadTipoVehiculo tipoVehiculo;

	@Column(name = "cilindraje")
	private double cilindraje;

	public EntidadVehiculo() {
		// Construtor creado para el uso de la conversión de entidad a Modelo
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public EntidadTipoVehiculo getTipoVehiculo() {
		return tipoVehiculo;
	}

	public void setTipoVehiculo(EntidadTipoVehiculo tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}

	public double getCilindraje() {
		return cilindraje;
	}

	public void setCilindraje(double cilindraje) {
		this.cilindraje = cilindraje;
	}

}
