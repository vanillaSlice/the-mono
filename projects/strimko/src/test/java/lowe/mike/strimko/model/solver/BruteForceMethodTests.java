package lowe.mike.strimko.model.solver;

import static lowe.mike.strimko.model.solver.BruteForceMethod.run;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import lowe.mike.strimko.model.Grid;
import org.junit.jupiter.api.Test;

/**
 * {@link BruteForceMethod} tests.
 *
 * @author Mike Lowe
 */
public final class BruteForceMethodTests extends SolvingMethodTests {

  @Test
  public void test_run_solvable() {
    // setup
    int size = 5;
    int[][] streams = {{1, 1, 2, 2, 2}, {2, 2, 1, 3, 3}, {1, 1, 3, 4, 3}, {5, 4, 4, 5, 3},
        {5, 5, 5, 4, 4}};
    int[][] numbers = {{0, 0, 3, 0, 0}, {0, 4, 0, 1, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0},
        {0, 0, 5, 0, 0}};
    Grid grid = newStrimkoGrid(size, streams, numbers);

    // execution
    Grid solvedGrid = run(grid);

    // verification
    assertTrue(solvedGrid.isSolved());
  }

  @Test
  public void test_run_multipleSolutions() {
    // setup
    int size = 3;
    int[][] streams = {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}};
    int[][] numbers = {{0, 1, 0}, {0, 0, 0}, {0, 0, 0}};
    Grid grid = newStrimkoGrid(size, streams, numbers);

    // execution
    assertThrows(IllegalArgumentException.class,
        () -> run(grid));
  }

  @Test
  public void test_run_unsolvable() {
    // setup
    int size = 4;
    int[][] streams = {{1, 2, 2, 3}, {2, 1, 3, 2}, {4, 3, 1, 4}, {3, 4, 4, 1}};
    int[][] numbers = {{2, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
    Grid grid = newStrimkoGrid(size, streams, numbers);
    grid.getCell(0, 1).setNumber(2);

    // execution
    assertThrows(IllegalArgumentException.class,
        () -> run(grid));
  }

  @Test
  public void test_run_filled() {
    // setup
    int size = 6;
    int[][] streams = {{1, 2, 3, 4, 5, 6}, {1, 2, 3, 4, 5, 6}, {1, 2, 3, 4, 5, 6},
        {1, 2, 3, 4, 5, 6},
        {1, 2, 3, 4, 5, 6}, {1, 2, 3, 4, 5, 6}};
    int[][] numbers = {{1, 2, 3, 4, 5, 6}, {2, 3, 4, 5, 6, 1}, {3, 4, 5, 6, 1, 2},
        {4, 5, 6, 1, 2, 3},
        {5, 6, 1, 2, 3, 4}, {6, 1, 2, 3, 4, 5}};
    Grid grid = newStrimkoGrid(size, streams, numbers);

    // execution
    assertThrows(IllegalArgumentException.class,
        () -> run(grid));
  }

}
