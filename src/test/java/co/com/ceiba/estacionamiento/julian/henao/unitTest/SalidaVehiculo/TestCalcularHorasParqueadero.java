package co.com.ceiba.estacionamiento.julian.henao.unitTest.SalidaVehiculo;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import co.com.ceiba.estacionamiento.julian.henao.dominioparqueadero.Calculadora;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCalcularHorasParqueadero {

	
	 @Autowired
	 Calculadora calculadora;
	
	@Test
	public void HorasCalculadasV1() {
		
		LocalDateTime fechaEntrada = LocalDateTime.of(2018, 8, 19, 10, 20,10);
		LocalDateTime fechaSalida = LocalDateTime.of(2018, 8, 20, 13, 20,9);
		long horasEsperadas = 27;
		long horasCalculadas = calculadora.calcularHorasParqueadero(fechaEntrada,fechaSalida);
		
		assertEquals(horasEsperadas, horasCalculadas);
	}

	@Test
	public void HorasCalculadasV2() {
		
		LocalDateTime fechaEntrada = LocalDateTime.of(2018, 8, 19, 10, 20,00);
		LocalDateTime fechaSalida = LocalDateTime.of(2018, 8, 20, 13, 50,10);
		long horasEsperadas = 28;
		long horasCalculadas = calculadora.calcularHorasParqueadero(fechaEntrada,fechaSalida);
		
		assertEquals(horasEsperadas, horasCalculadas);
	}

	
}
