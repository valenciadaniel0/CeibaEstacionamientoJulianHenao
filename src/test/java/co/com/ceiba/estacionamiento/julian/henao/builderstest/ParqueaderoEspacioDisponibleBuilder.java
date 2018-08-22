package co.com.ceiba.estacionamiento.julian.henao.builderstest;

import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloParqueaderoEspacioDisponible;
import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloTipoVehiculo;

public class ParqueaderoEspacioDisponibleBuilder {

	private int id;
	private ModeloTipoVehiculo tipoVehiculo;	
	private int limiteEspacio;
	private int espacioActual;
	
	public ParqueaderoEspacioDisponibleBuilder() {
		this.id = 1;
		this.tipoVehiculo = new ModeloTipoVehiculo(1, "Automovil");		
		this.limiteEspacio = 20;
		this.espacioActual = 10;
	}
	
	public ModeloParqueaderoEspacioDisponible build() {
		return new ModeloParqueaderoEspacioDisponible( id,  tipoVehiculo, limiteEspacio,
				espacioActual);
	}
	
	public ParqueaderoEspacioDisponibleBuilder conId(int id) {
		this.id = id;
		return this;
	}
	
	public ParqueaderoEspacioDisponibleBuilder conTipoVehiculo(ModeloTipoVehiculo tipo) {
		this.tipoVehiculo = tipo;
		return this;
	}
	
	public ParqueaderoEspacioDisponibleBuilder conLimiteEspacio(int limiteEspacio) {
		this.limiteEspacio = limiteEspacio;
		return this;
	}
	
	public ParqueaderoEspacioDisponibleBuilder conEspacioActual(int espacioActual) {
		this.espacioActual = espacioActual;
		return this;
	}
	
}
