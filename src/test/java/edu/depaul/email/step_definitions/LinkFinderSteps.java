package edu.depaul.email.step_definitions;

import edu.depaul.email.PageParser;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LinkFinderSteps {
    private Document doc;

    @Given("the path to the html file to find links is {string}")
    public void the_path_to_the_html_file_to_find_links_is(String path) {
        doc = Jsoup.parse(path);
    }

    @Then("the collected link is {string}")
    public void the_collected_link_is(String link) {
        PageParser parser = new PageParser();
        Set<String> setParsed = parser.findLinks(doc);
        for (String str : setParsed) {
            link = str;
        }
        assertEquals("http://www.cnn.com", link);
    }
}
