package interactive_coding_challenges.arrays_and_strings;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Mike Lowe
 */
public final class UniqueCharsTest {

  @Test
  public void hasUniqueChars_null_returnsFalse() {
    assertFalse(UniqueChars.hasUniqueChars(null));
  }

  @Test
  public void hasUniqueChars_empty_returnsTrue() {
    assertTrue(UniqueChars.hasUniqueChars(""));
  }

  @Test
  public void hasUniqueChars_present_returnsTrue() {
    assertTrue(UniqueChars.hasUniqueChars("a"));
    assertTrue(UniqueChars.hasUniqueChars("ab"));
    assertTrue(UniqueChars.hasUniqueChars("abc"));
    assertTrue(UniqueChars.hasUniqueChars("abcdefABCDEF12345'\n \\"));
  }

  @Test
  public void hasUniqueChars_notPresent_returnsFalse() {
    assertFalse(UniqueChars.hasUniqueChars("  "));
    assertFalse(UniqueChars.hasUniqueChars("aa"));
    assertFalse(UniqueChars.hasUniqueChars("aBB"));
    assertFalse(UniqueChars.hasUniqueChars("abcdefabcdef"));
  }

}
