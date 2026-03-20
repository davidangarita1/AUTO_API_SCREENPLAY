package com.turnos.automation.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.screenplay.actors.Cast;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.thucydides.model.environment.SystemEnvironmentVariables;

public class ActorSetup {

    @Before
    public void setTheStage() {
        String baseUrl = EnvironmentSpecificConfiguration
                .from(SystemEnvironmentVariables.currentEnvironmentVariables())
                .getProperty("restapi.base.url");
        OnStage.setTheStage(Cast.whereEveryoneCan(CallAnApi.at(baseUrl)));
        OnStage.theActorCalled("testUser");
    }

    @After
    public void drawTheCurtain() {
        OnStage.drawTheCurtain();
    }
}
