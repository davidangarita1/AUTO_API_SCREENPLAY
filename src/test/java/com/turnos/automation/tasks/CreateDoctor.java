package com.turnos.automation.tasks;

import com.turnos.automation.models.DoctorRequest;
import com.turnos.automation.util.Constants;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;

import static net.serenitybdd.rest.SerenityRest.lastResponse;

public class CreateDoctor implements Task {

    private final String nombre;
    private final String cedula;

    public CreateDoctor(String nombre, String cedula) {
        this.nombre = nombre;
        this.cedula = cedula;
    }

    public static CreateDoctor withData(String nombre, String cedula) {
        return new CreateDoctor(nombre, cedula);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        String token = actor.recall("authToken");

        actor.attemptsTo(
            Post.to(Constants.ENDPOINT_DOCTORS)
                .with(request -> request
                    .header(Constants.CONTENT_TYPE_HEADER, Constants.APPLICATION_JSON)
                    .header(Constants.AUTHORIZATION_HEADER, Constants.BEARER_PREFIX + token)
                    .body(new DoctorRequest(nombre, cedula)))
        );

        String doctorId = lastResponse().jsonPath().getString("_id");
        if (doctorId != null) {
            actor.remember("doctorId", doctorId);
        }
    }
}
