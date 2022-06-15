package problems;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static problems.P15.duplicate;

class P15Test {

  @Test
  @DisplayName("should duplicate elements in a list")
  void shouldDuplicateElementsInAList() {
    final List<String> duplicates = duplicate(asList("a", "b", "c"), 3);
    assertEquals(9, duplicates.size());
    assertEquals(asList("a", "a", "a", "b", "b", "b", "c", "c", "c"), duplicates);
  }

  @Test
  @DisplayName("should throw NullPointerException when passed null")
  void shouldThrowNullPointerExceptionWhenPassedNull() {
    final Exception exception = assertThrows(NullPointerException.class, () -> duplicate(null, 1));
    assertEquals("list cannot be null", exception.getMessage());
  }

}
