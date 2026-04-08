package com.turnos.automation.tasks;

import com.turnos.automation.util.Constants;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;

public class ListAllDoctors implements Task {

    public static ListAllDoctors fromApi() {
        return new ListAllDoctors();
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        String token = actor.recall("authToken");

        actor.attemptsTo(
            Get.resource(Constants.ENDPOINT_DOCTORS)
                .with(request -> request
                    .header(Constants.AUTHORIZATION_HEADER, Constants.BEARER_PREFIX + token))
        );
    }
}
