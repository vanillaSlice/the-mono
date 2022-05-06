package lowe.mike.strimko.model;

import static java.util.Arrays.asList;
import static lowe.mike.strimko.model.Constants.NO_NUMBER;
import static lowe.mike.strimko.model.Grid.copyOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import lowe.mike.strimko.model.Grid.GridBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * {@link Grid} tests.
 *
 * @author Mike Lowe
 */
public final class GridTests {

  private Grid grid;
  private static final int SIZE = 3;
  private static final int[][] STREAMS = {{1, 1, 2}, {1, 2, 3}, {2, 3, 3}};
  private static final int[][] NUMBERS = {{0, 0, 0}, {1, 3, 2}, {0, 0, 0}};
  private static final int[][] SOLUTION = {{3, 2, 1}, {1, 3, 2}, {2, 1, 3}};
  private static final int TOTAL_OCCURRENCES = SIZE * SIZE;

  @BeforeEach
  public void setup() {
    grid = new GridBuilder(SIZE).setStreams(STREAMS).setNumbers(NUMBERS).build();
  }

  @Test
  public void test_getRows() {
    // setup
    int rowIndex = 0;

    // execution and verification
    Collection<Collection<Cell>> rows = grid.getRows();
    assertEquals(SIZE, rows.size());
    for (Collection<Cell> row : rows) {
      assertEquals(SIZE, row.size());
      for (Cell cell : row) {
        assertEquals(rowIndex, cell.getRowIndex());
      }
      rowIndex++;
    }
  }

  @Test
  public void test_getColumns() {
    // setup
    int columnIndex = 0;

    // execution and verification
    Collection<Collection<Cell>> columns = grid.getColumns();
    assertEquals(SIZE, columns.size());
    for (Collection<Cell> column : columns) {
      assertEquals(SIZE, columns.size());
      for (Cell cell : column) {
        assertEquals(columnIndex, cell.getColumnIndex());
      }
      columnIndex++;
    }
  }

  @Test
  public void test_getStreams() {
    // setup
    int streamIndex = 1;

    // execution and verification
    Collection<Collection<Cell>> streams = grid.getStreams();
    assertEquals(SIZE, streams.size());
    for (Collection<Cell> stream : streams) {
      assertEquals(SIZE, streams.size());
      for (Cell cell : stream) {
        assertEquals(streamIndex, cell.getStreamIndex());
      }
      streamIndex++;
    }
  }

  @Test
  public void test_cellNumbers() {
    for (int rowIndex = 0; rowIndex < SIZE; rowIndex++) {
      for (int columnIndex = 0; columnIndex < SIZE; columnIndex++) {
        int expectedNumber = NUMBERS[rowIndex][columnIndex];
        int actualNumber = grid.getCell(rowIndex, columnIndex).getNumber();
        assertEquals(expectedNumber, actualNumber);
      }
    }
  }

  @Test
  public void test_cellPossibleNumbers() {
    // setup
    Cell cell = grid.getCell(0, 0);
    Collection<Integer> possibleNumbers = asList(2, 3);

    // execution and verification
    assertTrue(cell.getPossibleNumbers().containsAll(possibleNumbers));
    cell.setNumber(1);
    assertTrue(cell.getPossibleNumbers().isEmpty());
    cell.clearNumber();
    assertTrue(cell.getPossibleNumbers().containsAll(possibleNumbers));
  }

  @Test
  public void test_getNumberOccurrences() {
    // setup
    Cell cell = grid.getCell(0, 0);

    // execution and verification
    // set number
    int number = 1;
    cell.setNumber(number);
    assertEquals(2, grid.getNumberOccurrences(number));
    assertEquals(TOTAL_OCCURRENCES - 4, grid.getNumberOccurrences(NO_NUMBER));

    // update number
    int updatedNumber = 2;
    cell.setNumber(updatedNumber);
    assertEquals(2, grid.getNumberOccurrences(updatedNumber));
    assertEquals(1, grid.getNumberOccurrences(number));
    assertEquals(TOTAL_OCCURRENCES - 4, grid.getNumberOccurrences(NO_NUMBER));

    // clear number
    cell.clearNumber();
    assertEquals(1, grid.getNumberOccurrences(updatedNumber));
    assertEquals(TOTAL_OCCURRENCES - 3, grid.getNumberOccurrences(NO_NUMBER));
  }

  @Test
  public void test_isSolved() {
    assertFalse(grid.isSolved());

    // set solution
    for (int rowIndex = 0; rowIndex < SIZE; rowIndex++) {
      for (int columnIndex = 0; columnIndex < SIZE; columnIndex++) {
        Cell cell = grid.getCell(rowIndex, columnIndex);
        int number = SOLUTION[rowIndex][columnIndex];
        cell.setNumber(number);
      }
    }

    assertTrue(grid.isSolved());
  }

  @Test
  public void test_reset() {
    // setup
    Cell cell = grid.getCell(0, 0);
    int number = 1;
    cell.setNumber(number);

    // execution
    grid.reset();

    // verification
    assertNotEquals(number, cell.getNumber());
  }

  @Test
  public void test_copyOf() {
    // execution
    Grid copy = copyOf(grid);

    // verification
    assertEquals(grid, copy);
    assertNotSame(grid, copy);
    Cell cell = grid.getCell(0, 0);
    cell.setNumber(1);
    assertNotEquals(grid, copy);
  }

}
