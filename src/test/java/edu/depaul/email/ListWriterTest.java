package edu.depaul.email;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

// Make list writer test and use mock!
// Pass in object and verify that it contains expected results after calling writeList

class ListWriterTest {
    private final File file = new File("src\\test\\resources\\writerOutput.txt");
    private Collection<String> list;

    @BeforeEach
    void setup() {
        list = Arrays.asList("bwegner@mail.depaul.edu",
                "AnotherEmail@gmail.com",
                "HereBeAnother@yahoo.com",
                "email@gmail.com",
                "referenceMail@mail.org");
    }

    @Test
    @DisplayName("Basic test for writer to see if file is created when written to")
    void testBasicUsage() throws IOException {
        BufferedReader reader = Files.newBufferedReader(Paths.get(String.valueOf(file)));
        FileOutputStream out = new FileOutputStream(file);
        ListWriter writer = new ListWriter(out);
        writer.writeList(list);

        String line;
        int count = 0;
        while ((line = reader.readLine()) != null) {
            if (list.contains(line)) {
                count++;
            }
        }
        assertEquals(5, count);
    }

    @Test
    @DisplayName("Test for mocked ListWriter verifies when writeList is called")
    void testMockWriter() throws IOException {
        ListWriter mockWriter = mock(ListWriter.class);
        mockWriter.writeList(list);
        verify(mockWriter).writeList(list);
    }
}