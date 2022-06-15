package problems;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.AbstractMap.SimpleEntry;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static problems.P13.encode;

class P13Test {

  @Test
  @DisplayName("should encode a list")
  void shouldEncodeAList() {
    final List<SimpleEntry<Integer, String>> encoded =
        encode(asList("a", "a", "a", "a", "b", "c", "c", "a", "a", "d", "e", "e", "e", "e"));
    assertEquals(6, encoded.size());
    assertEquals(new SimpleEntry<>(4, "a"), encoded.get(0));
    assertEquals(new SimpleEntry<>(1, "b"), encoded.get(1));
    assertEquals(new SimpleEntry<>(2, "c"), encoded.get(2));
    assertEquals(new SimpleEntry<>(2, "a"), encoded.get(3));
    assertEquals(new SimpleEntry<>(1, "d"), encoded.get(4));
    assertEquals(new SimpleEntry<>(4, "e"), encoded.get(5));
  }

  @Test
  @DisplayName("should throw NullPointerException when passed null")
  void shouldThrowNullPointerExceptionWhenPassedNull() {
    final Exception exception = assertThrows(NullPointerException.class, () -> encode(null));
    assertEquals("list cannot be null", exception.getMessage());
  }

}
