package problems;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static problems.P05.reverse;

class P05Test {

  @Test
  @DisplayName("should reverse a list and not change original list")
  void shouldReverseAListAndNotChangeOriginalList() {
    final List<Integer> original = asList(1, 2, 3, 4, 5);
    final List<Integer> expected = asList(5, 4, 3, 2, 1);
    assertEquals(expected, reverse(original));
    assertNotEquals(expected, original);
  }

  @Test
  @DisplayName("should throw NullPointerException when passed null")
  void shouldThrowNullPointerExceptionWhenPassedNull() {
    final Exception exception = assertThrows(NullPointerException.class, () -> reverse(null));
    assertEquals("list cannot be null", exception.getMessage());
  }

}
