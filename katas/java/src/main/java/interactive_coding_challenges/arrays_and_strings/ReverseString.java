package interactive_coding_challenges.arrays_and_strings;

/**
 * PROBLEM:
 * Implement a function to reverse a string.
 *
 * @author Mike Lowe
 */
public final class ReverseString {

  // don't want instances
  private ReverseString() {
  }

  public static String reverse(final String input) {
    if (input == null || input.isEmpty() || input.length() == 1) {
      return input;
    }
    final StringBuilder builder = new StringBuilder();
    for (int i = input.length() - 1; i >= 0; i--) {
      builder.append(input.charAt(i));
    }
    return builder.toString();
  }

}
