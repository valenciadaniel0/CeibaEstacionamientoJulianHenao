package co.com.ceiba.estacionamiento.julian.henao.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class IngresarVehiculo extends PageObject {

	@FindBy(id="placa")
	private WebElement placa;
	
	@FindBy(id="tipoVehiculo")
	private WebElement tipoVehiculo;
	
	@FindBy(id="cilindraje")
	private WebElement cilindraje;
	
	@FindBy(id="enviar")
	private WebElement botonEnviar;
	
	@FindBy(id="exito")
	private WebElement mensajeExitoso;
	
	@FindBy(id="fracaso")
	private WebElement mensajeFracaso;
	
	@FindBy(id="errorPlaca")
	private WebElement mensajeErrorPlaca;
	
	@FindBy(id="errorTipoVehiculo")
	private WebElement mensajeErrorTipoVehiculo;
	
	@FindBy(id="errorCilindraje")
	private WebElement mensajeErrorCilindraje;	
	
	public IngresarVehiculo(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	public void ingresarPlaca(String placa){
		this.placa.clear();
		this.placa.sendKeys(placa);
	}
	
	public void enviar(){
		botonEnviar.click();        
    }
	
}
