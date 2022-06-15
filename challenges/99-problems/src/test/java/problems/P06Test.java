package problems;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static problems.P06.isPalindrome;

class P06Test {

  @Test
  @DisplayName("should return true when list is a palindrome")
  void shouldReturnTrueWhenListIsPalindrome() {
    assertTrue(isPalindrome(asList("x", "a", "m", "a", "x")));
  }

  @Test
  @DisplayName("should return false when list is not a palindrome")
  void shouldReturnFalseWhenListIsNotPalindrome() {
    assertFalse(isPalindrome(asList(1, 2, 3, 4, 5)));
  }

  @Test
  @DisplayName("should throw NullPointerException when passed null")
  void shouldThrowNullPointerExceptionWhenPassedNull() {
    final Exception exception = assertThrows(NullPointerException.class, () -> isPalindrome(null));
    assertEquals("list cannot be null", exception.getMessage());
  }

}
