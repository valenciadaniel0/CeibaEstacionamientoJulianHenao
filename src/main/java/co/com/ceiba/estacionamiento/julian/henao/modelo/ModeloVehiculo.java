package co.com.ceiba.estacionamiento.julian.henao.modelo;

public class ModeloVehiculo {

	private int id;
	private String placa;
	private ModeloTipoVehiculo tipoVehiculo; // String
	private long cilindraje;

	public ModeloVehiculo() {
		
	}

	public ModeloVehiculo(String placa, ModeloTipoVehiculo tipoVehiculo, long cilindraje) {
		this.placa = placa;
		this.tipoVehiculo = tipoVehiculo;
		this.cilindraje = cilindraje;
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

	public ModeloTipoVehiculo getTipoVehiculo() {
		return tipoVehiculo;
	}

	public void setTipoVehiculo(ModeloTipoVehiculo tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}

	public long getCilindraje() {
		return cilindraje;
	}

	public void setCilindraje(long cilindraje) {
		this.cilindraje = cilindraje;
	}

}
