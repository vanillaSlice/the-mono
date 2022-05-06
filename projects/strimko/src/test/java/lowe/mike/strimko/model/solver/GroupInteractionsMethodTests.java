package lowe.mike.strimko.model.solver;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static lowe.mike.strimko.model.solver.GroupInteractionsMethod.runPointingN;
import static lowe.mike.strimko.model.solver.GroupInteractionsMethod.runPointingNOverColumns;
import static lowe.mike.strimko.model.solver.GroupInteractionsMethod.runPointingNOverRows;
import static lowe.mike.strimko.model.solver.GroupInteractionsMethod.runStreamLineReductionN;
import static lowe.mike.strimko.model.solver.GroupInteractionsMethod.runStreamLineReductionNOverColumns;
import static lowe.mike.strimko.model.solver.GroupInteractionsMethod.runStreamLineReductionNOverRows;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import lowe.mike.strimko.model.Cell;
import lowe.mike.strimko.model.Grid;
import org.junit.jupiter.api.Test;

/**
 * {@link GroupInteractionsMethod} tests.
 *
 * @author Mike Lowe
 */
public final class GroupInteractionsMethodTests extends SolvingMethodTests {

  @Test
  public void test_runPointingNOverRows() {
    // setup
    int[][] numbers = {{0, 3, 0, 7, 8, 1, 0, 5, 0}, {7, 0, 0, 2, 3, 9, 6, 8, 0},
        {0, 0, 0, 6, 5, 4, 0, 7, 3},
        {5, 0, 0, 0, 0, 7, 3, 9, 8}, {3, 1, 7, 9, 2, 8, 5, 4, 6}, {4, 8, 9, 3, 6, 5, 7, 1, 2},
        {0, 0, 0, 0, 0, 2, 0, 3, 0}, {1, 0, 0, 0, 0, 3, 0, 6, 0}, {0, 0, 3, 0, 0, 6, 4, 2, 7}};
    Grid grid = newSudokuGrid(numbers);

    // execution
    boolean changed = runPointingNOverRows(grid, 2);

    // verification
    Map<Cell, Collection<Integer>> cellsAndExpectedPossibles = new HashMap<>();
    cellsAndExpectedPossibles.put(grid.getCell(6, 3), asList(4, 5, 8));
    cellsAndExpectedPossibles.put(grid.getCell(6, 4), asList(4, 7, 9));
    assertChangedAndCellsContainExpectedPossibles(changed, cellsAndExpectedPossibles);
  }

  @Test
  public void test_runPointingNOverColumns() {
    // setup
    int[][] numbers = {{9, 3, 0, 0, 5, 0, 0, 0, 0}, {2, 0, 0, 6, 3, 0, 0, 9, 5},
        {8, 5, 6, 0, 0, 2, 0, 0, 0},
        {0, 0, 3, 1, 8, 0, 5, 7, 0}, {0, 0, 5, 0, 2, 0, 9, 8, 0}, {0, 8, 0, 0, 0, 5, 0, 0, 0},
        {0, 0, 0, 8, 0, 0, 1, 5, 9}, {5, 0, 8, 2, 1, 0, 0, 0, 4}, {0, 0, 0, 5, 6, 0, 0, 0, 8}};
    Grid grid = newSudokuGrid(numbers);

    // execution
    boolean changed = runPointingNOverColumns(grid, 3);

    // verification
    Map<Cell, Collection<Integer>> cellsAndExpectedPossibles = new HashMap<>();
    cellsAndExpectedPossibles.put(grid.getCell(4, 5), asList(4, 6, 7));
    assertChangedAndCellsContainExpectedPossibles(changed, cellsAndExpectedPossibles);
  }

  @Test
  public void test_runPointingN_noChange() {
    // setup
    Grid grid = newGridWithNoNumbers();

    // execution
    boolean changed = runPointingN(grid, 2);

    // verification
    assertFalse(changed);
  }

  @Test
  public void test_runStreamLineReductionNOverRows() {
    // setup
    int[][] numbers = {{0, 1, 6, 0, 0, 7, 8, 0, 3}, {0, 9, 0, 8, 0, 0, 0, 0, 0},
        {8, 7, 0, 0, 0, 1, 0, 6, 0},
        {0, 4, 8, 0, 0, 0, 3, 0, 0}, {6, 5, 0, 0, 0, 9, 0, 8, 2}, {2, 3, 9, 0, 0, 0, 6, 5, 0},
        {0, 6, 0, 9, 0, 0, 0, 2, 0}, {0, 8, 0, 0, 0, 2, 9, 3, 6}, {9, 2, 4, 6, 0, 0, 5, 1, 0}};
    Grid grid = newSudokuGrid(numbers);

    // execution
    final boolean changed = runStreamLineReductionNOverRows(grid, 2);

    // verification
    Map<Cell, Collection<Integer>> cellsAndExpectedPossibles = new HashMap<>();
    cellsAndExpectedPossibles.put(grid.getCell(1, 4), asList(3, 4, 5, 6));
    cellsAndExpectedPossibles.put(grid.getCell(2, 3), asList(3, 4, 5));
    cellsAndExpectedPossibles.put(grid.getCell(2, 4), asList(3, 4, 5, 9));
    assertChangedAndCellsContainExpectedPossibles(changed, cellsAndExpectedPossibles);
  }

  @Test
  public void test_runStreamLineReductionNOverColumns() {
    // setup
    int[][] numbers = {{0, 1, 6, 0, 0, 7, 8, 0, 3}, {0, 9, 0, 8, 0, 0, 0, 0, 0},
        {8, 7, 0, 0, 0, 1, 0, 6, 0},
        {0, 4, 8, 0, 0, 0, 3, 0, 0}, {6, 5, 0, 0, 0, 9, 0, 8, 2}, {2, 3, 9, 0, 0, 0, 6, 5, 0},
        {0, 6, 0, 9, 0, 0, 0, 2, 0}, {0, 8, 0, 0, 0, 2, 9, 3, 6}, {9, 2, 4, 6, 0, 0, 5, 1, 0}};
    Grid grid = newSudokuGrid(numbers);

    // execution
    final boolean changed = runStreamLineReductionNOverColumns(grid, 2);

    // verification
    Map<Cell, Collection<Integer>> cellsAndExpectedPossibles = new HashMap<>();
    cellsAndExpectedPossibles.put(grid.getCell(1, 6), asList(1, 2, 7));
    cellsAndExpectedPossibles.put(grid.getCell(1, 8), asList(1, 5, 7));
    cellsAndExpectedPossibles.put(grid.getCell(2, 6), singletonList(2));
    cellsAndExpectedPossibles.put(grid.getCell(2, 8), asList(5, 9));
    assertChangedAndCellsContainExpectedPossibles(changed, cellsAndExpectedPossibles);
  }

  @Test
  public void test_runStreamLineReductionN_noChange() {
    // setup
    Grid grid = newGridWithNoNumbers();

    // execution
    boolean changed = runStreamLineReductionN(grid, 2);

    // verification
    assertFalse(changed);
  }

}
