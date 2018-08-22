package co.com.ceiba.estacionamiento.julian.henao.builderstest;

import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloParqueaderoTarifa;
import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloTipoVehiculo;

public class ParqueaderoTarifaBuilder {

	private int id;
	private ModeloTipoVehiculo tipoVehiculo;
	private long costoHora;
	private long costoDia;
	private int horasCobroDia;
	
	public ParqueaderoTarifaBuilder() {
		this.id = 1;
		this.tipoVehiculo = new ModeloTipoVehiculo(1, "Automovil");
		this.costoHora = 1000;
		this.costoDia = 8000;
		this.horasCobroDia = 9;
	}
	
	public ModeloParqueaderoTarifa build() {
		return new ModeloParqueaderoTarifa( id, tipoVehiculo, costoHora,  costoDia, horasCobroDia);
	}
	
	public ParqueaderoTarifaBuilder conId(int id) {
		this.id = id;
		return this;
	}
	
	public ParqueaderoTarifaBuilder conTipoVehiculo(ModeloTipoVehiculo tipo) {
		this.tipoVehiculo = tipo;
		return this;
	}
	
	public ParqueaderoTarifaBuilder conCostoHora(long costoHora) {
		this.costoHora = costoHora;
		return this;
	}
	
	public ParqueaderoTarifaBuilder conCostoDia(long costoDia) {
		this.costoDia = costoDia;
		return this;
	}
	
	public ParqueaderoTarifaBuilder conHorasCobroDia(int horasCobroDia) {
		this.horasCobroDia = horasCobroDia;
		return this;
	}
	
}
