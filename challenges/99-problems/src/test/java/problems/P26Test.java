package problems;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static problems.P26.combinations;

class P26Test {

  @Test
  @DisplayName("should find all combinations of size 3 from a list with size 6")
  void shouldFindAllCombinationsOfSize3FromAListWithSize6() {
    final List<String> input = asList("a", "b", "c", "d", "e", "f");
    final List<List<String>> combinations = combinations(input, 3);
    assertEquals(20, combinations.size());
  }

}
