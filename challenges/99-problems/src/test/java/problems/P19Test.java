package problems;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static problems.P19.rotate;

class P19Test {

  @Test
  @DisplayName("should rotate a list by three elements when n is 3")
  void shouldRotateAListByThreeElementsWhenNIs3() {
    final List<String> rotated = rotate(asList("a", "b", "c", "d", "e", "f", "g", "h"), 3);
    assertEquals(rotated, asList("d", "e", "f", "g", "h", "a", "b", "c"));
  }

  @Test
  @DisplayName("should return same list when n is 0")
  void shouldReturnSameListWhenNIs0() {
    final List<String> rotated = rotate(asList("a", "b", "c", "d", "e", "f", "g", "h"), 0);
    assertEquals(rotated, asList("a", "b", "c", "d", "e", "f", "g", "h"));
  }

  @Test
  @DisplayName("should rotate when n is negative")
  void shouldRotateWhenNIsNegative() {
    final List<String> rotated = rotate(asList("a", "b", "c", "d", "e", "f", "g", "h"), -2);
    assertEquals(rotated, asList("g", "h", "a", "b", "c", "d", "e", "f"));
  }

  @Test
  @DisplayName("should throw NullPointerException when passed null")
  void shouldThrowNullPointerExceptionWhenPassedNull() {
    final Exception exception = assertThrows(NullPointerException.class, () -> rotate(null, 3));
    assertEquals("list cannot be null", exception.getMessage());
  }

}
