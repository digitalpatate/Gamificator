Feature: Basic operations on point scale

  Background:
    Given there is a Gamificator server
    And I have a application payload
    When I POST the application payload to the /applications endpoint
    Then I have an API key

  Scenario: get a point scale by id
    Given there is 1 point scale payload
    And I POST the point scale payload to the /pointScales endpoint
    When I GET a previously created point scale with his id
    Then I receive a 200 status code
    And I receive the created point scale

  Scenario: create a point scale
    Given there is 1 point scale payload
    When I POST the point scale payload to the /pointScales endpoint
    Then I receive a 201 status code
    And I receive the created point scale

  Scenario: get all point scale
    Given there is 2 point scale payload
    And I POST the point scale payload to the /pointScales endpoint
    When I send a GET to the point scales endpoint
    Then I receive a 200 status code
    And I receive 2 point scales with different id

  Scenario: update a point scale
    Given there is 1 point scale payload
    And I POST the point scale payload to the /pointScales endpoint
    Then I receive a 201 status code
    And I receive the created point scale
    Given there is 1 point scale payload
    And I PUT the last created point scale payload to the /pointScales endpoint
    Then I receive a 201 status code
    And I receive the updated point scale

