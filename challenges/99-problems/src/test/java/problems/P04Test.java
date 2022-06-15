package problems;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static problems.P04.length;

class P04Test {

  @Test
  @DisplayName("length of empty list should be 0")
  void lengthOfEmptyListShouldBe0() {
    assertEquals(0, length(emptyList()));
  }

  @Test
  @DisplayName("should find length of non empty list")
  void shouldFindLengthOfNonEmptyList() {
    assertEquals(5, length(asList(1, 2, "hello", "world", 5)));
  }

  @Test
  @DisplayName("should throw NullPointerException when passed null")
  void shouldThrowNullPointerExceptionWhenPassedNull() {
    final Exception exception = assertThrows(NullPointerException.class, () -> length(null));
    assertEquals("list cannot be null", exception.getMessage());
  }

}
