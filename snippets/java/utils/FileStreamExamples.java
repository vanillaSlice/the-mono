/**
 * Note to self on how to read data from/write data to file.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Mike Lowe
 */
public final class FileStreamExamples {

  // don't want instances
  private FileStreamExamples() {
  }

  public static void readFromFile() throws IOException {
    // using old java.io classes
    try (final BufferedReader reader = new BufferedReader(new FileReader("file.txt"))) {
      String line;
      while ((line = reader.readLine()) != null) {
        System.out.println(line);
      }
    }

    // using java.nio classes
    try (final BufferedReader reader = Files.newBufferedReader(Paths.get("file.txt"))) {
      String line;
      while ((line = reader.readLine()) != null) {
        System.out.println(line);
      }
    }

    // using convenience method
    final List<String> lines = Files.readAllLines(Paths.get("file.txt"));
    for (final String line : lines) {
      System.out.println(line);
    }
  }

  public static void writeToFile() throws IOException {
    final String data = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    // using old java.io classes
    try (final BufferedWriter writer = new BufferedWriter(new FileWriter("file.txt"))) {
      writer.write(data);
    }

    // using java.nio classes
    try (final BufferedWriter writer = Files.newBufferedWriter(Paths.get("file.txt"))) {
      writer.write(data);
    }

    // using convenience method
    Files.write(Paths.get("file.txt"), data.getBytes());
  }

}
