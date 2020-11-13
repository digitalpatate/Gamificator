Feature: Basic operations on point scale

  Background:
    Given there is a Gamificator server

  Scenario: create a point scale
    Given there is a point scale payload with an application id of 1
    And there is an application with the id 1
    When I POST the point scale payload to the /pointScales endpoint
    Then I receive a 201 status code
    And I receive the created point scale
