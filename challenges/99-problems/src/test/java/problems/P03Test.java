package problems;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static problems.P03.kth;

class P03Test {

  @Test
  @DisplayName("should find kth element from a list")
  void shouldFindKthElementFromAList() {
    assertEquals(3, (int) kth(asList(1, 2, 3, 4, 5), 2));
  }

  @Test
  @DisplayName("should throw NullPointerException when passed null")
  void shouldThrowNullPointerExceptionWhenPassedNull() {
    final Exception exception = assertThrows(NullPointerException.class, () -> kth(null, 2));
    assertEquals("list cannot be null", exception.getMessage());
  }

  @Test
  @DisplayName("should throw IndexOutOfBoundsException when passed negative index")
  void shouldThrowIndexOutOfBoundsExceptionWhenPassedNegativeIndex() {
    final Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> kth(asList(1, 2, 3, 4, 5), -1));
    assertEquals("-1", exception.getMessage());
  }

  @Test
  @DisplayName("should throw IndexOutOfBoundsException when passed index greater than size")
  void shouldThrowIndexOutOfBoundsExceptionWhenPassedIndexGreaterThanSize() {
    final Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> kth(asList(1, 2, 3, 4, 5), 5));
    assertEquals("5", exception.getMessage());
  }

}
