package edu.depaul.email.step_definitions;

import edu.depaul.email.ListWriter;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.After;
import org.junit.Before;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class WriterOutputSteps {
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private List<String> emails;

    @Before
    public void setStreams() {
        System.setOut(new PrintStream(out));
    }

    @After
    public void restoreInitialStreams() {
        System.setOut(originalOut);
    }

    @Given("a list of emails")
    public void a_list_of_emails(DataTable table) {
        this.emails = new ArrayList<>();
        this.emails.addAll(table.asList());
    }

    @Given("we add another email for {string}")
    public void we_add_another_email_for(String string) {
        this.emails.add(string);
    }

    @Then("the expected list output should be")
    public void the_expected_list_output_should_be(List<String> strings) throws IOException {
        ListWriter writer = new ListWriter(out);
        writer.writeList(strings);
        assertArrayEquals(this.emails.toArray(), strings.toArray());
        assertEquals("someEmail@gmail.com\n" +
                "bwegner2@mail.depaul.edu\n" +
                "JSmith@gmail.com\n", out.toString());
    }
}
