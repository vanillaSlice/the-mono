package lowe.mike.strimko.model.solver;

import static lowe.mike.strimko.model.Constants.SUDOKU_SIZE;
import static lowe.mike.strimko.model.Constants.getSudokuStreams;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.Map;
import lowe.mike.strimko.model.Cell;
import lowe.mike.strimko.model.Grid;
import lowe.mike.strimko.model.Grid.GridBuilder;

/**
 * {@link SolvingMethod} tests.
 *
 * @author Mike Lowe
 */
class SolvingMethodTests {

  static Grid newStrimkoGrid(int size, int[][] streams, int[][] numbers) {
    return new GridBuilder(size).setStreams(streams).setNumbers(numbers).build();
  }

  static Grid newSudokuGrid(int[][] numbers) {
    return newStrimkoGrid(SUDOKU_SIZE, getSudokuStreams(), numbers);
  }

  static Grid newGridWithNoNumbers() {
    int size = 3;
    int[][] streams = {{1, 1, 2}, {1, 2, 3}, {2, 3, 3}};
    int[][] numbers = new int[size][size];
    return newStrimkoGrid(size, streams, numbers);
  }

  static void assertChangedAndCellsContainExpectedPossibles(boolean changed,
      Map<Cell, Collection<Integer>> cellsAndExpectedPossibles) {
    assertTrue(changed);
    for (Cell cell : cellsAndExpectedPossibles.keySet()) {
      Collection<Integer> expectedPossibles = cellsAndExpectedPossibles.get(cell);
      assertTrue(cell.getPossibleNumbers().containsAll(expectedPossibles));
    }
  }

}
