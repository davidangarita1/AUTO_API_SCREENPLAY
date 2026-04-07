# AUTO_API_SCREENPLAY

Automated API test project for the EPS appointment and doctors management system using **Java 21 + Serenity BDD + Cucumber + Gradle** with the **Screenplay pattern**.

Covers two API modules:

- **Turnos** — full lifecycle of an appointment: register user, create turno, list, query by cedula.
- **Doctors** — full CRUD lifecycle: create doctor, list active doctors, update office/shift, soft delete.

## Prerequisites

- Java 21 installed and configured (`JAVA_HOME` set)
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
│       │               ├── stepdefinitions/ ← Step Definitions per module
│       │               │   ├── TurnosStepDefinitions.java
│       │               │   └── DoctorsStepDefinitions.java
│       │               ├── tasks/           ← Tasks for each API operation
│       │               │   ├── RegisterUser.java
│       │               │   ├── CreateTurno.java
│       │               │   ├── ListAllTurnos.java
│       │               │   ├── GetTurnosByCedula.java
│       │               │   ├── CreateDoctor.java
│       │               │   ├── ListAllDoctors.java
│       │               │   ├── UpdateDoctor.java
│       │               │   └── DeleteDoctor.java
│       │               ├── questions/       ← Questions to verify responses
│       │               │   ├── ResponseStatusCode.java
│       │               │   ├── ResponseContainsToken.java
│       │               │   ├── TurnosListIsNotEmpty.java
│       │               │   ├── DoctorsListIsNotEmpty.java
│       │               │   └── ResponseContainsDoctorId.java
│       │               ├── models/          ← POJOs/DTOs for request bodies
│       │               │   ├── UserRequest.java
│       │               │   ├── TurnoRequest.java
│       │               │   ├── DoctorRequest.java
│       │               │   └── DoctorUpdateRequest.java
│       │               ├── util/            ← Shared constants
│       │               └── runners/         ← Cucumber runner with Serenity
│       └── resources/
│           ├── serenity.conf
│           └── features/
│               ├── crud_turnos.feature
│               └── crud_doctors.feature
```

## Automated Scenarios

### Turnos (`crud_turnos.feature`)

| Step | Action | Assertion |
|---|---|---|
| Given | Register employee account | Status 201 + token present |
| When | Create turno for patient | Status 202 |
| When | List all pending turnos | Status 200 + list not empty |
| When | Query turnos by cedula | Status 200 |

### Doctors (`crud_doctors.feature`)

| Step | Action | Assertion |
|---|---|---|
| Given | Authenticate as employee | Status 201 + token present |
| When | Create doctor (name + documentId) | Status 201 + `_id` present |
| When | List all active doctors | Status 200 + list not empty |
| When | Update doctor office and shift | Status 200 |
| When | Soft delete doctor | Status 204 |

## Tech Stack

| Component      | Technology                                          |
|----------------|-----------------------------------------------------|
| Language       | Java 21                                             |
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