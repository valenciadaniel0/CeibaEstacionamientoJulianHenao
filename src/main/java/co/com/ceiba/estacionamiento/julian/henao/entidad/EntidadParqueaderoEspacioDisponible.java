package co.com.ceiba.estacionamiento.julian.henao.entidad;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@SuppressWarnings("serial")
@Entity
@Table(name="espacio")
public class EntidadParqueaderoEspacioDisponible implements Serializable{

	@Id
	@Column(name = "id_espacio")
	private int id;
	
	@JsonManagedReference
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tipo_vehiculo")
	private EntidadTipoVehiculo tipoVehiculo;	
	
	@Column(name = "limite_espacio")
	private int limiteEspacio;
	
	@Column(name = "espacio_actual")
	private int espacioActual;		
	
	public EntidadParqueaderoEspacioDisponible(){
		// Construtor creado para el uso de la conversión de entidad a Modelo
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public EntidadTipoVehiculo getTipoVehiculo() {
		return tipoVehiculo;
	}

	public void setTipoVehiculo(EntidadTipoVehiculo tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}

	public int getLimiteEspacio() {
		return limiteEspacio;
	}

	public void setLimiteEspacio(int limiteEspacio) {
		this.limiteEspacio = limiteEspacio;
	}

	public int getEspacioActual() {
		return espacioActual;
	}

	public void setEspacioActual(int espacioActual) {
		this.espacioActual = espacioActual;
	}	
	
}
