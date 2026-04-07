package com.turnos.automation.tasks;

import com.turnos.automation.models.DoctorUpdateRequest;
import com.turnos.automation.util.Constants;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Put;

public class UpdateDoctor implements Task {

    private final String consultorio;
    private final String franjaHoraria;

    public UpdateDoctor(String consultorio, String franjaHoraria) {
        this.consultorio = consultorio;
        this.franjaHoraria = franjaHoraria;
    }

    public static UpdateDoctor withShift(String consultorio, String franjaHoraria) {
        return new UpdateDoctor(consultorio, franjaHoraria);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        String token = actor.recall("authToken");
        String doctorId = actor.recall("doctorId");

        actor.attemptsTo(
            Put.to(Constants.ENDPOINT_DOCTORS + "/" + doctorId)
                .with(request -> request
                    .header(Constants.CONTENT_TYPE_HEADER, Constants.APPLICATION_JSON)
                    .header(Constants.AUTHORIZATION_HEADER, Constants.BEARER_PREFIX + token)
                    .body(new DoctorUpdateRequest(consultorio, franjaHoraria)))
        );
    }
}
