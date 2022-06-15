package problems;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static problems.P01.last;

class P01Test {

  @Test
  @DisplayName("should find last element from a list of strings")
  void shouldFindLastElementFromAListOfStrings() {
    assertEquals("d", last(asList("a", "b", "c", "d")));
  }

  @Test
  @DisplayName("should find last element from a list of integers")
  void shouldFindLastElementFromAListOfIntegers() {
    assertEquals(5, (int) last(asList(1, 2, 3, 4, 5)));
  }

  @Test
  @DisplayName("should throw NullPointerException when passed null")
  void shouldThrowNullPointerExceptionWhenPassedNull() {
    final Exception exception = assertThrows(NullPointerException.class, () -> last(null));
    assertEquals("list cannot be null", exception.getMessage());
  }

  @Test
  @DisplayName("should throw NoSuchElementException when list is empty")
  void shouldThrowNoSuchElementExceptionWhenListEmpty() {
    final Exception exception = assertThrows(NoSuchElementException.class, () -> last(emptyList()));
    assertEquals("list is empty", exception.getMessage());
  }

}
