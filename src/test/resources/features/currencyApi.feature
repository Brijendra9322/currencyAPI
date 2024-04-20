Feature: To Validate USD rates against multiple currency

  @sanity
  Scenario Outline: Verify valid Currency Api
    Given User has '<endpoint>'
    When User hit the '<endpoint>' for currency
    Then Api should return the '<statusCode>' for currency
    And Api should return the '<status>' message
    And Api should fetch the USD price against AED with '<range>'
    And Api should return '<pairs>' currency pairs
    Examples:
      | endpoint       | statusCode | status  | range   | pairs |
      | /v6/latest/USD | 200        | success | 3.6-3.7 | 162   |

  @smoke
  Scenario Outline: verify invalid Currency Api
    Given User has '<endpoint>'
    When User hit the '<endpoint>' for currency
    Then Api should return the '<status>' message
    Examples:
      | endpoint      | status |
      | /v6/latest/US | error  |

  @regression
  Scenario Outline: verify schema with Currency Api response
    Given User has '<endpoint>'
    When User hit the '<endpoint>' for currency
    Then response is converted into Json schema
    And  response and Json schema should be match with '<endpoint>'

    Examples:
      | endpoint       |
      | /v6/latest/USD |