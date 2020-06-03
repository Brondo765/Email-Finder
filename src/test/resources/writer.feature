Feature: Writer outputs the correct list info of emails
  Scenario: Expected strings match what the list wrote to output
    Given a list of emails
    | someEmail@gmail.com       |
    | bwegner2@mail.depaul.edu  |
    And we add another email for "JSmith@gmail.com"
    Then the expected list output should be
    | someEmail@gmail.com          |
    | bwegner2@mail.depaul.edu     |
    | JSmith@gmail.com             |