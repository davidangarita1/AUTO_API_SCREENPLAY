package com.turnos.automation.tasks;

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
            Get.resource("/turnos/" + cedula)
                .with(request -> request
                    .header("Authorization", "Bearer " + token))
        );
    }
}
