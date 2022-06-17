package interactive_coding_challenges.arrays_and_strings;

import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.requireNonNull;

/**
 * PROBLEM:
 * Find the single different char between two strings.
 *
 * @author Mike Lowe
 */
public final class DifferentChar {

  // don't want instances
  private DifferentChar() {
  }

  public static char findDiff(final String s1, final String s2) {
    requireNonNull(s1, "s1 cannot be null");
    requireNonNull(s2, "s2 cannot be null");

    final String longest;
    final String shortest;
    if (s1.length() > s2.length()) {
      longest = s1;
      shortest = s2;
    } else {
      longest = s2;
      shortest = s1;
    }

    final Set<Character> seen = new HashSet<>();
    for (int i = 0; i < shortest.length(); i++) {
      seen.add(shortest.charAt(i));
    }
    for (int i = 0; i < longest.length(); i++) {
      if (!seen.contains(longest.charAt(i))) {
        return longest.charAt(i);
      }
    }

    return '\0';
  }

}
