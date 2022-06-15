package problems;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static problems.P07.flatten;

class P07Test {

  @Test
  @DisplayName("should flatten a list of list")
  void shouldFlattenAListOfList() {
    final List<String> flattened = flatten(asList("a", asList("b", asList("c", "d")), "e"), String.class);
    assertEquals(5, flattened.size());
    assertEquals(asList("a", "b", "c", "d", "e"), flattened);
  }

  @Test
  @DisplayName("should flatten deep nested lists")
  void shouldFlattenDeepNestedLists() {
    final List<String> flattened =
        flatten(asList("a", asList("b", asList("c", asList("d", "e", asList("f", "g"))), "h")), String.class);
    assertEquals(8, flattened.size());
    assertEquals(asList("a", "b", "c", "d", "e", "f", "g", "h"), flattened);
  }

  @Test
  @DisplayName("should return empty list when trying to flatten an empty list")
  void shouldReturnEmptyListWhenTryingToFlattenAnEmptyList() {
    final List<String> flattened = flatten(emptyList(), String.class);
    assertTrue(flattened.isEmpty());
  }

  @Test
  @DisplayName("should throw NullPointerException when passed null list")
  void shouldThrowNullPointerExceptionWhenPassedNullList() {
    final Exception exception = assertThrows(NullPointerException.class, () -> flatten(null, String.class));
    assertEquals("list cannot be null", exception.getMessage());
  }

  @Test
  @DisplayName("should throw NullPointerException when passed null element type")
  void shouldThrowNullPointerExceptionWhenPassedNullElementType() {
    final Exception exception = assertThrows(NullPointerException.class, () -> flatten(asList("a", "b"), null));
    assertEquals("elementType cannot be null", exception.getMessage());
  }

}
