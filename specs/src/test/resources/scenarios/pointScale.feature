Feature: Basic operations on point scale

  Background:
    Given there is a Gamificator server

  Scenario: get a point scale by id
    Given there is a point scale payload with an application id of 1
    And there is an application with the id 1
    And I POST the point scale payload to the /pointScales endpoint
    When I GET the point scale with the id 1
    Then I receive a 200 status code
    And I receive the created point scale

  Scenario: delete a point scale
    When a DELETE is sent to the pointscales endpoint with the id 1
    And I GET the point scale with the id 1
    Then I receive a 404 status code
    And I don't receive a point scale

  Scenario: create a point scale
    Given there is a point scale payload with an application id of 1
    When I POST the point scale payload to the /pointScales endpoint
    Then I receive a 201 status code
    And I receive the created point scale

  Scenario: get all point scale
    Given there is a point scale payload with an application id of 1
    And I POST the point scale payload to the /pointScales endpoint
    When I send a GET to the pointscales endpoint
    Then I receive a 200 status code
    And I receive 2 pointscales with differents id

  Scenario: get all point scale with application id of 1
    When I send a GET to the pointscales endpoint with an application id of 1
    Then I receive a 200 status code
    And I receive 2 pointscales with differents id

  Scenario: get all point scale with application id of 1
    When I send a GET to the pointscales endpoint with an application id of 2
    Then I receive a 404 status code
    And I don't receive a point scale

  Scenario: create a point scale with an application id that does not exists
    Given there is a point scale payload with an application id of 1
    And there isn't an application with an id of 1
    When I POST the point scale payload to the /pointScales endpoint
    Then I receive a 422 status code
