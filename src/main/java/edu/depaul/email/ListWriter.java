/*
 * Assignment: class project
 * Topic: demonstrate a variety of tests
 * Author: Dan Walker
 */
package edu.depaul.email;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

/**
 * Writes a given list to an output stream.  Each element is written to
 * a new line.
 */
public class ListWriter {

  private final OutputStream output;
  private final byte[] newLine = {'\n'};

  public ListWriter(OutputStream output) {
    this.output = output;
  }

  // Added suppress warning as default charset cannot be specified for platform dependencies
  @edu.umd.cs.findbugs.annotations.SuppressFBWarnings("DM_DEFAULT_ENCODING")
  public void writeList(Collection<String> aList) throws IOException {
    for (String item : aList) {
      output.write(item.getBytes());
      output.write(newLine);
    }
  }
}
