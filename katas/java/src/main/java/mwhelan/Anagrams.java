package mwhelan;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * PROBLEM:
 * Write a method to generate all anagrams for an input string.
 *
 * @author Mike Lowe
 */
public final class Anagrams {

  // don't want instances
  private Anagrams() {
  }

  public static Set<String> get(final String input) {
    if (input == null) {
      return Collections.emptySet();
    } else if (input.length() <= 1) {
      return Collections.singleton(input);
    }

    final Set<String> anagrams = new HashSet<>();

    for (int i = 0, length = input.length(); i < length; i++) {
      final char leadingChar = input.charAt(i);
      final String substringWithoutLeadingChar = input.substring(0, i) + input.substring(i + 1, length);
      final Set<String> subAnagrams = get(substringWithoutLeadingChar);
      for (final String subAnagram : subAnagrams) {
        anagrams.add(leadingChar + subAnagram);
      }
    }

    return anagrams;
  }

}
