package lowe.mike.strimko.model.solver;

import static lowe.mike.strimko.model.solver.HiddenSingleMethod.run;
import static lowe.mike.strimko.model.solver.HiddenSingleMethod.runOverColumns;
import static lowe.mike.strimko.model.solver.HiddenSingleMethod.runOverRows;
import static lowe.mike.strimko.model.solver.HiddenSingleMethod.runOverStreams;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.LinkedHashSet;
import lowe.mike.strimko.model.Grid;
import lowe.mike.strimko.model.Position;
import org.junit.jupiter.api.Test;

/**
 * {@link HiddenSingleMethod} tests.
 *
 * @author Mike Lowe
 */
public final class HiddenSingleMethodTests extends SolvingMethodTests {

  @Test
  public void test_runOverRows() {
    // setup
    int size = 4;
    int[][] streams = {{1, 2, 1, 2}, {3, 1, 2, 4}, {1, 3, 4, 2}, {3, 4, 3, 4}};
    int[][] numbers = {{0, 0, 0, 0}, {0, 3, 0, 1}, {0, 4, 0, 0}, {0, 0, 0, 0}};
    Grid grid = newStrimkoGrid(size, streams, numbers);
    Collection<Position> hints = new LinkedHashSet<>();

    // execution
    boolean changed = runOverRows(grid, hints);

    // verification
    Position changedPosition = new Position(0, 3);
    assertConditions(grid, hints, changed, 3, changedPosition);
  }

  @Test
  public void test_runOverColumns() {
    // setup
    int size = 5;
    int[][] streams = {{1, 1, 1, 1, 1}, {2, 2, 3, 3, 4}, {2, 3, 3, 5, 4}, {2, 3, 5, 5, 4},
        {2, 5, 5, 4, 4}};
    int[][] numbers = {{0, 0, 0, 0, 0}, {0, 3, 0, 0, 4}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0},
        {2, 0, 0, 5, 0}};
    Grid grid = newStrimkoGrid(size, streams, numbers);
    Collection<Position> hints = new LinkedHashSet<>();

    // execution
    boolean changed = runOverColumns(grid, hints);

    // verification
    Position changedPosition = new Position(0, 0);
    assertConditions(grid, hints, changed, 3, changedPosition);
  }

  @Test
  public void test_runOverStreams() {
    // setup
    int size = 5;
    int[][] streams = {{1, 2, 3, 4, 4}, {1, 3, 2, 2, 4}, {1, 3, 3, 2, 4}, {1, 1, 2, 3, 4},
        {5, 5, 5, 5, 5}};
    int[][] numbers = {{1, 0, 0, 0, 0}, {0, 2, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 4, 0},
        {0, 0, 0, 0, 5}};
    Grid grid = newStrimkoGrid(size, streams, numbers);
    Collection<Position> hints = new LinkedHashSet<>();

    // execution
    boolean changed = runOverStreams(grid, hints);

    // verification
    Position changedPosition = new Position(0, 3);
    assertConditions(grid, hints, changed, 5, changedPosition);
  }

  @Test
  public void test_run_noChange() {
    // setup
    Grid grid = newGridWithNoNumbers();
    Collection<Position> hints = new LinkedHashSet<>();

    // execution
    boolean changed = run(grid, hints);

    // verification
    assertFalse(changed);
  }

  private static void assertConditions(Grid grid, Collection<Position> hints, boolean changed,
      int number,
      Position changedPosition) {
    assertTrue(changed);
    assertEquals(number,
        grid.getCell(changedPosition.getRowIndex(), changedPosition.getColumnIndex()).getNumber());
    assertTrue(hints.contains(changedPosition));
  }

}
