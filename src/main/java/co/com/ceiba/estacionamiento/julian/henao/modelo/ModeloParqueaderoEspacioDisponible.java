package co.com.ceiba.estacionamiento.julian.henao.modelo;

public class ModeloParqueaderoEspacioDisponible {

	private int id;
	private ModeloTipoVehiculo tipoVehiculo;	
	private int limiteEspacio;
	private int espacioActual;
	
	public ModeloParqueaderoEspacioDisponible(){}

	public ModeloParqueaderoEspacioDisponible(int id, ModeloTipoVehiculo tipoVehiculo, int limiteEspacio,
			int espacioActual) {
		this.id = id;
		this.tipoVehiculo = tipoVehiculo;
		this.limiteEspacio = limiteEspacio;
		this.espacioActual = espacioActual;
	}	
	
	public void aumentarEspacio(){
		this.espacioActual++;
	}
	
	public void disminuirEspacio(){
		this.espacioActual--;
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

	public int getLimiteEspacio() {
		return limiteEspacio;
	}

	public void setLimiteEspacio(int limiteEspacio) {
		this.limiteEspacio = limiteEspacio;
	}

	public int getEspacioActual() {
		return espacioActual;
	}

	public void setEspacioActual(int espacioActual) {
		this.espacioActual = espacioActual;
	}		
	
}
