package co.com.ceiba.estacionamiento.julian.henao.excepcion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.MULTIPLE_CHOICES)
public class ExcepcionSobreCosto extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExcepcionSobreCosto(String mensaje) {
		super(mensaje);
	}

}
