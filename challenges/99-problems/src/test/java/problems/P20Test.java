package problems;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static problems.P20.removeAt;

class P20Test {

  @Test
  @DisplayName("should remove kth element from list")
  void shouldRemoveKthElementFromList() {
    final Object[] result = removeAt(asList("a", "b", "c", "d"), 2);
    assertEquals(asList("a", "c", "d"), result[0]);
    assertEquals("b", result[1]);
  }

  @Test
  @DisplayName("should throw NullPointerException when passed null")
  void shouldThrowNullPointerExceptionWhenPassedNull() {
    final Exception exception = assertThrows(NullPointerException.class, () -> removeAt(null, 3));
    assertEquals("list cannot be null", exception.getMessage());
  }

}
