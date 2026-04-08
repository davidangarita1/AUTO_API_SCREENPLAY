package com.turnos.automation.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

import java.util.List;

import static net.serenitybdd.rest.SerenityRest.lastResponse;

public class DoctorsListIsNotEmpty implements Question<Boolean> {

    public static DoctorsListIsNotEmpty inTheLastResponse() {
        return new DoctorsListIsNotEmpty();
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        List<?> doctors = lastResponse().jsonPath().getList("data");
        return doctors != null && !doctors.isEmpty();
    }
}
