package edu.depaul.email;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// No reason to mock here
// Verify that:
// 1. Get returns a document when the url is good
// 2. Same for getString
// 3. Try to force some exceptions to verify you get expected types

class PageFetcherTest {
    private PageFetcher fetcher;

    @BeforeEach
    void createFetched() {
        fetcher = new PageFetcher();
    }

    @Test
    @DisplayName("Test fetcher get method on web")
    void testGetWeb() {
        Document doc = fetcher.get(
                "https://www.wired.com/story/confessions-marcus-hutchins-hacker-who-saved-the-internet/" +
                "?utm_source=twitter&utm_medium=social&utm_brand=wired&utm_social-type=owned");
        assertEquals("The Confessions of Marcus Hutchins, the Hacker Who Saved the Internet | WIRED", doc.title());
    }

    @Test
    @DisplayName("Test fetcher get method local file")
    void testGetLocal() {
        Document doc = fetcher.get("src\\test\\resources\\multiTestPage.html");
        assertEquals("Test Page", doc.title());
    }

    @Test
    @DisplayName("Test get invalid URL")
    void testGetInvalid() {
        try {
            fetcher.get("http:/website.com");
        } catch (EmailFinderException e) {
            assertSame("Invalid URL http:/website.com", e.getMessage().intern());
        }
    }

    @Test
    @DisplayName("Test get unable to reach URL")
    void testGetUnableToReach() {
        try {
            fetcher.get("http://www.cnn.org/");
        } catch(EmailFinderException e) {
            assertSame("unable to fetch http://www.cnn.org/", e.getMessage().intern());
        }
    }

    @Test
    @DisplayName("Test getString method on web")
    void testGetStringWeb() throws IOException {
        Document doc = Jsoup.connect("http://www.littlewebhut.com/articles/simple_web_page/").get();
        assertSame(doc.outerHtml().intern(), fetcher.getString(
                "http://www.littlewebhut.com/articles/simple_web_page/").intern());
    }

    @Test
    @DisplayName("Test getString invalid URL")
    void testGetStringInvalid() throws IOException {
        try {
            fetcher.getString("http:/www.littlewebhut.com/articles/simple_web_page/");
        } catch (EmailFinderException e) {
            assertSame("Invalid URL http:/www.littlewebhut.com/articles/simple_web_page/", e.getMessage().intern());
        }
    }

    @Test
    @DisplayName("Test getString unable to reach URL")
    void testGetStringUnableToReach() throws IOException {
        try {
            fetcher.getString("http://www.google.biz/");
        } catch (EmailFinderException e) {
            assertSame("unable to fetch http://www.google.biz/", e.getMessage().intern());
        }
    }
}