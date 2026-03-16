package com.turnos.automation.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

import java.util.List;

import static net.serenitybdd.rest.SerenityRest.lastResponse;

public class TurnosListIsNotEmpty implements Question<Boolean> {

    public static TurnosListIsNotEmpty inTheLastResponse() {
        return new TurnosListIsNotEmpty();
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        List<?> turnos = lastResponse().jsonPath().getList("$");
        return turnos != null && !turnos.isEmpty();
    }
}
