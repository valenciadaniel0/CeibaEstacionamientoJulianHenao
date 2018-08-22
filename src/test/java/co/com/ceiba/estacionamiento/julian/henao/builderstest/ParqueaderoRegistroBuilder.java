package co.com.ceiba.estacionamiento.julian.henao.builderstest;

import java.time.LocalDateTime;

import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloParqueaderoRegistro;
import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloTipoVehiculo;
import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloVehiculo;

public class ParqueaderoRegistroBuilder {
	
	private int id;
	private ModeloVehiculo vehiculo;
	private LocalDateTime fechaEntrada;
	private LocalDateTime fechaSalida;
	private int horasParqueo;
	private int diasParqueadero;
	private int horasParqueadero;
	private long costoTotal;
	
	public ParqueaderoRegistroBuilder() {
		this.id = 1;
		this.vehiculo = new ModeloVehiculo("ABC123", new ModeloTipoVehiculo(1, "Automovil"), 0);
		this.fechaEntrada = LocalDateTime.now();
		this.fechaSalida = null;
		this.horasParqueo = 0;
		this.diasParqueadero = 0;
		this.horasParqueadero = 0;
		this.costoTotal = 0;
	
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
	
	public ParqueaderoRegistroBuilder conFechaSalida(LocalDateTime fechaSalida) {
		this.fechaSalida = fechaSalida;
		return this;
	}
	public ParqueaderoRegistroBuilder conHorasParqueo(int horasParqueo) {
		this.horasParqueo = horasParqueo;
		return this;
	}
	
	public ParqueaderoRegistroBuilder conDiasParqueadero(int diasParqueadero) {
		this.diasParqueadero= diasParqueadero;
		return this;
	}
	
	public ParqueaderoRegistroBuilder conHorasParqueadero(int horasParqueadero) {
		this.horasParqueadero = horasParqueadero;
		return this;
	}
	
	public ParqueaderoRegistroBuilder conCostoTotal(long costoTotal) {
		this.costoTotal = costoTotal;
		return this;
	}
	
}
