package problems;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static problems.P16.dropEveryNth;

class P16Test {

  @Test
  @DisplayName("should drop every nth element")
  void shouldDropEveryNthElement() {
    final List<String> result = dropEveryNth(asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k"), 3);
    assertEquals(8, result.size());
    assertEquals(asList("a", "b", "d", "e", "g", "h", "j", "k"), result);
  }

  @Test
  @DisplayName("should return same list when n is greater than size")
  void shouldReturnSameListWhenNIsGreaterThanListSize() {
    final List<String> result = dropEveryNth(asList("a", "b"), 3);
    assertEquals(2, result.size());
    assertEquals(asList("a", "b"), result);
  }

  @Test
  @DisplayName("should return same list when n is zero")
  void shouldReturnSameListWhenNIsZero() {
    final List<String> result = dropEveryNth(asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k"), 0);
    assertEquals(11, result.size());
    assertEquals(asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k"), result);
  }

  @Test
  @DisplayName("should return same list when n is less than zero")
  void shouldReturnSameListWhenNIsLessThanZero() {
    final List<String> result = dropEveryNth(asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k"), -1);
    assertEquals(11, result.size());
    assertEquals(asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k"), result);
  }

  @Test
  @DisplayName("should throw NullPointerException when passed null")
  void shouldThrowNullPointerExceptionWhenPassedNull() {
    final Exception exception = assertThrows(NullPointerException.class, () -> dropEveryNth(null, 1));
    assertEquals("list cannot be null", exception.getMessage());
  }

}
