package com.turnos.automation.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

import static net.serenitybdd.rest.SerenityRest.lastResponse;

public class ResponseContainsDoctorId implements Question<Boolean> {

    public static ResponseContainsDoctorId inTheLastResponse() {
        return new ResponseContainsDoctorId();
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        String doctorId = lastResponse().jsonPath().getString("_id");
        return doctorId != null && !doctorId.isEmpty();
    }
}
