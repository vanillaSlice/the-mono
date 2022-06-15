package problems;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.AbstractMap.SimpleEntry;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static problems.P12.decode;

class P12Test {

  @Test
  @DisplayName("should decode encoded list")
  void shouldDecodeEncodedList() {
    final List<Object> encoded = asList(
        new SimpleEntry<>(4, "a"),
        "b",
        new SimpleEntry<>(2, "c"),
        new SimpleEntry<>(2, "a"),
        "d",
        new SimpleEntry<>(4, "e")
    );
    final List<String> decoded = decode(encoded);
    assertEquals(14, decoded.size());
  }

  @Test
  @DisplayName("should throw NullPointerException when passed null")
  void shouldThrowNullPointerExceptionWhenPassedNull() {
    final Exception exception = assertThrows(NullPointerException.class, () -> decode(null));
    assertEquals("list cannot be null", exception.getMessage());
  }

}
