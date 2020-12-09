Feature: Basic operations on badges

  Background:
    Given there is a Gamificator server
    And I have a application payload
    When I POST the application payload to the /applications endpoint
    Then I have an API key

  Scenario: get a badge by id
    Given there is a badge payload
    And I POST the badge payload one to the /badges endpoints
    When I GET the badge with the id 1
    Then I receive a 200 status code
    And I receive the created badge

  Scenario: create a badge
    Given there is a badge payload
    And I POST the badge payload one to the /badges endpoints
    Then I receive a 201 status code
    And I receive the created badge


  Scenario: get all badge
    Given there is a badge payload
    Given there is a second badge payload
    And I POST the badge payload one to the /badges endpoints
    And I POST the badge payload two to the /badges endpoints
    When I send a GET to the badge endpoint
    Then I receive a 200 status code
    And I receive 2 badges with different id


  Scenario: update a badge
    Given there is a badge payload
    And I POST the badge payload one to the /badges endpoints
    Then I receive a 201 status code
    Given there is a second badge payload
    Given I PUT the second badge payload to the /badges endpoints
    Then I receive a 200 status code
    And I receive the updated badge