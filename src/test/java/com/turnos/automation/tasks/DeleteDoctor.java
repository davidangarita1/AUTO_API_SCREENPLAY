package com.turnos.automation.tasks;

import com.turnos.automation.util.Constants;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Delete;

public class DeleteDoctor implements Task {

    public static DeleteDoctor fromApi() {
        return new DeleteDoctor();
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        String token = actor.recall("authToken");
        String doctorId = actor.recall("doctorId");

        actor.attemptsTo(
            Delete.from(Constants.ENDPOINT_DOCTORS + "/" + doctorId)
                .with(request -> request
                    .header(Constants.AUTHORIZATION_HEADER, Constants.BEARER_PREFIX + token))
        );
    }
}
