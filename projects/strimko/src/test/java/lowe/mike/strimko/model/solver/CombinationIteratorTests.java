package lowe.mike.strimko.model.solver;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * {@link CombinationIterator} tests.
 *
 * @author Mike Lowe
 */
public final class CombinationIteratorTests {

  private static final int N = 2;
  private static final int SIZE = 4;
  private CombinationIterator iterator;

  @BeforeEach
  public void setup() {
    iterator = new CombinationIterator(N, SIZE);
  }

  @Test
  public void test_hasNext() {
    // setup
    int expectedCombinations = 6;

    // execution
    int numberOfCombinations = 0;
    while (iterator.hasNext()) {
      iterator.next();
      numberOfCombinations++;
    }

    // verification
    assertEquals(expectedCombinations, numberOfCombinations);
  }

  @Test
  public void test_get() {
    // setup
    List<Collection<Integer>> expectedCombinations = new ArrayList<>();
    expectedCombinations.add(asList(1, 2));
    expectedCombinations.add(asList(1, 3));
    expectedCombinations.add(asList(2, 3));
    expectedCombinations.add(asList(1, 4));
    expectedCombinations.add(asList(2, 4));
    expectedCombinations.add(asList(3, 4));

    // execution and verification
    int index = 0;
    while (iterator.hasNext()) {
      Collection<Integer> expectedCombination = expectedCombinations.get(index);
      Collection<Integer> actualCombination = iterator.next();
      assertTrue(actualCombination.containsAll(expectedCombination));
      index++;
    }
  }

}
