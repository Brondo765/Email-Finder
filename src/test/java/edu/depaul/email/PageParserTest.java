package edu.depaul.email;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PageParserTest {
    // Made easier for relative path in Windows file system
    private final File file = new File("src\\test\\resources\\oneTestPage.html");
    private final File file2 = new File("src\\test\\resources\\multiTestPage.html");
    private PageParser parser;
    private Set<String> expected = new HashSet<String>();

    @BeforeEach
    void createFileForHTML() {
        parser = new PageParser();
        expected = new HashSet<String>();
    }

    @Test
    @DisplayName("Test for when empty website is parsed")
    void testEmpty() throws IOException {
        Document doc = Jsoup.parse("https://blank.org/");
        Set<String> links = parser.findEmails(doc);
        Set<String> emails = parser.findLinks(doc);
        assert links.isEmpty();
        assert emails.isEmpty();
    }

    @Test
    @DisplayName("Test which should be able to find an email in a simple HTML page")
    void testOneEmail() throws IOException {
        Document doc = Jsoup.parse(file, String.valueOf(StandardCharsets.UTF_8));
        Set<String> emails = parser.findEmails(doc);
        expected.add("ThisIsAnEmail@yahoo.com");
        assertEquals(expected, emails);
    }

    @Test
    @DisplayName("Test which should be able to find a link in a simple HTML page")
    void testOneLink() throws IOException {
        Document doc = Jsoup.parse(file, String.valueOf(StandardCharsets.UTF_8));
        Set<String> links = parser.findLinks(doc);
        expected.add("www.cnn.com/sports-highlights");
        assertEquals(expected, links);
    }

    @Test
    @DisplayName("Test for multiple tags on one HTML page")
    void testMultipleTags() throws IOException {
        Document doc = Jsoup.parse(file2, String.valueOf(StandardCharsets.UTF_8));
        Set<String> links = parser.findLinks(doc);
        expected.add("https://this-links-out-somewhere.com");
        expected.add("../relativeLink.html");
        expected.add("mailto:info@woodridge68.org");
        expected.add("https://kli.org");
        expected.add("https://cnn.com");
        assertEquals(expected, links);
    }

    @Test
    @DisplayName("Test for multiple emails on one HTML page")
    void testMultipleLinks() throws IOException {
        Document doc = Jsoup.parse(file2, String.valueOf(StandardCharsets.UTF_8));
        Set<String> emails = parser.findEmails(doc);
        expected.add("SomeEmail@yahoo.com");
        expected.add("bwegner@depaul.edu");
        expected.add("AnotherEmail@gmail.com");
        assertEquals(expected, emails);
    }
}