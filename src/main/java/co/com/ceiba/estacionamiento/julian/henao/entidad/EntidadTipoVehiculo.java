package co.com.ceiba.estacionamiento.julian.henao.entidad;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloTipoVehiculo;

import javax.persistence.SqlResultSetMapping;
import javax.persistence.ConstructorResult;
import javax.persistence.ColumnResult;

@SuppressWarnings("serial")
@Entity
@Table(name="tipovehiculo")
@SqlResultSetMapping(
        name="ModeloTipoVehiculo",
        classes=@ConstructorResult(
                targetClass=ModeloTipoVehiculo.class,
                columns={@ColumnResult(name="id_tipo_vehiculo"),
                        @ColumnResult(name="descripcion")}))
public class EntidadTipoVehiculo implements Serializable {
	
	@Id    
    @Column(name="id_tipo_vehiculo")
	private int id;
	
	@Column(name="descripcion")
	private String descripcion;
	
	public EntidadTipoVehiculo(){
		// Construtor creado para el uso de la conversión de entidad a Modelo
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}		
	
}
