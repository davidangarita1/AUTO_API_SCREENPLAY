package com.turnos.automation.tasks;

import com.turnos.automation.models.SignInRequest;
import com.turnos.automation.models.UserRequest;
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
        UserRequest userRequest = new UserRequest(email, password, name, "empleado");

        actor.attemptsTo(
            Post.to("/auth/signUp")
                .with(request -> request
                    .header("Content-Type", "application/json")
                    .body(userRequest))
        );

        String token = lastResponse().jsonPath().getString("token");
        if (token == null) {
            actor.attemptsTo(
                Post.to("/auth/signIn")
                    .with(request -> request
                        .header("Content-Type", "application/json")
                        .body(new SignInRequest(email, password)))
            );
            token = lastResponse().jsonPath().getString("token");
        }
        if (token != null) {
            actor.remember("authToken", token);
        }
    }
}
