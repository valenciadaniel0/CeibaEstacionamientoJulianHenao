package co.com.ceiba.estacionamiento.julian.henao.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class IngresarVehiculo extends PageObject {

	@FindBy(id = "placa")
	private WebElement placa;

	@FindBy(id = "tipoVehiculo")
	private WebElement tipoVehiculo;

	@FindBy(id = "cilindraje")
	private WebElement cilindraje;

	@FindBy(id = "enviar")
	private WebElement botonEnviar;

	@FindBy(id = "exito")
	private WebElement mensajeExitoso;

	@FindBy(id = "fracaso")
	private WebElement mensajeFracaso;

	@FindBy(id = "errorPlaca")
	private WebElement mensajeErrorPlaca;

	@FindBy(id = "errorTipoVehiculo")
	private WebElement mensajeErrorTipoVehiculo;

	@FindBy(id = "errorCilindraje")
	private WebElement mensajeErrorCilindraje;

	public IngresarVehiculo(WebDriver driver) {
		super(driver);
	}

	public void ingresarPlaca(String placa) {
		this.placa.clear();
		this.placa.sendKeys(placa);
	}

	public void seleccionarTipoVehiculo(String tipoVehiculo) {
		new Select(this.tipoVehiculo).selectByVisibleText(tipoVehiculo);
	}

	public void ingresarCilindraje(String cilindraje) {
		this.cilindraje.clear();
		this.cilindraje.sendKeys(cilindraje);
	}

	public void botonIngresarVehiculo() {
		this.botonEnviar.click();
	}

	public String getMensajeExito() {
		return this.mensajeExitoso.getText();
	}
	
	public String getMensajeFracaso() {
		return this.mensajeFracaso.getText();
	}
	
	public String getMensajeErrorPlaca() {
		return this.mensajeErrorPlaca.getText();
	}

	public String getMensajeErrorTipoVehiculo() {
		return this.mensajeErrorTipoVehiculo.getText();
	}

	public String getMensajeErrorCilindraje() {
		return this.mensajeErrorCilindraje.getText();
	}

}
