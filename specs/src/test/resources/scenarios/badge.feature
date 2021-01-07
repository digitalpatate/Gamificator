Feature: Basic operations on badges

  Background:
    Given there is a Gamificator server
    And I have a application payload
    When I POST the application payload to the /applications endpoint
    Then I have an API key

  Scenario: get a badge by id
    Given there is 1 badge payload
    And I POST the badge payload to the /badges endpoints
    When I GET the badge previously created
    Then I receive a 200 status code
    And I receive the last created badge

  Scenario: create a badge
    Given there is 1 badge payload
    And I POST the badge payload to the /badges endpoints
    Then I receive a 201 status code
    And I receive the last created badge

  Scenario: create a badge from one application and try to get this badge from another application
    Given there is 1 badge payload
    And I POST the badge payload to the /badges endpoints
    Then I receive a 201 status code
    And I receive the last created badge
    Given there is a Gamificator server
    And I have a application payload
    When I POST the application payload to the /applications endpoint
    Then I have an API key
    When I GET the badge previously created from another application
    Then I receive a 404 status code

  Scenario: get all badges
    Given there is 20 badge payload
    And I POST the badge payload to the /badges endpoints
    When I send a GET to the badge endpoint
    Then I receive a 200 status code
    And I receive 20 badges with different id

  Scenario: update a badge
    Given there is 1 badge payload
    And I POST the badge payload to the /badges endpoints
    Then I receive a 201 status code
    Given there is 1 badge payload
    And I PUT the last created badge payload to the /badges endpoints
    Then I receive a 200 status code
    And I receive the updated badge