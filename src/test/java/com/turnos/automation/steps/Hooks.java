package com.turnos.automation.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import net.serenitybdd.screenplay.actors.Cast;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.thucydides.model.environment.SystemEnvironmentVariables;

public class Hooks {

    private static final String BASE_URL_KEY = "restapi.base.url";
    private static final String DEFAULT_BASE_URL = "http://localhost:3000";

    @Before
    public void setTheStage() {
        String baseUrl = SystemEnvironmentVariables.createEnvironmentVariables()
                .getProperty(BASE_URL_KEY, DEFAULT_BASE_URL);
        OnStage.setTheStage(Cast.whereEveryoneCan(CallAnApi.at(baseUrl)));
        OnStage.theActorCalled("testUser");
    }

    @After
    public void drawTheCurtain() {
        OnStage.drawTheCurtain();
    }
}
