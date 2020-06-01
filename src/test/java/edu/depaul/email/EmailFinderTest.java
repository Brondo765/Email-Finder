package edu.depaul.email;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmailFinderTest {
    private final Collection<String> emails = Arrays.asList("SomeEmail@yahoo.com", "bwegner@depaul.edu", "AnotherEmail@gmail.com");

    @Test
    @DisplayName("Test for no arguments with mock of PrintStream")
    void testEmptyFinder() {
        PrintStream out = mock(PrintStream.class);
        System.setOut(out);
        EmailFinder.main(new String[] {});
        verify(out).println("NO starting URL");
    }

    @Test
    @DisplayName("Test to find whether the output files were created and populated with data")
    void testOutputFilesExist() {
        EmailFinder.main(new String[]{"https://www.cdm.depaul.edu/Pages/default.aspx", String.valueOf(15)});
        try {
            Stream<Path> paths = Files.walk(Paths.get("src\\test\\resources\\"));
            paths.map(Path::toFile)
                    .filter(file -> file.getName().equals("finder-bad-links.txt")
                            || file.getName().equals("finder-email.txt")
                            || file.getName().equals("finder-good-links.txt"))
                            .forEach(file -> assertTrue(file.exists() && file.length() > 0));
            } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    @Test
    @DisplayName("Test for content of collected emails from EmailFinder")
    void testFileContents() {
        EmailFinder.main(new String[] {"src\\test\\resources\\multiTestPage.html", String.valueOf(3)});
        FileReader fileReader;
        BufferedReader reader;
        Collection<String> collected = new ArrayList<>(Collections.emptyList());
        try {
            Stream<Path> paths = Files.walk(Paths.get("src\\test\\resources\\"));
            List<File> list = paths.map(Path::toFile)
                    .filter(file -> file.getName().equals("finder-email.txt")).collect(Collectors.toList());

            for (File file : list) {
                fileReader = new FileReader(file);
                reader = new BufferedReader(fileReader);

                String line;
                while ((line = reader.readLine()) != null) {
                    collected.add(line);
                }
            }
            assertArrayEquals(emails.toArray(), collected.toArray());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}