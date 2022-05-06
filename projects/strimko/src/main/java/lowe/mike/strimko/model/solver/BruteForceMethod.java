package lowe.mike.strimko.model.solver;

import static com.google.common.base.Preconditions.checkArgument;
import static lowe.mike.strimko.model.Grid.copyOf;

import lowe.mike.strimko.model.Cell;
import lowe.mike.strimko.model.Grid;

/**
 * {@code BruteForceMethod} represents a brute-force solving method.
 *
 * <p>A depth-first search algorithm is used to test each possible number for a cell until a
 * solution
 * has been found.
 *
 * <p>Instances of {@code BruteForceMethod} cannot be created.
 *
 * @author Mike Lowe
 */
final class BruteForceMethod extends SolvingMethod {

  // don't want instances
  private BruteForceMethod() {
  }

  /**
   * Runs brute-force method.
   *
   * @param grid the {@link Grid} to run method over
   * @return the solved {@link Grid}
   * @throws IllegalArgumentException if {@code grid} is not solvable or has multiple solutions
   */
  static Grid run(Grid grid) {
    // used to keep note of information when running algorithm
    Note note = new Note();

    run(grid, note);

    checkArgument(!isUnsolvable(note), "Grid is unsolvable");
    checkArgument(!note.foundMultipleSolutions, "Grid has multiple solutions");

    return note.solution;
  }

  private static boolean run(Grid grid, Note note) {
    for (Cell cell : grid.getCells()) {
      if (shouldTryAndSet(cell)) {
        if (noMorePossibles(cell)) {
          return false;
        }
        return tryAndSetNumbers(grid, cell, note);
      }
    }

    return true;
  }

  private static boolean shouldTryAndSet(Cell cell) {
    return !cell.isSet();
  }

  private static boolean noMorePossibles(Cell cell) {
    return cell.getPossibleNumbers().isEmpty();
  }

  private static boolean tryAndSetNumbers(Grid grid, Cell cell, Note note) {
    int size = grid.getSize();

    for (int number = 1; number <= size; number++) {
      if (cellContainsPossible(cell, number)) {
        cell.setNumber(number);

        if (foundSolution(grid, note)) {

          if (isFirstSolution(note)) {
            updateNote(note, grid);
            cell.clearNumber();
            return false;
          } else {
            note.foundMultipleSolutions = true;
            return true;
          }

        } else {
          cell.clearNumber();
        }
      }
    }
    return false;
  }

  private static boolean foundSolution(Grid grid, Note note) {
    return run(grid, note);
  }

  private static boolean isFirstSolution(Note note) {
    return !note.foundSolution;
  }

  private static void updateNote(Note note, Grid grid) {
    note.foundSolution = true;
    note.solution = copyOf(grid);
  }

  private static boolean isUnsolvable(Note note) {
    return !note.foundSolution || !note.solution.isSolved();
  }

  private static class Note {

    private boolean foundSolution;
    private boolean foundMultipleSolutions;
    private Grid solution;
  }

}
