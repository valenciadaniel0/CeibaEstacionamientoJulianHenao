package co.com.ceiba.estacionamiento.julian.henao.dominioparqueadero;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.julian.henao.excepcion.ExcepcionSobreCosto;
import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloParqueaderoEspacioDisponible;
import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloParqueaderoRegistro;
import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloVehiculo;
import co.com.ceiba.estacionamiento.julian.henao.servicio.ServicioParqueaderoEspacioDisponible;
import co.com.ceiba.estacionamiento.julian.henao.servicio.ServicioParqueaderoRegistro;
import co.com.ceiba.estacionamiento.julian.henao.servicio.ServicioVehiculo;
import co.com.ceiba.estacionamiento.julian.henao.validacion.entrada.ValidacionEntrada;
import co.com.ceiba.estacionamiento.julian.henao.validacion.entrada.ValidacionesEntrada;
import co.com.ceiba.estacionamiento.julian.henao.validacion.salida.ValidacionSalida;
import co.com.ceiba.estacionamiento.julian.henao.validacion.salida.ValidacionesSalida;

/**
 * Valida la entrada de vehiculo Registra la entrada Registra la salida
 */
@Component("vigilante")
public class Vigilante {

	@Autowired
	@Qualifier("validacionesEntrada")
	private ValidacionesEntrada validacionesEntrada;

	@Autowired
	@Qualifier("validacionesSalida")
	private ValidacionesSalida validacionesSalida;

	@Autowired
	@Qualifier("servicioVehiculo")
	private ServicioVehiculo servicioVehiculo;

	@Autowired
	@Qualifier("servicioParqueaderoEspacioDisponible")
	private ServicioParqueaderoEspacioDisponible servicioParqueaderoEspacioDisponible;

	@Autowired
	@Qualifier("servicioParqueaderoRegistro")
	private ServicioParqueaderoRegistro servicioParqueaderoRegistro;

	@Autowired
	@Qualifier("calendario")
	private Calendario calendario;

	@Autowired
	@Qualifier("calculadora")
	private Calculadora calculadora;

	/**
	 * Valida las reglas del negocio, crea el vehiculo (si no esta registrado) y
	 * registra mediante el idVehiculo el Registro del Parqueadero con la fecha
	 * actual
	 * 
	 * @param modeloVehiculo
	 *            vehiculo que envia el frontend
	 */
	public void registrarEntrada(ModeloVehiculo modeloVehiculo) {

		for (ValidacionEntrada validacion : validacionesEntrada.validacionesEntrada()) {
			validacion.validar(modeloVehiculo);
		}

		if (servicioVehiculo.obtenerPorPlaca(modeloVehiculo.getPlaca()) == null) {
			// Si NO se encuentra registrado se debe de crear el Vehiculo
			servicioVehiculo.insertar(modeloVehiculo);
		}

		crearRegistro(modeloVehiculo.getPlaca(), modeloVehiculo.getTipoVehiculo().getId());

	}

	/**
	 * Crea el registro de parqueo del vehiculo y aumenta el espacio ocupado
	 * 
	 * @param placa
	 *            del vehiculo
	 */
	private void crearRegistro(String placa, int idTipoVehiculo) {
		servicioParqueaderoRegistro.insertar(new ModeloParqueaderoRegistro(servicioVehiculo.obtenerPorPlaca(placa),
				calendario.obtenerFechaActual()));
		ModeloParqueaderoEspacioDisponible modeloParqueaderoEspacioDisponible = servicioParqueaderoEspacioDisponible
				.obtenerEspacioDisponiblePorTipoVehiculo(idTipoVehiculo);
		modeloParqueaderoEspacioDisponible.aumentarEspacio();
		servicioParqueaderoEspacioDisponible.actualizar(modeloParqueaderoEspacioDisponible);
	}

	
	/**
	 * @param idRegistro id del registro del parqueadero
	 * @param placa del vehiculo a salir
	 */
	public void registrarSalida(int idRegistro, String placa) {
		ExcepcionSobreCosto esc = null;
		ModeloParqueaderoRegistro modeloParqueaderoRegistro = null;
		try {
			modeloParqueaderoRegistro = (servicioParqueaderoRegistro.obtenerRegistroPorIdYPorPlacaSinSalir(idRegistro,
					placa));

			for (ValidacionSalida validacion : validacionesSalida.validacionesSalida()) {
				validacion.validar(idRegistro, placa);
			}

		} catch (ExcepcionSobreCosto e) {
			esc = e;
		}
		generandoRegistroSalida(modeloParqueaderoRegistro, esc);
		/*
		 * En caso de sobreCosto lance la excepción, esto para el mensaje
		 * informativo en el FrontEnd
		 */
		if (esc != null) {
			throw esc;
		}
	}

	private void generandoRegistroSalida(ModeloParqueaderoRegistro modeloParqueaderoRegistro, ExcepcionSobreCosto esc) {
		if (modeloParqueaderoRegistro != null) {

			modeloParqueaderoRegistro.setFechaSalida(calendario.obtenerFechaActual());
			calculadora.calcularCostoParqueadero(modeloParqueaderoRegistro, calculadora.calcularHorasParqueadero(
					modeloParqueaderoRegistro.getFechaEntrada(), modeloParqueaderoRegistro.getFechaSalida()));

			// Si se genero una excepción de sobreCosto hago el aumento
			if (esc != null) {
				int sobreCosto = 2000;
				modeloParqueaderoRegistro.setCostoTotal(modeloParqueaderoRegistro.getCostoTotal() + sobreCosto);
			}

			// ALmaceno el registro
			servicioParqueaderoRegistro.insertar(modeloParqueaderoRegistro);

			// Disminuyo el campo espacio actual del parqueadero para aumentar capacidad 
			ModeloParqueaderoEspacioDisponible modeloParqueaderoEspacioDisponible = servicioParqueaderoEspacioDisponible
					.obtenerEspacioDisponiblePorTipoVehiculo(
							modeloParqueaderoRegistro.getVehiculo().getTipoVehiculo().getId());
			modeloParqueaderoEspacioDisponible.disminuirEspacio();
			servicioParqueaderoEspacioDisponible.actualizar(modeloParqueaderoEspacioDisponible);
		}
	}
	
}
