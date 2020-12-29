Feature: Basic operations on application

  Background:
    Given there is a Gamificator server

  Scenario: create an application
    Given I have a application payload
    When I POST the application payload to the /applications endpoint
    Then I receive a 201 status code

  Scenario: create an other application
    Given I have a application payload
    When I POST the application payload to the /applications endpoint
    Then I receive a 201 status code

  Scenario: update an application
    Given I have a application payload
    When I POST the application payload to the /applications endpoint
    Then I receive a 201 status code
    Given I have a application payload
    And I PUT the last created application payload to the /applications endpoint
    Then I receive a 200 status code