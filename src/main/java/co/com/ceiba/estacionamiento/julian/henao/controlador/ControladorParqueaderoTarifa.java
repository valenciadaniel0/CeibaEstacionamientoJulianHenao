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

import co.com.ceiba.estacionamiento.julian.henao.modelo.ModeloParqueaderoTarifa;
import co.com.ceiba.estacionamiento.julian.henao.servicio.ServicioParqueaderoTarifa;

@RestController
@RequestMapping("/estacionamiento/tarifa")
@CrossOrigin(origins = "localhost:4200/")
public class ControladorParqueaderoTarifa {
	
	
	@Autowired
	@Qualifier("servicioParqueaderoTarifa")
	private ServicioParqueaderoTarifa servicioParqueaderoTarifa; 
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public ResponseEntity<String> crear(@RequestBody ModeloParqueaderoTarifa modeloParqueaderoTarifa){
		servicioParqueaderoTarifa.crear(modeloParqueaderoTarifa);
		return ResponseEntity.status(HttpStatus.CREATED).body("Tarifa Ingresada exitosamente");
	}  
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public ResponseEntity<List<ModeloParqueaderoTarifa>> obtenerTarifas(){
		return ResponseEntity.status(HttpStatus.OK).body(servicioParqueaderoTarifa.obtenerTarifas());
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<ModeloParqueaderoTarifa> obtenerTarifasPorTipoVehiculo(@PathVariable("id") int idTipoVehiculo){
		return ResponseEntity.status(HttpStatus.OK).body(servicioParqueaderoTarifa.obtenerTarifasPorTipoVehiculo(idTipoVehiculo));
	}
	
}