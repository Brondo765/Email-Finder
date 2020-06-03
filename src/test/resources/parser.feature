Feature: Parser finds the correct links
  Scenario: Finding a set of links on an html file
    Given the path to the html file is "src\\test\\resources\\oneTestPage.html"
    Then the collected link is "http://www.cnn.com"