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
    private final String office;
    private final String shift;

    public CreateDoctor(String nombre, String cedula, String office, String shift) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.office = office;
        this.shift = shift;
    }

    public static CreateDoctor withData(String nombre, String cedula) {
        return new CreateDoctor(nombre, cedula, null, null);
    }

    public static CreateDoctor withFullData(String nombre, String cedula, String office, String shift) {
        return new CreateDoctor(nombre, cedula, office, shift);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        String token = actor.recall("authToken");

        DoctorRequest body = (office != null)
                ? new DoctorRequest(nombre, cedula, office, shift)
                : new DoctorRequest(nombre, cedula);

        actor.attemptsTo(
            Post.to(Constants.ENDPOINT_DOCTORS)
                .with(request -> request
                    .header(Constants.CONTENT_TYPE_HEADER, Constants.APPLICATION_JSON)
                    .header(Constants.AUTHORIZATION_HEADER, Constants.BEARER_PREFIX + token)
                    .body(body))
        );

        String doctorId = lastResponse().jsonPath().getString("_id");
        if (doctorId != null) {
            actor.remember("doctorId", doctorId);
        }
    }
}
