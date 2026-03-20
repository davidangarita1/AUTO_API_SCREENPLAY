package com.turnos.automation.tasks;

import com.turnos.automation.models.TurnoRequest;
import com.turnos.automation.util.Constants;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;

public class CreateTurno implements Task {

    private final long cedula;
    private final String name;
    private final String priority;

    public CreateTurno(long cedula, String name, String priority) {
        this.cedula = cedula;
        this.name = name;
        this.priority = priority;
    }

    public static CreateTurno forPatient(String name, long cedula, String priority) {
        return new CreateTurno(cedula, name, priority);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        TurnoRequest turnoRequest = new TurnoRequest(cedula, name, priority);
        String token = actor.recall("authToken");

        actor.attemptsTo(
            Post.to(Constants.ENDPOINT_TURNOS)
                .with(request -> request
                    .header(Constants.CONTENT_TYPE_HEADER, Constants.APPLICATION_JSON)
                    .header(Constants.AUTHORIZATION_HEADER, Constants.BEARER_PREFIX + token)
                    .body(turnoRequest))
        );
    }
}
