package edu.depaul.email;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = { "src\\test\\resources\\parser.feature", "src\\test\\resources\\writer.feature" },
        strict = true,
        stepNotifications = true)
class CucumberTest {
}
