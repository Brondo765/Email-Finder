package edu.depaul.email;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

// Test that:
// 1. Crawl function is recursive - does it avoid endless loops?
// 2. Does it quit when the target size is met
// 3. Does the report function produce expected results

class PageCrawlerTest {
    private StorageService service;

    @BeforeEach
    void setupObjects() {
        service = new StorageService();
        service.addLocation(StorageService.StorageType.EMAIL, "src\\test\\resources\\crawlerEmail.txt\\");
        service.addLocation(StorageService.StorageType.GOODLINKS, "src\\test\\resources\\crawlerGood.txt\\");
        service.addLocation(StorageService.StorageType.BADLINKS, "src\\test\\resources\\crawlerBad.txt\\");
    }

    @Test
    @DisplayName("Test for base URL without http protocol")
    void testNoHTTP() {
        PageCrawler crawler = new PageCrawler(service, 1);
        crawler.crawl("src\\test\\resources\\oneTestPage.html");
        assertEquals(1, crawler.getEmails().size());
    }

    @Test
    @DisplayName("Test for email size when specified to max size")
    void testEmailMaxSizeSet() {
        PageCrawler crawler = new PageCrawler(service, 50);
        crawler.crawl("https://www.u-46.org/");
        assertEquals(50, crawler.getEmails().size());
    }

    @Test
    @DisplayName("Test for email size when maxEmails is defaulted to 50 in default constructor")
    void testEmailMaxSizeNotSet() {
        PageCrawler crawler = new PageCrawler(service);
        crawler.crawl("https://www.u-46.org/");
        assertEquals(50, crawler.getEmails().size());
    }

    @Test
    @DisplayName("Test for report on emails stored in crawl")
    void testReportEmails() {
        File emails = new File(String.valueOf("src\\test\\resources\\crawlerEmail.txt\\"));
        PageCrawler crawler = new PageCrawler(service, 5);
        crawler.crawl("http://www.columbia.edu/~fdc/sample.html");
        crawler.report();
        assertTrue(emails.exists());
    }

    @Test
    @DisplayName("Test for report on bad links stored in crawl")
    void testReportGoodLinks() {
        File good = new File(String.valueOf("src\\test\\resources\\crawlerGood.txt\\"));
        PageCrawler crawler = new PageCrawler(service, 5);
        crawler.crawl("http://www.columbia.edu/~fdc/sample.html");
        crawler.report();
        assertTrue(good.exists());
    }

    @Test
    @DisplayName("Test for report on bad links stored in crawl")
    void testReportBadLinks() {
        File bad = new File(String.valueOf("src\\test\\resources\\crawlerBad.txt\\"));
        PageCrawler crawler = new PageCrawler(service, 5);
        crawler.crawl("http://www.columbia.edu/~fdc/sample.html");
        crawler.report();
        assertTrue(bad.exists());
    }

    @Test
    @DisplayName("Mock test for StorageService.addLocation when report is called on crawler")
    void testMockStorage() {
        StorageService mockStorage = mock(StorageService.class);
        mockStorage.addLocation(StorageService.StorageType.EMAIL, "src\\test\\resources\\mockStorageEmails.txt");
        mockStorage.addLocation(StorageService.StorageType.GOODLINKS, "src\\test\\resources\\mockStorageGood.txt");
        mockStorage.addLocation(StorageService.StorageType.BADLINKS, "src\\test\\resources\\mockStorageBad.txt");
        PageCrawler crawler = new PageCrawler(mockStorage, 5);
        crawler.crawl("https://www.msichicago.org/");
        crawler.report();
        verify(mockStorage).addLocation(StorageService.StorageType.EMAIL,"src\\test\\resources\\mockStorageEmails.txt");
        verify(mockStorage).addLocation(StorageService.StorageType.GOODLINKS,"src\\test\\resources\\mockStorageGood.txt");
        verify(mockStorage).addLocation(StorageService.StorageType.BADLINKS,"src\\test\\resources\\mockStorageBad.txt");
    }
}