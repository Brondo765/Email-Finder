Feature: Parser finds the correct links and emails
  Scenario: Finding one link on an html file
    Given the path to the html file for one link is "src\test\resources\oneTestPage.html"
    Then the collected link is "http://www.cnn.com"

  Scenario: Finding multiple links on an html file
    Given the path to the html file for multiple links is "src\test\resources\multiTestPage.html"
    Then the collected links are
    | https://this-links-out-somewhere.com  |
    | ../relativeLink.html                  |
    | mailto:info@woodridge68.org           |
    | https://kli.org                       |
    | http://cnn.com                        |

  Scenario: Finding one email on an html file
    Given the path to the html file for one email is "src\test\resources\oneTestPage.html"
    Then the collected email is "ThisIsAnEmail@yahoo.com"

  Scenario: Finding multiple emails on an html file
   Given the path to the html file for multiple emails is "src\test\resources\multiTestPage.html"
   Then the collected emails are
   | SomeEmail@yahoo.com    |
   | bwegner@depaul.edu     |
   | AnotherEmail@gmail.com |
