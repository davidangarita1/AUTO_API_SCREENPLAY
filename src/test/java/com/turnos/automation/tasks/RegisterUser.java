package com.turnos.automation.tasks;

import com.turnos.automation.models.UserRequest;
import com.turnos.automation.util.Constants;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;

import static net.serenitybdd.rest.SerenityRest.lastResponse;

public class RegisterUser implements Task {

    private final String name;
    private final String email;
    private final String password;

    public RegisterUser(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public static RegisterUser withCredentials(String name, String email, String password) {
        return new RegisterUser(name, email, password);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            Post.to(Constants.ENDPOINT_SIGN_UP)
                .with(request -> request
                    .header(Constants.CONTENT_TYPE_HEADER, Constants.APPLICATION_JSON)
                    .body(new UserRequest(email, password, name, Constants.TEST_USER_ROLE)))
        );

        String token = lastResponse().jsonPath().getString("token");
        if (token != null) {
            actor.remember("authToken", token);
        }
    }
}

