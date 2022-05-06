package lowe.mike.strimko.model.solver;

import static lowe.mike.strimko.model.Difficulty.EASY;
import static lowe.mike.strimko.model.Difficulty.HARD;
import static lowe.mike.strimko.model.Difficulty.MEDIUM;
import static lowe.mike.strimko.model.Grid.copyOf;

import java.util.Collection;
import java.util.LinkedHashSet;
import lowe.mike.strimko.model.Difficulty;
import lowe.mike.strimko.model.Grid;
import lowe.mike.strimko.model.Position;

/**
 * {@code Solver} class provides a single method {@link #solve(Grid)} which attempts to solve a
 * given {@link Grid} and returns a {@link SolvingResult} object which contains details about the
 * solution etc.
 *
 * <p>Various methods are used to attempt to solve a {@link Grid}, these include:
 * <ul>
 * <li>'Naked Singles'</li>
 * <li>'Hidden Singles'</li>
 * <li>'Pointing Pairs'</li>
 * <li>'Pointing Triples'</li>
 * <li>'Stream Line Reduction Pairs'</li>
 * <li>'Stream Line Reduction Triples'</li>
 * <li>'Naked Pairs'</li>
 * <li>'Naked Triples'</li>
 * <li>'Naked Quads'</li>
 * <li>'Hidden Pairs'</li>
 * <li>'Hidden Triples'</li>
 * <li>'Hidden Quads'</li>
 * <li>Brute-force (as a last resort)</li>
 * </ul>
 * Details about each of these methods can be found online.
 *
 * <p>Instances of {@code Solver} cannot be created.
 *
 * @author Mike Lowe
 */
public final class Solver {

  // don't want instances
  private Solver() {
  }

  /**
   * Attempts to solve the given {@link Grid} and returns a {@link SolvingResult} object.
   *
   * @param grid the {@link Grid} to solve
   * @return the {@link SolvingResult} containing useful information from solving
   * @throws IllegalArgumentException if {@code grid} is not solvable or has multiple solutions
   */
  public static SolvingResult solve(Grid grid) {
    // create a copy so we don't alter the original
    grid = copyOf(grid);
    return runSolvingMethods(grid);
  }

  private static SolvingResult runSolvingMethods(Grid grid) {
    Difficulty difficulty = EASY;
    Collection<Position> hints = new LinkedHashSet<>();

    while (!grid.isSolved()) {
      boolean changed = runEasyMethods(grid, hints);

      if (!changed) {
        changed = runMediumMethods(grid);
        if (difficulty == EASY) {
          difficulty = MEDIUM;
        }
      }

      if (!changed) {
        difficulty = HARD;
        changed = runHardMethods(grid);
      }

      if (!changed) {
        grid = BruteForceMethod.run(grid);
        break;
      }
    }

    return new SolvingResult(difficulty, getSolution(grid), hints);
  }

  private static boolean runEasyMethods(Grid grid, Collection<Position> hints) {
    if (NakedSingleMethod.run(grid, hints)) {
      return true;
    }
    return HiddenSingleMethod.run(grid, hints);
  }

  private static boolean runMediumMethods(Grid grid) {
    // pointing pairs
    if (GroupInteractionsMethod.runPointingN(grid, 2)) {
      return true;
    }
    // stream line reduction pairs
    if (GroupInteractionsMethod.runStreamLineReductionN(grid, 2)) {
      return true;
    }
    // naked pairs
    return NMethod.runNakedN(grid, 2);
  }

  private static boolean runHardMethods(Grid grid) {
    // pointing triples
    if (GroupInteractionsMethod.runPointingN(grid, 3)) {
      return true;
    }
    // stream line reduction triples
    if (GroupInteractionsMethod.runStreamLineReductionN(grid, 3)) {
      return true;
    }
    // naked triples
    if (NMethod.runNakedN(grid, 3)) {
      return true;
    }
    // naked quads
    if (NMethod.runNakedN(grid, 4)) {
      return true;
    }
    // hidden pairs
    if (NMethod.runHiddenN(grid, 2)) {
      return true;
    }
    // hidden triples
    if (NMethod.runHiddenN(grid, 3)) {
      return true;
    }
    // hidden quads
    return NMethod.runHiddenN(grid, 4);
  }

  private static int[][] getSolution(Grid grid) {
    int size = grid.getSize();
    int[][] solution = new int[size][size];
    for (int rowIndex = 0; rowIndex < size; rowIndex++) {
      for (int columnIndex = 0; columnIndex < size; columnIndex++) {
        int number = grid.getCell(rowIndex, columnIndex).getNumber();
        solution[rowIndex][columnIndex] = number;
      }
    }
    return solution;
  }

}
