package problems;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static problems.P21.insertAt;

class P21Test {

  @Test
  @DisplayName("should insert element at second position")
  void shouldInsertElementAtSecondPosition() {
    final List<String> input = Stream.of("a", "b", "c", "d").collect(toList());
    final List<String> result = insertAt(input, 2, "alfa");
    assertEquals(5, result.size());
    assertEquals(asList("a", "alfa", "b", "c", "d"), result);
  }

  @Test
  @DisplayName("should insert element at first position")
  void shouldInsertElementAtFirstPosition() {
    final List<String> input = Stream.of("a", "b", "c", "d").collect(toList());
    final List<String> result = insertAt(input, 1, "alfa");
    assertEquals(5, result.size());
    assertEquals(asList("alfa", "a", "b", "c", "d"), result);
  }

  @Test
  @DisplayName("should insert element at end")
  void shouldInsertElementAtEnd() {
    final List<String> input = Stream.of("a", "b", "c", "d").collect(toList());
    final List<String> result = insertAt(input, 5, "alfa");
    assertEquals(5, result.size());
    assertEquals(asList("a", "b", "c", "d", "alfa"), result);
  }

  @Test
  @DisplayName("should throw NullPointerException when passed null")
  void shouldThrowNullPointerExceptionWhenPassedNull() {
    final Exception exception = assertThrows(NullPointerException.class, () -> insertAt(null, 3, "alfa"));
    assertEquals("list cannot be null", exception.getMessage());
  }

}
