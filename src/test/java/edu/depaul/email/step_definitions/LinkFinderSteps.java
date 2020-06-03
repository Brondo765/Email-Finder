package edu.depaul.email.step_definitions;

import edu.depaul.email.PageParser;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class LinkFinderSteps {
    private Document doc;

    @Given("the path to the html file for one link is {string}")
    public void the_path_to_the_html_file_for_one_link_is(String path) throws IOException {
        File file = new File(path);
        doc = Jsoup.parse(file, String.valueOf(StandardCharsets.UTF_8));
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

    @Given("the path to the html file for multiple links is {string}")
    public void the_path_to_the_html_file_for_multiple_links_is(String path) throws IOException {
        File file = new File(path);
        doc = Jsoup.parse(file, String.valueOf(StandardCharsets.UTF_8));
    }

    @Then("the collected links are")
    public void the_collected_links_are(DataTable dataTable) {
        PageParser parser = new PageParser();
        List<String> links = dataTable.asList();
        Set<String> setParsed = parser.findLinks(doc);
        assertTrue(setParsed.containsAll(links));
    }

    @Given("the path to the html file for one email is {string}")
    public void the_path_to_the_html_file_for_one_email_is(String path) throws IOException {
        File file = new File(path);
        doc = Jsoup.parse(file, String.valueOf(StandardCharsets.UTF_8));
    }

    @Then("the collected email is {string}")
    public void the_collected_email_is(String email) {
        PageParser parser = new PageParser();
        Set<String> setParsed = parser.findEmails(doc);
        for (String str : setParsed) {
            email = str;
        }
        assertEquals("ThisIsAnEmail@yahoo.com", email);
    }

    @Given("the path to the html file for multiple emails is {string}")
    public void the_path_to_the_html_file_for_multiple_emails_is(String path) throws IOException {
        File file = new File(path);
        doc = Jsoup.parse(file, String.valueOf(StandardCharsets.UTF_8));
    }


    @Then("the collected emails are")
    public void the_collected_emails_are(io.cucumber.datatable.DataTable dataTable) {
        PageParser parser = new PageParser();
        List<String> emails = dataTable.asList();
        Set<String> setParsed = parser.findEmails(doc);
        assertTrue(setParsed.containsAll(emails));
    }
}
