package mwhelan;

import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Mike Lowe
 */
public final class AnagramsTest {

  @Test
  public void get_null_returnsEmptySet() {
    assertTrue(Anagrams.get(null).isEmpty());
  }

  @Test
  public void get_emptyString_returnsSetWithSingleValue() {
    assertions(Anagrams.get(""), 1, "");
  }

  @Test
  public void get_singleCharString_returnsSetWithSingleValue() {
    assertions(Anagrams.get("a"), 1, "a");
    assertions(Anagrams.get("Z"), 1, "Z");
    assertions(Anagrams.get("\n"), 1, "\n");
  }

  @Test
  public void get_twoCharString_returnsSetWithTwoValues() {
    assertions(Anagrams.get("ab"), 2, "ab", "ba");
    assertions(Anagrams.get("CD"), 2, "CD", "DC");
    assertions(Anagrams.get("z\n"), 2, "z\n", "\nz");
  }

  @Test
  public void get_threeCharString_returnsSetWithSixValues() {
    assertions(Anagrams.get("abc"), 6, "abc", "acb", "bac", "bca", "cab", "cba");
    assertions(Anagrams.get("DEF"), 6, "DEF", "DFE", "EDF", "EFD", "FDE", "FED");
    assertions(Anagrams.get("y\nz"), 6, "y\nz", "yz\n", "\nyz", "\nzy", "z\ny", "zy\n");
  }

  @Test
  public void get_withDuplicateChars_doesNotReturnDuplicateAnagrams() {
    assertions(Anagrams.get("AA"), 1, "AA");
    assertions(Anagrams.get("bbc"), 3, "bbc", "bcb", "cbb");
  }

  private void assertions(final Set<String> anagrams, final int expectedSize, final String... expectedAnagrams) {
    assertEquals(expectedSize, anagrams.size());
    for (final String expectedAnagram : expectedAnagrams) {
      assertTrue(anagrams.contains(expectedAnagram));
    }
  }

}
