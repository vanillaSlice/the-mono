package problems;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static problems.P23.randomSelect;

class P23Test {

  @Test
  @DisplayName("should return a list of three random selected elements")
  void shouldReturnAListOfThreeRandomSelectedElements() {
    final List<String> list = asList("a", "b", "c", "d", "e", "f", "g", "h");
    final List<String> result = randomSelect(list, 3);
    assertEquals(3, result.size());
    assertTrue(list.containsAll(asList(result.get(0), result.get(1), result.get(2))));
  }

  @Test
  @DisplayName("should throw NullPointerException when passed null")
  void shouldThrowNullPointerExceptionWhenPassedNull() {
    final Exception exception = assertThrows(NullPointerException.class, () -> randomSelect(null, 3));
    assertEquals("list cannot be null", exception.getMessage());
  }

}
