package mwhelan;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Mike Lowe
 */
public final class WordCounterTest {

  @Test(expected = NullPointerException.class)
  public void count_nullInput_throwsNullPointerException() {
    WordCounter.count(null);
  }

  @Test(expected = NullPointerException.class)
  public void count_nullDelimiter_throwsNullPointerException() {
    WordCounter.count("hello", null);
  }

  @Test
  public void count_emptyString_returnsMapWithOneEntry() {
    // execution
    final Map<String, Integer> result = WordCounter.count("");

    // verification
    assertEquals(1, result.size());
    wordAssertions(result, "", 1);
  }

  @Test
  public void count_singleWord_returnsMapWithOneEntry() {
    // execution
    final Map<String, Integer> result = WordCounter.count("hello");

    // verification
    assertEquals(1, result.size());
    wordAssertions(result, "hello", 1);
  }

  @Test
  public void count_twoWords_returnsMapWithTwoEntries() {
    // execution
    final Map<String, Integer> result = WordCounter.count("hello,world");

    // verification
    assertEquals(2, result.size());
    wordAssertions(result, "hello", 1);
    wordAssertions(result, "world", 1);
  }

  @Test
  public void count_withRepeatingStrings() {
    // execution
    final Map<String, Integer> result = WordCounter.count("hello,world,hello");

    // verification
    assertEquals(2, result.size());
    wordAssertions(result, "hello", 2);
    wordAssertions(result, "world", 1);
  }

  @Test
  public void count_customDelimiter() {
    // execution
    final Map<String, Integer> result = WordCounter.count("hello;world;hello", ";");

    // verification
    assertEquals(2, result.size());
    wordAssertions(result, "hello", 2);
    wordAssertions(result, "world", 1);
  }

  private void wordAssertions(final Map<String, Integer> map, final String word, final int expectedOccurrences) {
    assertTrue(map.containsKey(word));
    assertEquals(expectedOccurrences, (int) map.get(word));
  }

}
