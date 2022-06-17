package mwhelan;

import java.util.ArrayList;
import java.util.List;

/**
 * PROBLEM:
 * Create a simple string calculator. The rules are:
 * - the method takes in a string containing 0...n numbers
 * - "" returns 0
 * - "1" returns 1, "2" returns 2
 * - the default delimiters are "," and "\n" so "1,2,3" returns 6 and "1\n2\n3" returns 6
 * - a custom delimiter can be used by passing in "//[delimiter]\n1..." so "//;\n2;2;1" returns 5
 * - numbers bigger than 1000 should be ignored
 * - negative numbers should throw an exception
 *
 * @author Mike Lowe
 */
public final class StringCalculator {

  // don't want instances
  private StringCalculator() {
  }

  public static int add(String numbers) {
    if (numbers.isEmpty()) {
      return 0;
    }

    // get custom delimiter if there is one
    final String delimiter;
    if (numbers.startsWith("//")) {
      final int endOfDelimiter = numbers.indexOf("\n");
      delimiter = numbers.substring(2, endOfDelimiter);
      numbers = numbers.substring(endOfDelimiter + 1);
    } else {
      delimiter = "[,\n]";
    }

    // split string and add numbers together
    int total = 0;
    final String[] arr = numbers.split(delimiter);
    final List<Integer> negatives = new ArrayList<>();
    for (final String str : arr) {
      final int number = Integer.parseInt(str);
      if (number < 0) {
        negatives.add(number);
      } else if (number <= 1000) {
        total += number;
      }
    }

    // don't want to allow negative numbers
    if (!negatives.isEmpty()) {
      throw new IllegalArgumentException("negatives not allowed: " + negatives);
    }

    return total;
  }

}
