package com.turnos.automation.tasks;

import com.turnos.automation.util.ApiConstants;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;

public class ListAllTurnos implements Task {

    public static ListAllTurnos fromApi() {
        return new ListAllTurnos();
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        String token = actor.recall("authToken");

        actor.attemptsTo(
            Get.resource(ApiConstants.ENDPOINT_TURNOS)
                .with(request -> request
                    .header(ApiConstants.AUTHORIZATION_HEADER, ApiConstants.BEARER_PREFIX + token))
        );
    }
}
