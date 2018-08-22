package co.com.ceiba.estacionamiento.julian.henao.controlador;

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

import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloParqueaderoEspacioDisponible;
import co.com.ceiba.estacionamiento.julian.henao.servicio.ServicioParqueaderoEspacioDisponible;

@RestController
@RequestMapping("/estacionamiento/espacio")
@CrossOrigin(origins = "http://localhost:4200")
public class ControladorParqueaderoEspacioDisponible {
	
	
	@Autowired
	@Qualifier("servicioParqueaderoEspacioDisponible")
	private ServicioParqueaderoEspacioDisponible servicioParqueaderoEspacioDisponible; 
	
	// Por cada peticion nos trae un header y un body del tipo Json
	// Con RequestBody llamo y con el Valid lo convierto en entityVehiculo
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public ResponseEntity<String> crear(@RequestBody ModeloParqueaderoEspacioDisponible modeloParqueaderoEspacioDisponible){
		servicioParqueaderoEspacioDisponible.crear(modeloParqueaderoEspacioDisponible);
		return ResponseEntity.status(HttpStatus.CREATED).body("Espacio Ingresado exitosamente");
	}
     
	@RequestMapping(value="", method=RequestMethod.PUT)
	public ResponseEntity<String> actualizar(@RequestBody ModeloParqueaderoEspacioDisponible modeloParqueaderoEspacioDisponible){
		servicioParqueaderoEspacioDisponible.actualizar(modeloParqueaderoEspacioDisponible);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Espacio Actualizado exitosamente");
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<ModeloParqueaderoEspacioDisponible> obtenerEspacioDisponiblePorTipoVehiculo(@PathVariable("id") int idTipoVehiculo){
		return ResponseEntity.status(HttpStatus.OK).body(servicioParqueaderoEspacioDisponible.obtenerEspacioDisponiblePorTipoVehiculo(idTipoVehiculo));
	}
}