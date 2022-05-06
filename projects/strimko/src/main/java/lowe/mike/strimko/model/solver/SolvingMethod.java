package lowe.mike.strimko.model.solver;

import java.util.Collection;
import java.util.HashSet;
import lowe.mike.strimko.model.Cell;
import lowe.mike.strimko.model.Position;

/**
 * {@code SolvingMethod} represents a solving method that subclasses define the specific
 * implementations for.
 *
 * @author Mike Lowe
 */
class SolvingMethod {

  /**
   * Adds a hint given the {@link Cell} to get positional information from.
   *
   * @param cell the {@link Cell}
   * @param hints the {@link Collection} of hints
   */
  static void addToHints(Cell cell, Collection<Position> hints) {
    Position position = new Position(cell.getRowIndex(), cell.getColumnIndex());
    hints.add(position);
  }

  /**
   * Returns {@code true} if the {@link Cell} contains the number in its possible candidate
   * numbers.
   *
   * @param cell the {@link Cell} to check
   * @param number the number to check is in the possible numbers
   * @return {@code true} if the {@link Cell} contains the number in its possible candidate numbers
   */
  static boolean cellContainsPossible(Cell cell, int number) {
    return cell.getPossibleNumbers().contains(number);
  }

  /**
   * Returns a {@link Collection} of {@link Cell}s containing the number in its possible candidate
   * numbers.
   *
   * @param group the group of {@link Cell}s to get the subset from
   * @param number the number to check the {@link Cell}s for
   * @return a {@link Collection} of {@link Cell}s containing the number in its possible candidate
   *     numbers
   */
  static Collection<Cell> getCellsContainingPossible(Collection<Cell> group, int number) {
    Collection<Cell> cellsContainingPossible = new HashSet<>();

    for (Cell cell : group) {
      if (cellContainsPossible(cell, number)) {
        cellsContainingPossible.add(cell);
      }
    }

    return cellsContainingPossible;
  }

}
