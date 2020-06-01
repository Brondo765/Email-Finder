package edu.depaul.email;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

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

    @ParameterizedTest
    @MethodSource("provideFileInput")
    @DisplayName("Test for get method with multiple local files")
    void testGetMultipleLocal(String path) throws IOException {
        File input = new File(path);
        Document doc = Jsoup.parse(input, String.valueOf(StandardCharsets.UTF_8));
        assertEquals(String.valueOf(doc).intern(), String.valueOf(fetcher.get(path)).intern());
    }

    private static Stream<Arguments> provideFileInput() {
        return Stream.of(
                Arguments.of("src\\test\\resources\\multiTestPage.html"),
                Arguments.of("src\\test\\resources\\oneTestPage.html"));
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

    @ParameterizedTest
    @MethodSource("provideWebsiteInput")
    @DisplayName("Test for multiple connections to websites")
    void testParameterizedGetStringWeb(String website) throws IOException {
        Document doc = Jsoup.connect(website).get();
        assertEquals(doc.outerHtml().intern(), fetcher.getString(website).intern());
    }

    private static Stream<Arguments> provideWebsiteInput() {
        return Stream.of(
                Arguments.of("http://www.cnn.com"),
                Arguments.of("https://web.ics.purdue.edu/~gchopra/class/public/pages/webdesign/05_simple.html"),
                Arguments.of("http://www.cdm.depaul.edu"),
                Arguments.of("http://www.basicwebsiteexample.com/"),
                Arguments.of("https://www.york.ac.uk/teaching/cws/wws/webpage1.html"));
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