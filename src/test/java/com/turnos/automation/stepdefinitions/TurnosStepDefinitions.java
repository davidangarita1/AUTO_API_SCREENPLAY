package com.turnos.automation.stepdefinitions;

import com.turnos.automation.questions.ResponseContainsToken;
import com.turnos.automation.questions.ResponseStatusCode;
import com.turnos.automation.questions.TurnosListIsNotEmpty;
import com.turnos.automation.tasks.CreateTurno;
import com.turnos.automation.tasks.GetTurnosByCedula;
import com.turnos.automation.tasks.ListAllTurnos;
import com.turnos.automation.tasks.RegisterUser;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import com.turnos.automation.util.Constants;
import net.serenitybdd.screenplay.actors.OnStage;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class TurnosStepDefinitions {

    @Given("que el empleado tiene una cuenta activa en el sistema")
    public void theEmployeeHasAnActiveAccount() {
        String uniqueEmail = Constants.TEST_EMAIL_PREFIX + System.currentTimeMillis() + Constants.TEST_EMAIL_DOMAIN;
        OnStage.theActorInTheSpotlight().attemptsTo(
            RegisterUser.withCredentials(Constants.TEST_USER_NAME, uniqueEmail, Constants.TEST_USER_PASSWORD)
        );
    }

    @And("el sistema confirma que la cuenta fue creada exitosamente")
    public void theSystemConfirmsAccountCreated() {
        OnStage.theActorInTheSpotlight().should(
            seeThat(ResponseStatusCode.ofTheLastResponse(), equalTo(Constants.STATUS_CREATED)),
            seeThat(ResponseContainsToken.inTheLastResponse(), is(true))
        );
    }

    @When("el empleado registra un nuevo turno para el paciente {string} con {long} y {string}")
    public void theEmployeeRegistersATurno(String name, long cedula, String priority) {
        OnStage.theActorInTheSpotlight().attemptsTo(
            CreateTurno.forPatient(name, cedula, priority)
        );
    }

    @Then("el sistema confirma que el turno fue recibido y puesto en cola")
    public void theSystemConfirmsTurnoQueued() {
        OnStage.theActorInTheSpotlight().should(
            seeThat(ResponseStatusCode.ofTheLastResponse(), equalTo(Constants.STATUS_ACCEPTED))
        );
    }

    @When("el empleado consulta todos los turnos pendientes")
    public void theEmployeeListsAllTurnos() {
        OnStage.theActorInTheSpotlight().attemptsTo(
            ListAllTurnos.fromApi()
        );
    }

    @Then("el sistema retorna la lista de turnos con al menos un registro")
    public void theSystemReturnsTurnosList() {
        OnStage.theActorInTheSpotlight().should(
            seeThat(ResponseStatusCode.ofTheLastResponse(), equalTo(Constants.STATUS_OK)),
            seeThat(TurnosListIsNotEmpty.inTheLastResponse(), is(true))
        );
    }

    @When("el empleado busca los turnos registrados a nombre de la {long}")
    public void theEmployeeGetsTurnosByCedula(long cedula) {
        OnStage.theActorInTheSpotlight().attemptsTo(
            GetTurnosByCedula.withCedula(cedula)
        );
    }

    @Then("el sistema retorna la informacion del turno del paciente")
    public void theSystemReturnsPatientTurno() {
        OnStage.theActorInTheSpotlight().should(
            seeThat(ResponseStatusCode.ofTheLastResponse(), equalTo(Constants.STATUS_OK))
        );
    }
}
