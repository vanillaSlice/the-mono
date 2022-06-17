/**
 * When sorting file names in various projects, I have found it useful to create comparators
 * to take into account numbers at the end of file names and sort as such. This is a more
 * generic implementation that compares strings and not just files.
 */

import java.math.BigInteger;
import java.util.Comparator;

import static java.util.Objects.requireNonNull;

/**
 * {@code AlphanumericComparator} instances are used to compare {@link String}s
 * where the numeric values of digits are taken into account.
 * <p>
 * When calling {@link String#compareTo(String)} the value of the character is used.
 * So for example, "string10" is evaluated to be less than "string2". If we take into
 * account the numeric values at the end of the strings using {@code AlphanumericComparator}
 * however, we will find that "string10" is evaluated to be greater than "string2".
 * <p>
 * Note that leading zeros will be truncated so "string0000001" will be equivalent to
 * "string1".
 *
 * @author Mike Lowe
 * @see Comparator
 */
public final class AlphanumericComparator implements Comparator<String> {

  @Override
  public int compare(final String s1, final String s2) {
    requireNonNull(s1, "s1 cannot be null");
    requireNonNull(s2, "s2 cannot be null");

    // separate strings into chunks for comparison
    final String[] s1Chunks = getChunks(s1);
    final String[] s2Chunks = getChunks(s2);

    // will stop IndexOutOfBoundsException
    final int comparisonsToMake = Math.min(s1Chunks.length, s2Chunks.length);

    for (int i = 0; i < comparisonsToMake; i++) {
      final String s1Chunk = s1Chunks[i];
      final String s2Chunk = s2Chunks[i];

      // determine the comparison value ensuring that digits are not evaluated as strings
      final int comparisonValue;
      final DigitParseResult s1Result = parseDigits(s1Chunk);
      final DigitParseResult s2Result = parseDigits(s2Chunk);
      if (s1Result.isDigits && s2Result.isDigits) {
        comparisonValue = s1Result.digits.compareTo(s2Result.digits);
      } else {
        comparisonValue = s1Chunk.compareTo(s2Chunk);
      }

      if (comparisonValue != 0) {
        // chunks are different so return comparison value
        return comparisonValue;
      }
    }

    // strings are alphanumerically equivalent up to this point.
    // the string that has more chunks will be determined to be greater
    // e.g. "string1-2" will be greater than "string1-"
    return Integer.compare(s1Chunks.length, s2Chunks.length);
  }

  /*
   * Splits string into chunks of characters and numeric characters.
   * e.g. "1-name.1:0.txt" will be split into ["1", "-name.", "1", ":", "0", ".txt"]
   */
  private static String[] getChunks(final String str) {
    return str.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
  }

  /*
   * Determine if the string contains digits and store this along with the value.
   */
  private static DigitParseResult parseDigits(final String str) {
    try {
      return new DigitParseResult(true, new BigInteger(str));
    } catch (final NumberFormatException e) {
      return new DigitParseResult(false, null);
    }
  }

  private static class DigitParseResult {

    private final boolean isDigits;
    private final BigInteger digits;

    private DigitParseResult(final boolean isDigits, final BigInteger digits) {
      this.isDigits = isDigits;
      this.digits = digits;
    }

  }

}
