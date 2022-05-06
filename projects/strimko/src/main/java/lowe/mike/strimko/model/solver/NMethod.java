package lowe.mike.strimko.model.solver;

import static java.util.Collections.disjoint;

import java.util.Collection;
import java.util.HashSet;
import lowe.mike.strimko.model.Cell;
import lowe.mike.strimko.model.Grid;

/**
 * {@code NMethod} represents the 'Naked N' and 'Hidden N' solving methods.
 *
 * <p>The 'Naked N' solving method is as follows:
 *
 * <p>If there are N number of {@link Cell}s in a group that have a combined number of possibles
 * equal to N, then we can remove these numbers from all other {@link Cell}s in the group.
 *
 * <p>The 'Hidden N' solving method is as follows:
 *
 * <p>If there are N number of {@link Cell}s with N candidates between them that don't appear
 * elsewhere in the same group, then the other possible numbers can be removed from these {@link
 * Cell}s.
 *
 * <p>Instances of {@code NMethod} cannot be created.
 *
 * @author Mike Lowe
 */
final class NMethod {

  private enum Mode {
    NAKED, HIDDEN
  }

  // don't want instances
  private NMethod() {
  }

  /**
   * Runs 'Naked N' method.
   *
   * @param grid the {@link Grid} to run method over
   * @param n number of possible numbers that must be shared
   * @return {@code true} if any changes where made to the {@link Grid}, {@code false} otherwise
   */
  static boolean runNakedN(Grid grid, int n) {
    return run(Mode.NAKED, grid, n);
  }

  /**
   * Runs 'Hidden N' method.
   *
   * @param grid the {@link Grid} to run method over
   * @param n number of possible numbers that must be shared
   * @return {@code true} if any changes where made to the {@link Grid}, {@code false} otherwise
   */
  static boolean runHiddenN(Grid grid, int n) {
    return run(Mode.HIDDEN, grid, n);
  }

  private static boolean runOverRows(Mode mode, Grid grid, int n) {
    return run(mode, grid.getRows(), n, grid.getSize());
  }

  private static boolean runOverColumns(Mode mode, Grid grid, int n) {
    return run(mode, grid.getColumns(), n, grid.getSize());
  }

  private static boolean runOverStreams(Mode mode, Grid grid, int n) {
    return run(mode, grid.getStreams(), n, grid.getSize());
  }

  static boolean runNakedNOverRows(Grid grid, int n) {
    return runOverRows(Mode.NAKED, grid, n);
  }

  static boolean runNakedNOverColumns(Grid grid, int n) {
    return runOverColumns(Mode.NAKED, grid, n);
  }

  static boolean runNakedNOverStreams(Grid grid, int n) {
    return runOverStreams(Mode.NAKED, grid, n);
  }

  static boolean runHiddenNOverRows(Grid grid, int n) {
    return runOverRows(Mode.HIDDEN, grid, n);
  }

  static boolean runHiddenNOverColumns(Grid grid, int n) {
    return runOverColumns(Mode.HIDDEN, grid, n);
  }

  static boolean runHiddenNOverStreams(Grid grid, int n) {
    return runOverStreams(Mode.HIDDEN, grid, n);
  }

  private static boolean run(Mode mode, Grid grid, int n) {
    if (runOverRows(mode, grid, n)) {
      return true;
    }
    if (runOverColumns(mode, grid, n)) {
      return true;
    }
    return runOverStreams(mode, grid, n);
  }

  private static boolean run(Mode mode, Collection<Collection<Cell>> groups, int n, int size) {
    CombinationIterator iterator = new CombinationIterator(n, size);

    while (iterator.hasNext()) {
      Collection<Integer> combination = iterator.next();
      if (groupContainsN(mode, groups, n, combination)) {
        return true;
      }
    }

    return false;
  }

  private static boolean groupContainsN(Mode mode, Collection<Collection<Cell>> groups, int n,
      Collection<Integer> combination) {
    for (Collection<Cell> group : groups) {
      Collection<Integer> foundNumbers = new HashSet<>();
      Collection<Cell> cellsContainingCombination = new HashSet<>();

      findCellsContainingCombination(mode, group, cellsContainingCombination, combination,
          foundNumbers);

      if (foundN(cellsContainingCombination, n, foundNumbers, combination)) {
        if (removePossibles(mode, combination, group, cellsContainingCombination)) {
          return true;
        }
      }
    }
    return false;
  }

  private static void findCellsContainingCombination(Mode mode, Collection<Cell> group,
      Collection<Cell> cellsContainingCombination, Collection<Integer> combination,
      Collection<Integer> foundNumbers) {
    for (Cell cell : group) {
      if (shouldAddToCombination(mode, cell, combination)) {
        cellsContainingCombination.add(cell);
        foundNumbers.addAll(cell.getPossibleNumbers());
      }
    }
  }

  private static boolean shouldAddToCombination(Mode mode, Cell cell,
      Collection<Integer> combination) {
    return (mode == Mode.NAKED && cellContainsOnly(cell, combination))
        || (mode == Mode.HIDDEN && cellContainsOneOf(cell, combination));
  }

  private static boolean cellContainsOnly(Cell cell, Collection<Integer> combination) {
    Collection<Integer> original = new HashSet<>(cell.getPossibleNumbers());
    if (original.size() == 0) {
      return false;
    }
    original.removeAll(combination);
    return original.size() == 0;
  }

  private static boolean cellContainsOneOf(Cell cell, Collection<Integer> combination) {
    return !disjoint(cell.getPossibleNumbers(), combination);
  }

  private static boolean foundN(Collection<Cell> cellsContainingCombination, int n,
      Collection<Integer> foundNumbers,
      Collection<Integer> combination) {
    return cellsContainingCombination.size() == n && foundNumbers.containsAll(combination);
  }

  private static boolean removePossibles(Mode mode, Collection<Integer> combination,
      Collection<Cell> group,
      Collection<Cell> cellsContainingCombination) {
    if (mode == Mode.NAKED) {
      return removePossiblesFromGroupExcept(combination, group, cellsContainingCombination);
    } else {
      return removeNumbersOutsideOfCombination(cellsContainingCombination, combination);
    }
  }

  private static boolean removePossiblesFromGroupExcept(Collection<Integer> combination,
      Collection<Cell> group,
      Collection<Cell> cells) {
    boolean changed = false;

    for (Cell cell : group) {
      if (shouldRemove(cell, cells)) {
        if (removeNumbers(cell, combination)) {
          changed = true;
        }
      }
    }

    return changed;
  }

  private static boolean shouldRemove(Cell cell, Collection<Cell> cells) {
    return !cells.contains(cell);
  }

  private static boolean removeNumbers(Cell cell, Collection<Integer> combination) {
    return cell.getPossibleNumbers().removeAll(combination);
  }

  private static boolean removeNumbersOutsideOfCombination(
      Collection<Cell> cellsContainingCombination,
      Collection<Integer> combination) {
    boolean changed = false;

    for (Cell cellContainingCombination : cellsContainingCombination) {
      if (cellContainingCombination.getPossibleNumbers().retainAll(combination)) {
        changed = true;
      }
    }

    return changed;
  }

}
