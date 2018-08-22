package co.com.ceiba.estacionamiento.julian.henao.modelo;

public class ModeloParqueaderoTarifa {

	private int id;
	private ModeloTipoVehiculo tipoVehiculo;
	private long costoHora;
	private long costoDia;
	private int horasCobroDia;
		
	public ModeloParqueaderoTarifa(){}
		
	public ModeloParqueaderoTarifa(int id, ModeloTipoVehiculo tipoVehiculo, long costoHora, long costoDia, int horasCobroDia) {	
		this.id = id;
		this.tipoVehiculo = tipoVehiculo;
		this.costoHora = costoHora;
		this.costoDia = costoDia;
		this.horasCobroDia = horasCobroDia;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ModeloTipoVehiculo getTipoVehiculo() {
		return tipoVehiculo;
	}
	public void setTipoVehiculo(ModeloTipoVehiculo tipoVehiculo) {
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
