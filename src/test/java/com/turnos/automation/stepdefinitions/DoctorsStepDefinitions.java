package com.turnos.automation.stepdefinitions;

import com.turnos.automation.questions.DoctorsListIsNotEmpty;
import com.turnos.automation.questions.ResponseContainsDoctorId;
import com.turnos.automation.questions.ResponseContainsToken;
import com.turnos.automation.questions.ResponseStatusCode;
import com.turnos.automation.tasks.CreateDoctor;
import com.turnos.automation.tasks.DeleteDoctor;
import com.turnos.automation.tasks.ListAllDoctors;
import com.turnos.automation.tasks.RegisterUser;
import com.turnos.automation.tasks.UpdateDoctor;
import com.turnos.automation.util.Constants;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.actors.OnStage;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class DoctorsStepDefinitions {

    @Given("que el empleado se autentica en el sistema")
    public void theEmployeeAuthenticates() {
        String uniqueEmail = Constants.TEST_EMAIL_PREFIX + System.currentTimeMillis() + Constants.TEST_EMAIL_DOMAIN;
        OnStage.theActorInTheSpotlight().attemptsTo(
            RegisterUser.withCredentials(Constants.TEST_USER_NAME, uniqueEmail, Constants.TEST_USER_PASSWORD)
        );
    }

    @And("el sistema confirma que la autenticacion fue exitosa")
    public void theSystemConfirmsAuthentication() {
        OnStage.theActorInTheSpotlight().should(
            seeThat(ResponseStatusCode.ofTheLastResponse(), equalTo(Constants.STATUS_CREATED)),
            seeThat(ResponseContainsToken.inTheLastResponse(), is(true))
        );
    }

    @When("el empleado crea un medico con nombre {string} y cedula {string}")
    public void theEmployeeCreatesADoctor(String nombre, String cedula) {
        String uniqueCedula = cedula.length() >= 7
                ? cedula.substring(0, 5) + System.currentTimeMillis() % 100000
                : cedula;
        OnStage.theActorInTheSpotlight().remember("doctorCedula", uniqueCedula);
        OnStage.theActorInTheSpotlight().attemptsTo(
            CreateDoctor.withData(nombre, uniqueCedula)
        );
    }

    @Then("el sistema confirma que el medico fue creado exitosamente")
    public void theSystemConfirmsDoctorCreated() {
        OnStage.theActorInTheSpotlight().should(
            seeThat(ResponseStatusCode.ofTheLastResponse(), equalTo(Constants.STATUS_CREATED)),
            seeThat(ResponseContainsDoctorId.inTheLastResponse(), is(true))
        );
    }

    @When("el empleado consulta la lista de medicos activos")
    public void theEmployeeListsAllDoctors() {
        OnStage.theActorInTheSpotlight().attemptsTo(
            ListAllDoctors.fromApi()
        );
    }

    @Then("el sistema retorna la lista de medicos con al menos un registro")
    public void theSystemReturnsDoctorsList() {
        OnStage.theActorInTheSpotlight().should(
            seeThat(ResponseStatusCode.ofTheLastResponse(), equalTo(Constants.STATUS_OK)),
            seeThat(DoctorsListIsNotEmpty.inTheLastResponse(), is(true))
        );
    }

    @When("el empleado actualiza el medico con consultorio {string} y franja {string}")
    public void theEmployeeUpdatesTheDoctor(String office, String shift) {
        OnStage.theActorInTheSpotlight().attemptsTo(
            UpdateDoctor.withShift(office, shift)
        );
    }

    @Then("el sistema confirma que el medico fue actualizado exitosamente")
    public void theSystemConfirmsDoctorUpdated() {
        OnStage.theActorInTheSpotlight().should(
            seeThat(ResponseStatusCode.ofTheLastResponse(), equalTo(Constants.STATUS_OK))
        );
    }

    @When("el empleado da de baja al medico creado")
    public void theEmployeeDeletesTheDoctor() {
        OnStage.theActorInTheSpotlight().attemptsTo(
            DeleteDoctor.fromApi()
        );
    }

    @Then("el sistema confirma que el medico fue dado de baja exitosamente")
    public void theSystemConfirmsDoctorDeleted() {
        OnStage.theActorInTheSpotlight().should(
            seeThat(ResponseStatusCode.ofTheLastResponse(), equalTo(Constants.STATUS_NO_CONTENT))
        );
    }

    @When("el empleado crea un medico con nombre {string} y la misma cedula")
    public void theEmployeeCreatesWithSameCedula(String nombre) {
        String cedula = OnStage.theActorInTheSpotlight().recall("doctorCedula");
        OnStage.theActorInTheSpotlight().attemptsTo(
            CreateDoctor.withData(nombre, cedula)
        );
    }

    @Then("el sistema responde con codigo {int}")
    public void theSystemRespondsWithCode(int statusCode) {
        OnStage.theActorInTheSpotlight().should(
            seeThat(ResponseStatusCode.ofTheLastResponse(), equalTo(statusCode))
        );
    }

    @When("el empleado crea un medico con nombre {string} cedula {string} consultorio {string} y franja {string}")
    public void theEmployeeCreatesWithOfficeAndShift(String nombre, String cedula, String office, String shift) {
        String uniqueCedula = cedula.length() >= 7
                ? cedula.substring(0, 5) + System.currentTimeMillis() % 100000
                : cedula;
        OnStage.theActorInTheSpotlight().remember("doctorCedula", uniqueCedula);
        OnStage.theActorInTheSpotlight().attemptsTo(
            CreateDoctor.withFullData(nombre, uniqueCedula, office, shift)
        );
    }
}
