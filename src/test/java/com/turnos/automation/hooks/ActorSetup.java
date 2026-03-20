package com.turnos.automation.hooks;

import com.turnos.automation.util.ApiConstants;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import net.serenitybdd.screenplay.actors.Cast;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.thucydides.model.environment.SystemEnvironmentVariables;

public class ActorSetup {

    @Before
    public void setTheStage() {
        String baseUrl = SystemEnvironmentVariables.createEnvironmentVariables()
                .getProperty(ApiConstants.BASE_URL_KEY, ApiConstants.DEFAULT_BASE_URL);
        OnStage.setTheStage(Cast.whereEveryoneCan(CallAnApi.at(baseUrl)));
        OnStage.theActorCalled("testUser");
    }

    @After
    public void drawTheCurtain() {
        OnStage.drawTheCurtain();
    }
}
