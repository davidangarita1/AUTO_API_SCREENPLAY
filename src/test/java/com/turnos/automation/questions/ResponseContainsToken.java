package com.turnos.automation.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

import static net.serenitybdd.rest.SerenityRest.lastResponse;

public class ResponseContainsToken implements Question<Boolean> {

    public static ResponseContainsToken inTheLastResponse() {
        return new ResponseContainsToken();
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        String token = lastResponse().jsonPath().getString("token");
        return token != null && !token.isEmpty();
    }
}
