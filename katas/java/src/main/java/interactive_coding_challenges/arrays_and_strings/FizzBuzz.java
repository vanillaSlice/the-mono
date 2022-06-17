package interactive_coding_challenges.arrays_and_strings;

/**
 * PROBLEM:
 * Implement Fizz Buzz.
 *
 * @author Mike Lowe
 */
public final class FizzBuzz {

  // don't want instances
  private FizzBuzz() {
  }

  public static String[] fizzBuzz(final int n) {
    if (n < 1) {
      throw new IllegalArgumentException("n cannot be less than 1 but was " + n);
    }
    final String[] output = new String[15];
    for (int i = 1; i <= n; i++) {
      output[i - 1] = getOutputString(i);
    }
    return output;
  }

  private static String getOutputString(final int n) {
    final StringBuilder message = new StringBuilder();

    if (n % 3 == 0) {
      message.append("Fizz");
    }
    if (n % 5 == 0) {
      message.append("Buzz");
    }
    if (message.length() == 0) {
      message.append(Integer.toString(n));
    }

    return message.toString();
  }

}
