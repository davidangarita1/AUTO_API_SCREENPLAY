package com.turnos.automation.hooks;

import com.turnos.automation.util.Constants;
import io.cucumber.java.Before;
import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.thucydides.model.environment.SystemEnvironmentVariables;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DoctorCleanupHook {

    @Before(value = "@limpiar_medicos", order = 1)
    public void cleanupDoctorsBeforeScenario() throws Exception {
        String apiBaseUrl = EnvironmentSpecificConfiguration
                .from(SystemEnvironmentVariables.currentEnvironmentVariables())
                .getProperty("restapi.base.url");

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        String token = getAuthToken(client, apiBaseUrl);
        if (token == null) return;

        String doctorsJson = getAllDoctors(client, apiBaseUrl, token);
        if (doctorsJson == null) return;

        deleteDoctorsFromJson(client, apiBaseUrl, token, doctorsJson);
    }

    private String getAuthToken(HttpClient client, String apiBaseUrl) throws Exception {
        String signUpBody = String.format(
                "{\"email\":\"%s\",\"password\":\"%s\",\"nombre\":\"%s\",\"rol\":\"%s\"}",
                Constants.CLEANUP_EMAIL,
                Constants.TEST_USER_PASSWORD,
                Constants.TEST_USER_NAME,
                Constants.TEST_USER_ROLE
        );

        HttpRequest signUpRequest = HttpRequest.newBuilder()
                .uri(URI.create(apiBaseUrl + Constants.ENDPOINT_SIGN_UP))
                .header("Content-Type", Constants.APPLICATION_JSON)
                .POST(HttpRequest.BodyPublishers.ofString(signUpBody))
                .build();

        HttpResponse<String> response = client.send(signUpRequest, HttpResponse.BodyHandlers.ofString());
        String token = extractToken(response.body());

        if (token != null) return token;

        String signInBody = String.format(
                "{\"email\":\"%s\",\"password\":\"%s\"}",
                Constants.CLEANUP_EMAIL,
                Constants.TEST_USER_PASSWORD
        );

        HttpRequest signInRequest = HttpRequest.newBuilder()
                .uri(URI.create(apiBaseUrl + Constants.ENDPOINT_SIGN_IN))
                .header("Content-Type", Constants.APPLICATION_JSON)
                .POST(HttpRequest.BodyPublishers.ofString(signInBody))
                .build();

        response = client.send(signInRequest, HttpResponse.BodyHandlers.ofString());
        return extractToken(response.body());
    }

    private String extractToken(String body) {
        int tokenIndex = body.indexOf("\"token\":\"");
        if (tokenIndex < 0) return null;

        int start = tokenIndex + 9;
        int end = body.indexOf("\"", start);
        if (end < 0) return null;
        return body.substring(start, end);
    }

    private String getAllDoctors(HttpClient client, String apiBaseUrl, String token) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiBaseUrl + Constants.ENDPOINT_DOCTORS))
                .header("Authorization", "Bearer " + token)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) return null;
        return response.body();
    }

    private void deleteDoctorsFromJson(HttpClient client, String apiBaseUrl, String token, String json) throws Exception {
        int searchFrom = 0;
        while (true) {
            int idIndex = json.indexOf("\"_id\":\"", searchFrom);
            if (idIndex < 0) break;

            int start = idIndex + 7;
            int end = json.indexOf("\"", start);
            String doctorId = json.substring(start, end);

            HttpRequest deleteRequest = HttpRequest.newBuilder()
                    .uri(URI.create(apiBaseUrl + Constants.ENDPOINT_DOCTORS + "/" + doctorId))
                    .header("Authorization", "Bearer " + token)
                    .DELETE()
                    .build();
            client.send(deleteRequest, HttpResponse.BodyHandlers.ofString());

            searchFrom = end;
        }
    }
}
