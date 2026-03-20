Feature: Ciclo completo de gestión de turnos

  Scenario: Un empleado gestiona un turno de paciente desde su registro hasta su consulta
    Given que el empleado tiene una cuenta activa en el sistema
    And el sistema confirma que la cuenta fue creada exitosamente
    When el empleado registra un nuevo turno para el paciente "Carlos Ramirez" con cedula 1098765432 y prioridad "media"
    Then el sistema confirma que el turno fue recibido y puesto en cola
    When el empleado consulta todos los turnos pendientes
    Then el sistema retorna la lista de turnos con al menos un registro
    When el empleado busca los turnos registrados a nombre de la cedula 1098765432
    Then el sistema retorna la informacion del turno del paciente
