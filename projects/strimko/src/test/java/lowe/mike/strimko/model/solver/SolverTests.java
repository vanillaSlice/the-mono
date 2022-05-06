package lowe.mike.strimko.model.solver;

import static lowe.mike.strimko.model.Difficulty.EASY;
import static lowe.mike.strimko.model.Difficulty.HARD;
import static lowe.mike.strimko.model.Difficulty.MEDIUM;
import static lowe.mike.strimko.model.solver.Solver.solve;
import static org.junit.jupiter.api.Assertions.assertEquals;

import lowe.mike.strimko.model.Difficulty;
import lowe.mike.strimko.model.Grid;
import org.junit.jupiter.api.Test;

/**
 * {@link Solver} tests.
 *
 * @author Mike Lowe
 */
public final class SolverTests extends SolvingMethodTests {

  @Test
  public void test_solve_easyStrimko() {
    // setup
    int size = 4;
    int[][] streams = {{1, 2, 2, 3}, {2, 1, 3, 2}, {4, 3, 1, 4}, {3, 4, 4, 1}};
    int[][] numbers = {{0, 0, 0, 2}, {0, 0, 0, 0}, {0, 0, 0, 0}, {4, 0, 0, 1}};
    Grid grid = newStrimkoGrid(size, streams, numbers);

    // execution
    test_solve(grid, EASY);
  }

  @Test
  public void test_solve_easySudoku() {
    // setup
    int[][] numbers = {{8, 0, 0, 0, 0, 0, 4, 6, 0}, {0, 9, 7, 6, 0, 1, 2, 0, 8},
        {0, 4, 0, 8, 0, 5, 0, 1, 0},
        {0, 0, 0, 2, 7, 9, 0, 0, 4}, {0, 5, 0, 0, 0, 0, 0, 2, 0}, {1, 0, 0, 4, 5, 3, 0, 0, 0},
        {0, 7, 0, 3, 0, 6, 0, 9, 0}, {3, 0, 2, 9, 0, 7, 5, 4, 0}, {0, 6, 1, 0, 0, 0, 0, 0, 2}};
    Grid grid = newSudokuGrid(numbers);

    // execution
    test_solve(grid, EASY);
  }

  @Test
  public void test_solve_mediumStrimko() {
    // setup
    int size = 5;
    int[][] streams = {{1, 1, 2, 2, 2}, {3, 1, 1, 2, 4}, {3, 3, 1, 4, 2}, {3, 5, 4, 5, 4},
        {3, 5, 5, 5, 4}};
    int[][] numbers = {{0, 0, 0, 0, 3}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 2},
        {4, 0, 0, 1, 0}};
    Grid grid = newStrimkoGrid(size, streams, numbers);

    // execution
    test_solve(grid, MEDIUM);
  }

  @Test
  public void test_solve_mediumSudoku() {
    // setup
    int[][] numbers = {{0, 7, 0, 0, 0, 6, 0, 0, 0}, {5, 0, 0, 0, 0, 8, 0, 2, 0},
        {8, 1, 0, 0, 2, 4, 0, 0, 0},
        {0, 6, 5, 0, 0, 0, 0, 0, 7}, {3, 0, 0, 7, 0, 1, 0, 0, 6}, {7, 0, 0, 0, 0, 0, 5, 1, 0},
        {0, 0, 0, 2, 4, 0, 0, 7, 1}, {0, 8, 0, 3, 0, 0, 0, 0, 5}, {0, 0, 0, 6, 0, 0, 0, 9, 0}};
    Grid grid = newSudokuGrid(numbers);

    // execution
    test_solve(grid, MEDIUM);
  }

  @Test
  public void test_solve_hardStrimko() {
    // setup
    int size = 7;
    int[][] streams = {{1, 2, 3, 3, 2, 3, 3}, {2, 1, 2, 2, 3, 4, 3}, {2, 1, 1, 1, 1, 4, 3},
        {2, 1, 5, 4, 4, 5, 4}, {6, 6, 6, 5, 4, 5, 7}, {6, 6, 5, 5, 5, 4, 7}, {6, 6, 7, 7, 7, 7, 7}};
    int[][] numbers = {{0, 0, 0, 0, 0, 0, 0}, {1, 0, 0, 3, 5, 6, 0}, {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 5, 7, 2, 0}, {0, 0, 0, 4, 0, 0, 0}, {0, 0, 0, 0, 6, 3, 0}, {0, 0, 0, 0, 0, 0, 0}};
    Grid grid = newStrimkoGrid(size, streams, numbers);

    // execution
    test_solve(grid, HARD);
  }

  @Test
  public void test_solve_hardSudoku() {
    // setups
    int[][] numbers = {{2, 0, 0, 0, 1, 0, 0, 0, 8}, {0, 4, 0, 8, 0, 9, 0, 2, 0},
        {0, 0, 1, 0, 0, 0, 9, 0, 0},
        {0, 3, 0, 2, 0, 1, 0, 7, 0}, {4, 0, 0, 0, 0, 0, 0, 0, 1}, {0, 9, 0, 4, 0, 8, 0, 3, 0},
        {0, 0, 4, 0, 0, 0, 3, 0, 0}, {0, 7, 0, 1, 0, 5, 0, 9, 0}, {6, 0, 0, 0, 9, 0, 0, 0, 2}};
    Grid grid = newSudokuGrid(numbers);

    // execution
    test_solve(grid, HARD);
  }

  private void test_solve(Grid grid, Difficulty expectedDifficulty) {
    SolvingResult result = solve(grid);
    assertEquals(expectedDifficulty, result.getDifficulty());
  }

}
