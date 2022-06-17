package interactive_coding_challenges.arrays_and_strings;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author Mike Lowe
 */
public final class ReverseStringTest {

  @Test
  public void reverse_null_returnsNull() {
    assertNull(ReverseString.reverse(null));
  }

  @Test
  public void reverse_empty_returnsEmpty() {
    assertEquals("", ReverseString.reverse(""));
  }

  @Test
  public void reverse_singleChar_returnsSingleChar() {
    assertEquals("a", ReverseString.reverse("a"));
    assertEquals("b", ReverseString.reverse("b"));
    assertEquals("4", ReverseString.reverse("4"));
  }

  @Test
  public void reverse_reversesString() {
    assertEquals("ba", ReverseString.reverse("ab"));
    assertEquals("123456789", ReverseString.reverse("987654321"));
    assertEquals("racecar", ReverseString.reverse("racecar"));
  }

}
