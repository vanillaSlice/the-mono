package problems;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static problems.P22.range;

class P22Test {

  @Test
  @DisplayName("should create a range between 4 and 9")
  void shouldCreateARangeBetween4And9() {
    final List<Integer> range = range(4, 9);
    assertEquals(6, range.size());
    assertEquals(asList(4, 5, 6, 7, 8, 9), range);
  }

}
