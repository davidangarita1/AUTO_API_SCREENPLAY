Feature: Ciclo completo de gestion de medicos

  Scenario Outline: Un empleado gestiona el ciclo de vida de un medico desde su creacion hasta su baja
    Given que el empleado se autentica en el sistema
    And el sistema confirma que la autenticacion fue exitosa

    When el empleado crea un medico con nombre "<nombre>" y cedula "<cedula>"
    Then el sistema confirma que el medico fue creado exitosamente

    When el empleado consulta la lista de medicos activos
    Then el sistema retorna la lista de medicos con al menos un registro

    When el empleado actualiza el medico con consultorio "<office>" y franja "<shift>"
    Then el sistema confirma que el medico fue actualizado exitosamente

    When el empleado da de baja al medico creado
    Then el sistema confirma que el medico fue dado de baja exitosamente

    Examples:
      | nombre       | cedula     | office | shift       |
      | Juan Garcia  | 1098700001 | 2      | 06:00-14:00 |
