package lowe.mike.strimko.model.solver;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import lowe.mike.strimko.model.Grid;

/**
 * {@code CombinationIterator} instances create combinations of possible numbers when solving a
 * puzzle.
 *
 * <p>For example, given combination size of 2 and the size of the grid given as 3, the possible
 * combinations would
 * be: [1, 2], [1, 3] and [2, 3].
 *
 * @author Mike Lowe
 */
final class CombinationIterator implements Iterator<Collection<Integer>> {

  private final int combinationSize;
  private final int gridSize;
  private final int numberOfSubsets;
  private int cursor = 1;
  private Collection<Integer> nextCombination;

  /**
   * Creates a new {@code CombinationIterator} given the size of each combination and size
   * of the {@link Grid}.
   *
   * @param combinationSize size of each combination
   * @param gridSize size of the {@link Grid}
   */
  CombinationIterator(int combinationSize, int gridSize) {
    this.combinationSize = combinationSize;
    this.gridSize = gridSize;
    this.numberOfSubsets = 1 << this.gridSize;
    this.nextCombination = getNextCombination();
  }

  @Override
  public boolean hasNext() {
    return nextCombination != null;
  }

  @Override
  public Collection<Integer> next() {
    Collection<Integer> currentCombination = nextCombination;
    nextCombination = getNextCombination();
    return currentCombination;
  }

  private Collection<Integer> getNextCombination() {
    Collection<Integer> nextCombination = new HashSet<>();

    for (int i = 0; i < gridSize; i++) {
      if ((cursor & (1 << i)) > 0) {
        nextCombination.add(i + 1);
      }
    }

    cursor++;

    // no more combinations left
    if (cursor > numberOfSubsets) {
      return null;
    }

    // if combination is not the correct size, find the next one
    if (nextCombination.size() != combinationSize) {
      return getNextCombination();
    }

    return nextCombination;
  }

}
