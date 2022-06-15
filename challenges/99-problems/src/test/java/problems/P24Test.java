package problems;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static problems.P24.randomSelect;

class P24Test {

  @Test
  @DisplayName("should give 6 random numbers from a range starting from 1 to 49")
  void shouldGive6RandomNumbersFromARangeStartingFrom1To49() {
    final List<Integer> randomList = randomSelect(6, 1, 49);
    assertEquals(6, randomList.size());
    randomList.forEach(i -> assertTrue(i >= 1 && i <= 49));
  }

}
