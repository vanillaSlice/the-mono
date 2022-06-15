package problems;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static problems.P18.slice;

class P18Test {

  @Test
  @DisplayName("should give slice of a list")
  void shouldGiveSliceOfAList() {
    final List<String> slice = slice(asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "k"), 3, 7);
    assertEquals(5, slice.size());
    assertEquals(asList("c", "d", "e", "f", "g"), slice);
  }

  @Test
  @DisplayName("should throw NullPointerException when passed null")
  void shouldThrowNullPointerExceptionWhenPassedNull() {
    final Exception exception = assertThrows(NullPointerException.class, () -> slice(null, 3, 7));
    assertEquals("list cannot be null", exception.getMessage());
  }

}
