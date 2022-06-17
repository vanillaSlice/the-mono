package interactive_coding_challenges.arrays_and_strings;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author Mike Lowe
 */
public final class CompressStringTest {

  @Test
  public void compress_null_returnsNull() {
    assertNull(CompressString.compress(null));
  }

  @Test
  public void compress_empty_returnsEmpty() {
    assertEquals("", CompressString.compress(""));
  }

  @Test
  public void compress_redundantCompression_returnsOriginalString() {
    assertEquals("a", CompressString.compress("a"));
    assertEquals("aa", CompressString.compress("aa"));
    assertEquals("ab", CompressString.compress("ab"));
    assertEquals("aab", CompressString.compress("aab"));
    assertEquals("abc", CompressString.compress("abc"));
    assertEquals("aabbc", CompressString.compress("aabbc"));
    assertEquals("aabbcc", CompressString.compress("aabbcc"));
  }

  @Test
  public void compress_needsCompressing_returnsCompressed() {
    assertEquals("a3bc2d4e", CompressString.compress("aaabccdddde"));
    assertEquals("ba3c2d4", CompressString.compress("baaaccdddd"));
    assertEquals("a3ba2c2d4", CompressString.compress("aaabaaccdddd"));
  }

}
