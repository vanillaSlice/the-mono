package interactive_coding_challenges.arrays_and_strings;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Mike Lowe
 */
public final class DifferentCharTest {

  @Test(expected = NullPointerException.class)
  public void findDiff_nulls1_throwsNullPointerException() {
    DifferentChar.findDiff(null, "test");
  }

  @Test(expected = NullPointerException.class)
  public void findDiff_nulls2_throwsNullPointerException() {
    DifferentChar.findDiff("test", null);
  }

  @Test
  public void findDiff_present_returnsDifferentChar() {
    assertEquals('e', DifferentChar.findDiff("abcd", "abcde"));
    assertEquals('e', DifferentChar.findDiff("abcde", "abcd"));
    assertEquals('a', DifferentChar.findDiff("abcde", "bcde"));
    assertEquals('a', DifferentChar.findDiff("bcde", "abcde"));
    assertEquals('c', DifferentChar.findDiff("abcdef", "abdef"));
    assertEquals('c', DifferentChar.findDiff("abdef", "abcdef"));
    assertEquals('e', DifferentChar.findDiff("aaabbcdd", "abdbacade"));
    assertEquals('e', DifferentChar.findDiff("abdbacade", "aaabbcdd"));
  }

  @Test
  public void findDiff_notPresent_returnsEmptyChar() {
    assertEquals('\0', DifferentChar.findDiff("", ""));
    assertEquals('\0', DifferentChar.findDiff("a", "a"));
    assertEquals('\0', DifferentChar.findDiff("ab", "ab"));
    assertEquals('\0', DifferentChar.findDiff("abc", "abc"));
    assertEquals('\0', DifferentChar.findDiff("abcd", "abcd"));
  }

}
