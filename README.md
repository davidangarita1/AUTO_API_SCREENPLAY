# AUTO_API_SCREENPLAY

Automated API test project for a Turnos (appointment scheduling) system using **Java 17 + Serenity BDD + Cucumber + Gradle** with the **Screenplay pattern**.

## Prerequisites

- Java 17 installed and configured (`JAVA_HOME` set)
- The Turnos API running on `http://localhost:3000`
  - MongoDB running (default port 27017)
  - RabbitMQ running (default port 5672)
  - NestJS backend started

## Start the Infrastructure

If a `docker-compose.yml` is provided in the backend project:

```bash
docker-compose up -d
```

This starts MongoDB, RabbitMQ, and the NestJS backend together.

## Run the Tests

```bash
./gradlew clean test
```

## Generate the Serenity Report

```bash
./gradlew aggregate
```

## View the Report

Open the following file in a browser after running `aggregate`:

```
target/site/serenity/index.html
```

## Project Structure

```
AUTO_API_SCREENPLAY/
├── build.gradle
├── settings.gradle
├── gradle/wrapper/
├── src/
│   └── test/
│       ├── java/
│       │   └── com/
│       │       └── turnos/
│       │           └── automation/
│       │               ├── hooks/           ← Cucumber lifecycle (ActorSetup)
│       │               ├── stepdefinitions/ ← Cucumber Step Definitions
│       │               ├── tasks/           ← Tasks for each API operation
│       │               ├── questions/       ← Questions to verify responses
│       │               ├── models/          ← POJOs/DTOs for request/response bodies
│       │               ├── util/            ← Shared constants (ApiConstants)
│       │               └── runners/         ← Cucumber runner with Serenity
│       └── resources/
│           ├── serenity.conf
│           └── features/
│               └── crud_turnos.feature
```

## Tech Stack

| Component      | Technology                                          |
|----------------|-----------------------------------------------------|
| Language       | Java 17                                             |
| Build tool     | Gradle (Groovy DSL)                                 |
| Framework      | Serenity BDD + serenity-rest-assured + serenity-screenplay-rest |
| Test runner    | Cucumber with JUnit 5 (Platform Suite)              |
| Pattern        | Screenplay (Actors, Tasks, Questions)               |
| HTTP ability   | `CallAnApi` (no WebDriver)                          |

## Configuration

The base URL is read from `serenity.conf` (`restapi.base.url`). To override it at runtime:

```bash
./gradlew clean test -Drestapi.base.url=http://your-host:3000
```