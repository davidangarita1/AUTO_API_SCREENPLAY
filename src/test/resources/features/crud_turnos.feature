Feature: Complete cycle of operations on the turnos API

  Scenario: The user registers an account creates a turno and queries the turnos
    Given el usuario registra una nueva cuenta con nombre "API Test User" email "api_test_user@correo.com" y contraseña "Test1234!"
    And el sistema responde con un codigo 201 y retorna un token de autenticacion
    When el usuario crea un turno para el paciente "Carlos Ramirez" con cedula 1098765432 y prioridad "media"
    Then el sistema responde con un codigo 202 indicando que el turno fue aceptado
    When el usuario consulta la lista completa de turnos
    Then el sistema responde con un codigo 200 y una lista que contiene turnos
    When el usuario consulta los turnos del paciente con cedula 1098765432
    Then el sistema responde con un codigo 200 y los datos del turno del paciente
