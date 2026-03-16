package com.turnos.automation.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

import static net.serenitybdd.rest.SerenityRest.lastResponse;

public class ResponseStatusCode implements Question<Integer> {

    public static ResponseStatusCode ofTheLastResponse() {
        return new ResponseStatusCode();
    }

    @Override
    public Integer answeredBy(Actor actor) {
        return lastResponse().statusCode();
    }
}
