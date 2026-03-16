package com.turnos.automation.tasks;

import com.turnos.automation.models.TurnoRequest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;

public class CreateTurno implements Task {

    private final long cedula;
    private final String nombre;
    private final String priority;

    public CreateTurno(long cedula, String nombre, String priority) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.priority = priority;
    }

    public static CreateTurno forPatient(String nombre, long cedula, String priority) {
        return new CreateTurno(cedula, nombre, priority);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        TurnoRequest turnoRequest = new TurnoRequest(cedula, nombre, priority);
        String token = actor.recall("authToken");

        actor.attemptsTo(
            Post.to("/turnos")
                .with(request -> request
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + token)
                    .body(turnoRequest))
        );
    }
}
