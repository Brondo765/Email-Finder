package edu.depaul.email;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;

import static edu.depaul.email.StorageService.StorageType.*;
import static org.junit.jupiter.api.Assertions.*;

class StorageServiceTest {
    private StorageService good;
    private StorageService bad;
    private StorageService email;
    private String locationGood;
    private String locationBad;
    private String locationEmail;
    private final Collection<String> listGoodLinks = Arrays.asList(
            "../correctLink.html", "www.website.com/exists", "www.website.com/landing-page");
    private final Collection<String> listBadLinks = Arrays.asList(
            "../incorrectLink.html", "www.website.com/does-not-exist", "www.website.com/another-missing-page");
    private final Collection<String> listEmails = Arrays.asList(
            "bwegner2@mail.depaul.edu", "someEmail@yahoo.com", "AnotherEmail@gmail.com");

    @BeforeEach
    void createStorageAndLocations() {
        good = new StorageService();
        bad = new StorageService();
        email = new StorageService();
        locationGood = "src\\test\\resources\\storageGoodLinks.txt\\";
        locationBad = "src\\test\\resources\\storageBadLinks.txt\\";
        locationEmail = "src\\test\\resources\\storageEmail.txt\\";
    }

    @Test
    @DisplayName("Test for storage of good links")
    void testGoodStorage() {
        Path path = Paths.get(locationGood);
        File file = new File(String.valueOf(path));
        good.addLocation(GOODLINKS, locationGood);
        good.storeList(GOODLINKS, listGoodLinks);
        assertTrue(file.exists());
    }

    @Test
    @DisplayName("Test for storage of bad links")
    void testBadStorage() {
        Path path = Paths.get(locationBad);
        File file = new File(String.valueOf(path));
        bad.addLocation(StorageService.StorageType.BADLINKS, locationBad);
        bad.storeList(BADLINKS, listBadLinks);
        assertTrue(file.exists());
    }

    @Test
    @DisplayName("Test for storage of emails")
    void testEmailStorage() {
        Path path = Paths.get(locationEmail);
        File file = new File(String.valueOf(path));
        email.addLocation(StorageService.StorageType.EMAIL, locationEmail);
        email.storeList(EMAIL, listEmails);
        assertTrue(file.exists());
    }

    @Test
    @DisplayName("Invalid testing path")
    void testBadPathForStorage() {
        String doesNotExistPath = "src\\doesNotExist\\resources\\error.txt";
        try {
            good.addLocation(GOODLINKS, doesNotExistPath);
            good.storeList(GOODLINKS, listGoodLinks);
        } catch(EmailFinderException e) {
            assertSame("Error while write out GOODLINKS", e.getMessage().intern());
        }
    }
}