package co.com.ceiba.estacionamiento.julian.henao.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloParqueaderoRegistro;
import co.com.ceiba.estacionamiento.julian.henao.servicio.ServicioParqueaderoRegistro;

@RestController
@RequestMapping("/estacionamiento/registros")
@CrossOrigin(origins = "http://localhost:4200")
public class ControladorParqueaderoRegistro {

	@Autowired
	@Qualifier("servicioParqueaderoRegistro")
	private ServicioParqueaderoRegistro servicioParqueaderoRegistro;

	// Por cada peticion nos trae un header y un body del tipo Json
	// Con RequestBody llamo y con el Valid lo convierto en entityParqueaderoRegistro

	
	@RequestMapping(value="", method=RequestMethod.POST)
	public ResponseEntity<String> crear(@RequestBody ModeloParqueaderoRegistro modeloParqueaderoRegistro) {
		servicioParqueaderoRegistro.insertar(modeloParqueaderoRegistro);
		return ResponseEntity.status(HttpStatus.CREATED).body("Registro Ingresado exitosamente");
	}

	@RequestMapping(value="/reciente/{id}", method=RequestMethod.GET)
	public ResponseEntity<ModeloParqueaderoRegistro> obtenerRegistros(@PathVariable("id") int idRegistro) {
		return ResponseEntity.status(HttpStatus.OK).body(servicioParqueaderoRegistro.obtenerRegistroPorId(idRegistro));
	}
	
	@RequestMapping(value="/antes", method=RequestMethod.GET)
	public ResponseEntity<List<ModeloParqueaderoRegistro>> obtenerRegistrosAnteriores() {
		return ResponseEntity.status(HttpStatus.OK).body(servicioParqueaderoRegistro.obtenerRegistrosAnteriores());
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<List<ModeloParqueaderoRegistro>> obtenerRegistrosPorTipoVehiculoSinSalir(@PathVariable("id") int idTipoVehiculo) {
		return ResponseEntity.status(HttpStatus.OK).body(servicioParqueaderoRegistro.obtenerRegistrosPorTipoVehiculoSinSalir(idTipoVehiculo));
	}
		
}