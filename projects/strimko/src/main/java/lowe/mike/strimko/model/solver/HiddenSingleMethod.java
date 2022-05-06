package lowe.mike.strimko.model.solver;

import java.util.Collection;
import lowe.mike.strimko.model.Cell;
import lowe.mike.strimko.model.Grid;
import lowe.mike.strimko.model.Position;

/**
 * {@code HiddenSingleMethod} represents the 'Hidden Single' solving method.
 *
 * <p>If a candidate only appears once in a set of possible numbers for a row, column or stream,
 * then
 * the cell containing this possible must be this number.
 *
 * <p>Instances of {@code HiddenSingleMethod} cannot be created.
 *
 * @author Mike Lowe
 */
final class HiddenSingleMethod extends SolvingMethod {

  // don't want instances
  private HiddenSingleMethod() {
  }


  static boolean runOverRows(Grid grid, Collection<Position> hints) {
    return run(grid, hints, grid.getRows());
  }

  static boolean runOverColumns(Grid grid, Collection<Position> hints) {
    return run(grid, hints, grid.getColumns());
  }

  static boolean runOverStreams(Grid grid, Collection<Position> hints) {
    return run(grid, hints, grid.getStreams());
  }

  /**
   * Runs 'Hidden Single' method.
   *
   * @param grid the {@link Grid} to run method over
   * @param hints the {@link Collection} of hints to update
   * @return {@code true} if any changes where made to the {@link Grid}, {@code false} otherwise
   */
  static boolean run(Grid grid, Collection<Position> hints) {
    if (runOverRows(grid, hints)) {
      return true;
    }
    if (runOverColumns(grid, hints)) {
      return true;
    }
    return runOverStreams(grid, hints);
  }

  private static boolean run(Grid grid, Collection<Position> hints,
      Collection<Collection<Cell>> groups) {
    int size = grid.getSize();

    for (Collection<Cell> group : groups) {
      for (int number = 1; number <= size; number++) {
        if (groupContainsHiddenSingle(hints, group, number)) {
          return true;
        }
      }
    }

    return false;
  }

  private static boolean groupContainsHiddenSingle(Collection<Position> hints,
      Collection<Cell> group,
      int number) {
    Collection<Cell> cellsContainingPossible = getCellsContainingPossible(group, number);

    if (foundCellWithHiddenSingle(cellsContainingPossible)) {
      Cell cell = getCell(cellsContainingPossible);
      cell.setNumber(number);
      addToHints(cell, hints);
      return true;
    }

    return false;
  }

  private static boolean foundCellWithHiddenSingle(Collection<Cell> cellsContainingPossible) {
    return cellsContainingPossible.size() == 1;
  }

  private static Cell getCell(Collection<Cell> cellsContainingPossible) {
    return cellsContainingPossible.iterator().next();
  }

}
