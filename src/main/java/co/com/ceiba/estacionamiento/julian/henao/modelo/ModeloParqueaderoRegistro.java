package co.com.ceiba.estacionamiento.julian.henao.modelo;

import java.time.LocalDateTime;

public class ModeloParqueaderoRegistro {

	private int id;
	private ModeloVehiculo vehiculo;
	private LocalDateTime fechaEntrada;
	private LocalDateTime fechaSalida;
	private int horasParqueo;
	private int diasParqueadero;
	private int horasParqueadero;
	private long costoTotal;
	
	public ModeloParqueaderoRegistro(){}		
	
	public ModeloParqueaderoRegistro(ModeloVehiculo vehiculo, LocalDateTime fechaEntrada, LocalDateTime fechaSalida,
			int horasParqueo, int diasParqueadero, int horasParqueadero, long costoTotal) {		
		this.vehiculo = vehiculo;
		this.fechaEntrada = fechaEntrada;
		this.fechaSalida = fechaSalida;
		this.horasParqueo = horasParqueo;
		this.diasParqueadero = diasParqueadero;
		this.horasParqueadero = horasParqueadero;
		this.costoTotal = costoTotal;
	}

	public ModeloParqueaderoRegistro(int id, ModeloVehiculo vehiculo, LocalDateTime fechaEntrada) {				
		this(vehiculo,fechaEntrada,null,0,0,0,0);		
	}
	
	public ModeloParqueaderoRegistro(ModeloVehiculo vehiculo, LocalDateTime fechaEntrada) {				
		this(vehiculo,fechaEntrada,null,0,0,0,0);		
	}


		public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ModeloVehiculo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(ModeloVehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	public LocalDateTime getFechaEntrada() {
		return fechaEntrada;
	}

	public void setFechaEntrada(LocalDateTime fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}

	public LocalDateTime getFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida(LocalDateTime fechaSalida) {
		this.fechaSalida = fechaSalida;
	}		

	public int getHorasParqueo() {
		return horasParqueo;
	}

	public void setHorasParqueo(int horasParqueo) {
		this.horasParqueo = horasParqueo;
	}

	public int getDiasParqueadero() {
		return diasParqueadero;
	}

	public void setDiasParqueadero(int diasParqueadero) {
		this.diasParqueadero = diasParqueadero;
	}

	public int getHorasParqueadero() {
		return horasParqueadero;
	}

	public void setHorasParqueadero(int horasParqueadero) {
		this.horasParqueadero = horasParqueadero;
	}

	public long getCostoTotal() {
		return costoTotal;
	}

	public void setCostoTotal(long costoTotal) {
		this.costoTotal = costoTotal;
	}
	

}
