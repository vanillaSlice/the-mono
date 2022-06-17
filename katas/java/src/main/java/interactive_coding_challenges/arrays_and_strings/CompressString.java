package interactive_coding_challenges.arrays_and_strings;

/**
 * PROBLEM:
 * Compress a string such that 'AAABCCDDDD' becomes 'A3BC2D4'.
 * Only compress the string if it saves space.
 *
 * @author Mike Lowe
 */
public final class CompressString {

  // don't want instances
  private CompressString() {
  }

  public static String compress(final String input) {
    // don't need to bother compressing here
    if (input == null || input.isEmpty() || input.length() == 1) {
      return input;
    }

    final StringBuilder stringBuilder = new StringBuilder();
    char previousChar = input.charAt(0);
    int previousCharCount = 1;
    stringBuilder.append(previousChar);

    for (int i = 1; i < input.length(); i++) {
      final char currentChar = input.charAt(i);
      if (previousChar == currentChar) {
        previousCharCount++;
      } else {
        appendPreviousCharCount(stringBuilder, previousCharCount);
        stringBuilder.append(currentChar);
        previousChar = currentChar;
        previousCharCount = 1;
      }
    }
    // takes into account the final character
    appendPreviousCharCount(stringBuilder, previousCharCount);

    // only return the compressed string if it saves space
    if (stringBuilder.length() >= input.length()) {
      return input;
    } else {
      return stringBuilder.toString();
    }
  }

  private static void appendPreviousCharCount(final StringBuilder stringBuilder, final int previousCharCount) {
    if (previousCharCount != 1) {
      stringBuilder.append(previousCharCount);
    }
  }

}
