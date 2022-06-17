package interactive_coding_challenges.arrays_and_strings;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Mike Lowe
 */
public final class PermutationsTest {

  @Test
  public void isPermutation_null_returnsFalse() {
    assertFalse(Permutations.isPermutation(null, "test"));
    assertFalse(Permutations.isPermutation("test", null));
    assertFalse(Permutations.isPermutation(null, null));
  }

  @Test
  public void isPermutation_emptyString_returnsFalse() {
    assertFalse(Permutations.isPermutation("", "test"));
    assertFalse(Permutations.isPermutation("test", ""));
    assertFalse(Permutations.isPermutation("", ""));
  }

  @Test
  public void isPermutation_doesNotMatchCase_returnsFalse() {
    assertFalse(Permutations.isPermutation("a", "A"));
    assertFalse(Permutations.isPermutation("abc", "ABC"));
    assertFalse(Permutations.isPermutation("a B", "a b"));
  }

  @Test
  public void isPermutation_present_returnsTrue() {
    assertTrue(Permutations.isPermutation("test", "test"));
    assertTrue(Permutations.isPermutation("test", "stet"));
    assertTrue(Permutations.isPermutation("abc", "cba"));
    assertTrue(Permutations.isPermutation("1 2 3", "  123"));
  }

  @Test
  public void isPermutation_notPresent_returnsFalse() {
    assertFalse(Permutations.isPermutation("test", "test "));
    assertFalse(Permutations.isPermutation("abc", "abbc"));
    assertFalse(Permutations.isPermutation("44", "444"));
  }

}
