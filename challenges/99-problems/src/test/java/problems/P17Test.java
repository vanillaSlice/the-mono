package problems;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static problems.P17.split;

class P17Test {

  @Test
  @DisplayName("should split in two halves")
  void shouldSplitInTwoHalves() {
    final Map<Boolean, List<String>> result = split(asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "k"), 3);
    assertEquals(asList("a", "b", "c"), result.get(true));
    assertEquals(asList("d", "e", "f", "g", "h", "i", "k"), result.get(false));
  }

  @Test
  @DisplayName("should throw NullPointerException when passed null")
  void shouldThrowNullPointerExceptionWhenPassedNull() {
    final Exception exception = assertThrows(NullPointerException.class, () -> split(null, 1));
    assertEquals("list cannot be null", exception.getMessage());
  }

}
