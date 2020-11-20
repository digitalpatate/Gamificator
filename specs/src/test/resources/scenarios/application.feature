Feature: Basic operations on application

  Background:
    Given there is a Gamificator server

  Scenario: create an application
    Given I have a application payload
    When I POST the application payload to the /applications endpoint
    Then I receive a 201 status code

  Scenario: get the list of application
    When I send a GET to the /applications endpoint
    Then I receive a 200 status code

  Scenario: create an other application
    Given I have a application payload
    When I POST the application payload to the /applications endpoint
    Then I receive a 201 status code

#
#  Scenario: Delete an application
#    When I send a DELETE to the application endpoint
#    Then I receive a 204 status code