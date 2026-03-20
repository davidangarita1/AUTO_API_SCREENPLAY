package com.turnos.automation.util;

public final class Constants {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String CONTENT_TYPE_HEADER = "Content-Type";
    public static final String APPLICATION_JSON = "application/json";
    public static final String BEARER_PREFIX = "Bearer ";

    public static final String ENDPOINT_SIGN_UP = "/auth/signUp";
    public static final String ENDPOINT_SIGN_IN = "/auth/signIn";
    public static final String ENDPOINT_TURNOS = "/turnos";

    public static final int STATUS_CREATED = 201;
    public static final int STATUS_ACCEPTED = 202;
    public static final int STATUS_OK = 200;

    public static final String TEST_USER_NAME = "API Test User";
    public static final String TEST_USER_PASSWORD = "Test1234!";
    public static final String TEST_EMAIL_PREFIX = "api_test_";
    public static final String TEST_EMAIL_DOMAIN = "@correo.com";
}
