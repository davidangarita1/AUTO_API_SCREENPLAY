package com.turnos.automation.tasks;

import com.turnos.automation.models.UserRequest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;

import static net.serenitybdd.rest.SerenityRest.lastResponse;

public class RegisterUser implements Task {

    private final String nombre;
    private final String email;
    private final String password;

    public RegisterUser(String nombre, String email, String password) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
    }

    public static RegisterUser withCredentials(String nombre, String email, String password) {
        return new RegisterUser(nombre, email, password);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        UserRequest userRequest = new UserRequest(email, password, nombre, "empleado");

        actor.attemptsTo(
            Post.to("/auth/signUp")
                .with(request -> request
                    .header("Content-Type", "application/json")
                    .body(userRequest))
        );

        String token = lastResponse().jsonPath().getString("token");
        if (token != null) {
            actor.remember("authToken", token);
        }
    }
}
