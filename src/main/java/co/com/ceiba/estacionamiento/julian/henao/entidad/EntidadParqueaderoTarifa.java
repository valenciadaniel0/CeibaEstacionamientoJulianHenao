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

import co.com.ceiba.estacionamiento.julian.henao.entidad.EntidadTipoVehiculo;

@SuppressWarnings("serial")
@Entity
@Table(name="tarifa")
public class EntidadParqueaderoTarifa implements Serializable {

	@Id
	@Column(name = "id_tarifa")
	private int id;
	
	@JsonManagedReference
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tipo_vehiculo")
	private EntidadTipoVehiculo tipoVehiculo;
	
	//, referencedColumnName="id_tipo_vehiculo"
	
	@Column(name="costo_hora")
	private long costoHora;
	
	@Column(name="costo_dia")
	private long costoDia;
	
	@Column(name="horas_cobro_dia")
	private int horasCobroDia;
		
	public EntidadParqueaderoTarifa(){
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
	public long getCostoHora() {
		return costoHora;
	}
	public void setCostoHora(long costoHora) {
		this.costoHora = costoHora;
	}
	public long getCostoDia() {
		return costoDia;
	}
	public void setCostoDia(long costoDia) {
		this.costoDia = costoDia;
	}
	public int getHorasCobroDia() {
		return horasCobroDia;
	}
	public void setHorasCobroDia(int horasCobroDia) {
		this.horasCobroDia = horasCobroDia;
	}	
}
