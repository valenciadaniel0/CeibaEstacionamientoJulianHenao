package co.com.ceiba.estacionamiento.julian.henao.functionalTest.Runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "Features", glue={"co.com.ceiba.estacionamiento.julian.henao.functionalTest.StepDefinition"})
public class TestIngresarVehiculo {

}
