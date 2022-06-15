package problems;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static problems.P02.secondLast;

class P02Test {

  @Test
  @DisplayName("should find second last element from a list")
  void shouldFindSecondLastElementFromAList() {
    assertEquals(10, (int) secondLast(asList(1, 2, 11, 4, 5, 8, 10, 6)));
  }

  @Test
  @DisplayName("should throw NullPointerException when passed null")
  void shouldThrowNullPointerExceptionWhenPassedNull() {
    final Exception exception = assertThrows(NullPointerException.class, () -> secondLast(null));
    assertEquals("list cannot be null", exception.getMessage());
  }

  @Test
  @DisplayName("should throw NoSuchElementException when list is empty")
  void shouldThrowNoSuchElementExceptionWhenListEmpty() {
    final Exception exception = assertThrows(NoSuchElementException.class, () -> secondLast(emptyList()));
    assertEquals("list is empty", exception.getMessage());
  }

  @Test
  @DisplayName("should throw NoSuchElementException when list has single element")
  void shouldThrowNoSuchElementExceptionWhenListHasSingleElement() {
    final Exception exception = assertThrows(NoSuchElementException.class, () -> secondLast(singletonList(1)));
    assertEquals("list has single element", exception.getMessage());
  }

}
