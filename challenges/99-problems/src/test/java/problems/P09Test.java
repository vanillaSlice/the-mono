package problems;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static problems.P09.pack;

class P09Test {

  @Test
  @DisplayName("should return a list with two list elements when a list with two unique elements is passed")
  void shouldReturnAListWithTwoListElementsWhenAListWithTwoUniqueElementsIsPassed() {
    final List<List<String>> packed = pack(asList("a", "b"));
    assertEquals(2, packed.size());
    assertEquals(singletonList("a"), packed.get(0));
    assertEquals(singletonList("b"), packed.get(1));
  }

  @Test
  @DisplayName("should pack consecutive duplicates in their own lists")
  void shouldPackConsecutiveDuplicatesInTheirOwnLists() {
    final List<List<String>> packed =
        pack(asList("a", "a", "a", "a", "b", "c", "c", "a", "a", "d", "e", "e", "e", "e"));
    assertEquals(6, packed.size());
    assertEquals(asList("a", "a", "a", "a"), packed.get(0));
    assertEquals(singletonList("b"), packed.get(1));
    assertEquals(asList("c", "c"), packed.get(2));
    assertEquals(asList("a", "a"), packed.get(3));
    assertEquals(singletonList("d"), packed.get(4));
    assertEquals(asList("e", "e", "e", "e"), packed.get(5));
  }

  @Test
  @DisplayName("should throw NullPointerException when passed null")
  void shouldThrowNullPointerExceptionWhenPassedNull() {
    final Exception exception = assertThrows(NullPointerException.class, () -> pack(null));
    assertEquals("list cannot be null", exception.getMessage());
  }

}
