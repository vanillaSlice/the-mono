package interactive_coding_challenges.arrays_and_strings;

import java.util.HashMap;
import java.util.Map;

/**
 * PROBLEM:
 * Determine if a string is a permutation of another string.
 *
 * @author Mike Lowe
 */
public final class Permutations {

  // don't want instances
  private Permutations() {
  }

  public static boolean isPermutation(final String s1, final String s2) {
    if (s1 == null || s2 == null || s1.isEmpty() || s2.isEmpty() || s1.length() != s2.length()) {
      return false;
    } else if (s1.equals(s2)) {
      return true;
    }

    final Map<Character, Integer> s1CharCount = countChars(s1);
    final Map<Character, Integer> s2CharCount = countChars(s2);
    for (final char c : s1CharCount.keySet()) {
      if (s1CharCount.get(c) != s2CharCount.get(c)) {
        return false;
      }
    }
    return true;
  }

  private static Map<Character, Integer> countChars(final String s) {
    final Map<Character, Integer> charCount = new HashMap<>();

    for (int i = 0; i < s.length(); i++) {
      final char c = s.charAt(i);
      if (charCount.containsKey(c)) {
        charCount.put(c, charCount.get(c) + 1);
      } else {
        charCount.put(c, 1);
      }
    }

    return charCount;
  }

}
