package co.com.ceiba.estacionamiento.julian.henao.builderstest;

import java.time.LocalDateTime;

import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloParqueaderoRegistro;
import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloTipoVehiculo;
import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloVehiculo;

public class ParqueaderoRegistroBuilder {
	
	private int id;
	private ModeloVehiculo vehiculo;
	private LocalDateTime fechaEntrada;

	public ParqueaderoRegistroBuilder() {
		this.id = 1;
		this.vehiculo = new ModeloVehiculo("ABC123", new ModeloTipoVehiculo(1, "Automovil"), 0);
		this.fechaEntrada = LocalDateTime.now();
	}
	
	public ModeloParqueaderoRegistro build() {
		return new ModeloParqueaderoRegistro(id, vehiculo, fechaEntrada);
	}
	
	public ParqueaderoRegistroBuilder conId(int id) {
		this.id = id;
		return this;
	}
	
	public ParqueaderoRegistroBuilder conVehiculo(ModeloVehiculo vehiculo) {
		this.vehiculo = vehiculo;
		return this;
	}
	
	public ParqueaderoRegistroBuilder conFechaEntrada(LocalDateTime fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
		return this;
	}
	
}
