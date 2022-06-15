package problems;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static problems.P08.compress;

class P08Test {

  @Test
  @DisplayName("should remove consecutive duplicates in a list")
  void shouldRemoveConsecutiveDuplicatesInAList() {
    final List<String> compressed = compress(asList("a", "a", "a", "a", "b", "c", "c", "d", "e", "e", "e", "e"));
    assertEquals(5, compressed.size());
    assertEquals(asList("a", "b", "c", "d", "e"), compressed);
  }

  @Test
  @DisplayName("should not remove non consecutive similar elements from a list")
  void shouldNotRemoveNonConsecutiveSimilarElementsFromAList() {
    final List<String> compressed =
        compress(asList("a", "a", "a", "a", "b", "c", "c", "a", "a", "d", "e", "e", "e", "e"));
    assertEquals(6, compressed.size());
    assertEquals(asList("a", "b", "c", "a", "d", "e"), compressed);
  }

  @Test
  @DisplayName("should throw NullPointerException when passed null")
  void shouldThrowNullPointerExceptionWhenPassedNull() {
    final Exception exception = assertThrows(NullPointerException.class, () -> compress(null));
    assertEquals("list cannot be null", exception.getMessage());
  }

}
