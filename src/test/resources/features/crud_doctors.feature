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

  Scenario: El sistema rechaza la creacion de un medico con cedula duplicada
    Given que el empleado se autentica en el sistema
    And el sistema confirma que la autenticacion fue exitosa
    When el empleado crea un medico con nombre "Original Doc" y cedula "5555500001"
    Then el sistema confirma que el medico fue creado exitosamente
    When el empleado crea un medico con nombre "Duplicado Doc" y la misma cedula
    Then el sistema responde con codigo 409

  Scenario Outline: El sistema rechaza la creacion de un medico con datos invalidos
    Given que el empleado se autentica en el sistema
    And el sistema confirma que la autenticacion fue exitosa
    When el empleado crea un medico con nombre "<nombre>" y cedula "<cedula>"
    Then el sistema responde con codigo 400

    Examples:
      | nombre | cedula     |
      | Ju     | 1098700099 |
      | Valido | 123        |

  Scenario: Un empleado crea un medico con consultorio y franja horaria
    Given que el empleado se autentica en el sistema
    And el sistema confirma que la autenticacion fue exitosa
    When el empleado crea un medico con nombre "Ana Torres" cedula "3334400001" consultorio "3" y franja "14:00-22:00"
    Then el sistema confirma que el medico fue creado exitosamente
