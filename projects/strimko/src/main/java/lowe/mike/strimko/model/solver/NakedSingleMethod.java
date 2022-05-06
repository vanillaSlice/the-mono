package lowe.mike.strimko.model.solver;

import java.util.Collection;
import lowe.mike.strimko.model.Cell;
import lowe.mike.strimko.model.Grid;
import lowe.mike.strimko.model.Position;

/**
 * {@code NakedSingleMethod} represents the 'Naked Single' solving method.
 *
 * <p>When a cell has only one candidate in its set of possible numbers, then it must be this
 * number.
 *
 * <p>Instances of {@code NakedSingleMethod} cannot be created.
 *
 * @author Mike Lowe
 */
final class NakedSingleMethod extends SolvingMethod {

  // don't want instances
  private NakedSingleMethod() {
  }

  /**
   * Runs 'Naked Single' method.
   *
   * @param grid the {@link Grid} to run method over
   * @param hints the {@link Collection} of hints to update
   * @return {@code true} if any changes where made to the {@link Grid}, {@code false} otherwise
   */
  static boolean run(Grid grid, Collection<Position> hints) {
    for (Cell cell : grid.getCells()) {
      if (foundCellWithOnePossibleNumber(cell)) {
        int number = getNumber(cell);
        cell.setNumber(number);
        addToHints(cell, hints);
        return true;
      }
    }

    return false;
  }

  private static boolean foundCellWithOnePossibleNumber(Cell cell) {
    return cell.getPossibleNumbers().size() == 1;
  }

  private static int getNumber(Cell cell) {
    return cell.getPossibleNumbers().iterator().next();
  }

}
