package com.turnos.automation.tasks;

import com.turnos.automation.util.Constants;
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
            Get.resource(Constants.ENDPOINT_TURNOS)
                .with(request -> request
                    .header(Constants.AUTHORIZATION_HEADER, Constants.BEARER_PREFIX + token))
        );
    }
}
