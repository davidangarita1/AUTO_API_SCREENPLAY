package com.turnos.automation.steps;

import com.turnos.automation.questions.ResponseContainsToken;
import com.turnos.automation.questions.ResponseStatusCode;
import com.turnos.automation.questions.TurnosListIsNotEmpty;
import com.turnos.automation.tasks.CreateTurno;
import com.turnos.automation.tasks.GetTurnosByCedula;
import com.turnos.automation.tasks.ListAllTurnos;
import com.turnos.automation.tasks.RegisterUser;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.actors.Cast;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.thucydides.model.environment.SystemEnvironmentVariables;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class TurnosStepDefinitions {

    private static final String BASE_URL_KEY = "restapi.base.url";
    private static final String DEFAULT_BASE_URL = "http://localhost:3000";

    @Before
    public void prepareStage() {
        String baseUrl = SystemEnvironmentVariables.createEnvironmentVariables()
                .getProperty(BASE_URL_KEY, DEFAULT_BASE_URL);
        OnStage.setTheStage(Cast.whereEveryoneCan(CallAnApi.at(baseUrl)));
        OnStage.theActorCalled("testUser");
    }

    @Given("el usuario registra una nueva cuenta con nombre {string} email {string} y contraseña {string}")
    public void theUserRegistersANewAccount(String nombre, String email, String password) {
        OnStage.theActorInTheSpotlight().attemptsTo(
            RegisterUser.withCredentials(nombre, email, password)
        );
    }

    @And("el sistema responde con un codigo 201 y retorna un token de autenticacion")
    public void theSystemResponds201WithToken() {
        OnStage.theActorInTheSpotlight().should(
            seeThat(ResponseStatusCode.ofTheLastResponse(), equalTo(201)),
            seeThat(ResponseContainsToken.inTheLastResponse(), is(true))
        );
    }

    @When("el usuario crea un turno para el paciente {string} con cedula {long} y prioridad {string}")
    public void theUserCreatesATurno(String nombre, long cedula, String priority) {
        OnStage.theActorInTheSpotlight().attemptsTo(
            CreateTurno.forPatient(nombre, cedula, priority)
        );
    }

    @Then("el sistema responde con un codigo 202 indicando que el turno fue aceptado")
    public void theSystemResponds202() {
        OnStage.theActorInTheSpotlight().should(
            seeThat(ResponseStatusCode.ofTheLastResponse(), equalTo(202))
        );
    }

    @When("el usuario consulta la lista completa de turnos")
    public void theUserListsAllTurnos() {
        OnStage.theActorInTheSpotlight().attemptsTo(
            ListAllTurnos.fromApi()
        );
    }

    @Then("el sistema responde con un codigo 200 y una lista que contiene turnos")
    public void theSystemResponds200WithTurnosList() {
        OnStage.theActorInTheSpotlight().should(
            seeThat(ResponseStatusCode.ofTheLastResponse(), equalTo(200)),
            seeThat(TurnosListIsNotEmpty.inTheLastResponse(), is(true))
        );
    }

    @When("el usuario consulta los turnos del paciente con cedula {long}")
    public void theUserGetsTurnosByCedula(long cedula) {
        OnStage.theActorInTheSpotlight().attemptsTo(
            GetTurnosByCedula.withCedula(cedula)
        );
    }

    @Then("el sistema responde con un codigo 200 y los datos del turno del paciente")
    public void theSystemResponds200WithPatientTurno() {
        OnStage.theActorInTheSpotlight().should(
            seeThat(ResponseStatusCode.ofTheLastResponse(), equalTo(200))
        );
    }
}
