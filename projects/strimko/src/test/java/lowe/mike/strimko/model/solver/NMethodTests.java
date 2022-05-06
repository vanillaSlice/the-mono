package lowe.mike.strimko.model.solver;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static lowe.mike.strimko.model.solver.NMethod.runHiddenN;
import static lowe.mike.strimko.model.solver.NMethod.runHiddenNOverColumns;
import static lowe.mike.strimko.model.solver.NMethod.runHiddenNOverRows;
import static lowe.mike.strimko.model.solver.NMethod.runHiddenNOverStreams;
import static lowe.mike.strimko.model.solver.NMethod.runNakedN;
import static lowe.mike.strimko.model.solver.NMethod.runNakedNOverColumns;
import static lowe.mike.strimko.model.solver.NMethod.runNakedNOverRows;
import static lowe.mike.strimko.model.solver.NMethod.runNakedNOverStreams;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import lowe.mike.strimko.model.Cell;
import lowe.mike.strimko.model.Grid;
import org.junit.jupiter.api.Test;

/**
 * {@link NMethod} tests.
 *
 * @author Mike Lowe
 */
public final class NMethodTests extends SolvingMethodTests {

  @Test
  public void test_runNakedNOverRows() {
    // setup
    int[][] numbers = {{4, 0, 0, 2, 7, 0, 6, 0, 0}, {7, 9, 8, 1, 5, 6, 2, 3, 4},
        {0, 2, 0, 8, 4, 0, 0, 0, 7},
        {2, 3, 7, 4, 6, 8, 9, 5, 1}, {8, 4, 9, 5, 3, 1, 7, 2, 6}, {5, 6, 1, 7, 9, 2, 8, 4, 3},
        {0, 8, 2, 0, 1, 5, 4, 7, 9}, {0, 7, 0, 0, 2, 4, 3, 0, 0}, {0, 0, 4, 0, 8, 7, 0, 0, 2}};
    Grid grid = newSudokuGrid(numbers);

    // execution
    final boolean changed = runNakedNOverRows(grid, 2);

    // verification
    Map<Cell, Collection<Integer>> cellsAndExpectedPossibles = new HashMap<>();
    cellsAndExpectedPossibles.put(grid.getCell(8, 0), asList(3, 6, 9));
    cellsAndExpectedPossibles.put(grid.getCell(8, 7), singletonList(6));
    assertChangedAndCellsContainExpectedPossibles(changed, cellsAndExpectedPossibles);
  }

  @Test
  public void test_runNakedNOverColumns() {
    // setup
    int[][] numbers = {{8, 9, 1, 0, 0, 5, 7, 6, 0}, {5, 3, 7, 6, 9, 0, 2, 0, 8},
        {4, 6, 2, 0, 0, 0, 0, 0, 5},
        {2, 4, 3, 5, 1, 0, 8, 0, 6}, {1, 5, 6, 0, 0, 0, 4, 0, 0}, {9, 7, 8, 0, 4, 6, 5, 0, 0},
        {3, 1, 9, 0, 5, 0, 6, 8, 7}, {6, 8, 4, 0, 0, 0, 0, 5, 2}, {7, 2, 5, 8, 6, 0, 3, 0, 0}};
    Grid grid = newSudokuGrid(numbers);

    // execution
    final boolean changed = runNakedNOverColumns(grid, 3);

    // verification
    Map<Cell, Collection<Integer>> cellsAndExpectedPossibles = new HashMap<>();
    cellsAndExpectedPossibles.put(grid.getCell(2, 3), asList(1, 7));
    cellsAndExpectedPossibles.put(grid.getCell(4, 3), asList(7, 9));
    cellsAndExpectedPossibles.put(grid.getCell(7, 3), asList(1, 7, 9));
    assertChangedAndCellsContainExpectedPossibles(changed, cellsAndExpectedPossibles);
  }

  @Test
  public void test_runNakedNOverStreams() {
    // setup
    int[][] numbers = {{6, 2, 4, 9, 0, 0, 0, 0, 0}, {7, 3, 9, 1, 0, 0, 0, 0, 8},
        {8, 1, 5, 0, 0, 4, 0, 0, 0},
        {4, 0, 0, 0, 0, 9, 3, 7, 0}, {3, 0, 0, 0, 4, 0, 0, 0, 6}, {5, 9, 1, 0, 0, 3, 0, 0, 2},
        {9, 0, 0, 4, 0, 0, 2, 0, 0}, {1, 0, 0, 2, 9, 6, 0, 0, 4}, {2, 4, 8, 3, 5, 7, 1, 6, 9}};
    Grid grid = newSudokuGrid(numbers);

    // execution
    final boolean changed = runNakedNOverStreams(grid, 4);

    // verification
    Map<Cell, Collection<Integer>> cellsAndExpectedPossibles = new HashMap<>();
    cellsAndExpectedPossibles.put(grid.getCell(1, 6), asList(4, 6));
    cellsAndExpectedPossibles.put(grid.getCell(1, 7), asList(2, 4));
    cellsAndExpectedPossibles.put(grid.getCell(2, 6), asList(6, 9));
    cellsAndExpectedPossibles.put(grid.getCell(2, 7), asList(2, 9));
    assertChangedAndCellsContainExpectedPossibles(changed, cellsAndExpectedPossibles);
  }

  @Test
  public void test_runNakedN_noChange() {
    // setup
    Grid grid = newGridWithNoNumbers();

    // execution
    final boolean changed = runNakedN(grid, 2);

    // verification
    assertFalse(changed);
  }

  @Test
  public void test_runHiddenNOverRows() {
    // setup
    int[][] numbers = {{0, 6, 0, 3, 9, 0, 1, 0, 0}, {0, 0, 3, 1, 5, 0, 0, 9, 0},
        {1, 9, 0, 4, 2, 6, 3, 0, 0},
        {8, 3, 0, 5, 7, 9, 4, 1, 0}, {9, 0, 0, 0, 6, 1, 0, 0, 0}, {0, 5, 1, 0, 4, 3, 0, 0, 9},
        {4, 1, 9, 6, 3, 5, 8, 2, 7}, {0, 2, 0, 9, 8, 4, 5, 0, 1}, {0, 8, 0, 7, 1, 2, 9, 4, 0}};
    Grid grid = newSudokuGrid(numbers);

    // execution
    final boolean changed = runHiddenNOverRows(grid, 2);

    // verification
    Map<Cell, Collection<Integer>> cellsAndExpectedPossibles = new HashMap<>();
    Collection<Integer> expectedPossibles = asList(3, 5);
    cellsAndExpectedPossibles.put(grid.getCell(4, 7), expectedPossibles);
    cellsAndExpectedPossibles.put(grid.getCell(4, 8), expectedPossibles);
    assertChangedAndCellsContainExpectedPossibles(changed, cellsAndExpectedPossibles);
  }

  @Test
  public void test_runHiddenNOverColumns() {
    // setup
    int[][] numbers = {{0, 4, 9, 1, 3, 2, 0, 0, 0}, {0, 8, 1, 4, 7, 9, 0, 0, 0},
        {3, 2, 7, 6, 8, 5, 9, 1, 4},
        {0, 9, 6, 0, 5, 1, 8, 0, 0}, {0, 7, 5, 0, 2, 8, 0, 0, 0}, {0, 3, 8, 0, 4, 6, 0, 0, 5},
        {8, 5, 3, 2, 6, 7, 0, 0, 0}, {7, 1, 2, 8, 9, 4, 5, 6, 3}, {9, 6, 4, 5, 1, 3, 0, 0, 0}};
    Grid grid = newSudokuGrid(numbers);

    // execution
    final boolean changed = runHiddenNOverColumns(grid, 2);

    // verification
    Map<Cell, Collection<Integer>> cellsAndExpectedPossibles = new HashMap<>();
    Collection<Integer> expectedPossibles = asList(1, 9);
    cellsAndExpectedPossibles.put(grid.getCell(4, 8), expectedPossibles);
    cellsAndExpectedPossibles.put(grid.getCell(6, 8), expectedPossibles);
    assertChangedAndCellsContainExpectedPossibles(changed, cellsAndExpectedPossibles);
  }

  @Test
  public void test_runHiddenNOverStreams() {
    // setup
    int[][] numbers = {{2, 8, 0, 0, 0, 0, 4, 7, 3}, {5, 3, 4, 8, 2, 7, 1, 9, 6},
        {0, 7, 1, 0, 3, 4, 0, 8, 0},
        {3, 0, 0, 5, 0, 0, 0, 4, 0}, {0, 0, 0, 3, 4, 0, 0, 6, 0}, {4, 6, 0, 7, 9, 0, 3, 1, 0},
        {0, 9, 0, 2, 0, 3, 6, 5, 4}, {0, 0, 3, 0, 0, 9, 8, 2, 1}, {0, 0, 0, 0, 8, 0, 9, 3, 7}};
    Grid grid = newSudokuGrid(numbers);

    // execution
    final boolean changed = runHiddenNOverStreams(grid, 3);

    // verification
    Map<Cell, Collection<Integer>> cellsAndExpectedPossibles = new HashMap<>();
    cellsAndExpectedPossibles.put(grid.getCell(7, 1), asList(4, 5));
    cellsAndExpectedPossibles.put(grid.getCell(8, 1), asList(2, 4, 5));
    cellsAndExpectedPossibles.put(grid.getCell(8, 2), asList(2, 5));
    assertChangedAndCellsContainExpectedPossibles(changed, cellsAndExpectedPossibles);
  }

  @Test
  public void test_runHiddenN_noChange() {
    // setup
    Grid grid = newGridWithNoNumbers();

    // execution
    final boolean changed = runHiddenN(grid, 2);

    // verification
    assertFalse(changed);
  }

}
