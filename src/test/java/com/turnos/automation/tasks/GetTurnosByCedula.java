package com.turnos.automation.tasks;

import com.turnos.automation.util.Constants;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;

public class GetTurnosByCedula implements Task {

    private final long cedula;

    public GetTurnosByCedula(long cedula) {
        this.cedula = cedula;
    }

    public static GetTurnosByCedula withCedula(long cedula) {
        return new GetTurnosByCedula(cedula);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        String token = actor.recall("authToken");

        actor.attemptsTo(
            Get.resource(Constants.ENDPOINT_TURNOS + "/" + cedula)
                .with(request -> request
                    .header(Constants.AUTHORIZATION_HEADER, Constants.BEARER_PREFIX + token))
        );
    }
}
