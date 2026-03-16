package com.turnos.automation.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

import static net.serenitybdd.rest.SerenityRest.lastResponse;

public class ResponseBody implements Question<String> {

    public static ResponseBody ofTheLastResponse() {
        return new ResponseBody();
    }

    @Override
    public String answeredBy(Actor actor) {
        return lastResponse().body().asString();
    }
}
