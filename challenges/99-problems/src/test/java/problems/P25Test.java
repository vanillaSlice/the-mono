package problems;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static problems.P25.randomPermutation;

class P25Test {

  @Test
  @DisplayName("should generate random permutation of elements of a list")
  void shouldGenerateRandomPermutationOfElementsOfAList() {
    final List<String> permutation = randomPermutation(asList("a", "b", "c", "d", "e", "f"));
    assertEquals(6, permutation.size());
    assertTrue(permutation.containsAll(asList("a", "b", "c", "d", "e", "f")));
  }

}
