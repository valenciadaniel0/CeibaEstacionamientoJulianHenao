package co.com.ceiba.estacionamiento.julian.henao.modelo;


public class ModeloTipoVehiculo {

	private int id;
	private String descripcion;
	
	public ModeloTipoVehiculo(){
		
	}
	
	public ModeloTipoVehiculo(int id, String descripcion){
		this.id = id;
		this.descripcion = descripcion;
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
