package lowe.mike.strimko.model;

import static java.util.Arrays.asList;
import static lowe.mike.strimko.model.Cell.copyOf;
import static lowe.mike.strimko.model.Cell.newInstance;
import static lowe.mike.strimko.model.Constants.NO_NUMBER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import org.junit.jupiter.api.Test;

/**
 * {@link Cell} tests.
 *
 * @author Mike Lowe
 */
public final class CellTests {

  private static final int ROW_INDEX = 1;
  private static final int COLUMN_INDEX = 2;
  private static final int STREAM_INDEX = 3;
  private static final int NUMBER = 4;
  private static final Collection<Integer> POSSIBLE_NUMBERS = asList(1, 2, 3, 5);

  @Test
  public void test_newInstance_noNumber() {
    // setup
    Cell cell = getNewInstance();

    // verification
    assertEquals(ROW_INDEX, cell.getRowIndex());
    assertEquals(COLUMN_INDEX, cell.getColumnIndex());
    assertEquals(STREAM_INDEX, cell.getStreamIndex());
    assertEquals(NO_NUMBER, cell.getNumber());
    assertFalse(cell.isSet());
    assertTrue(cell.getPossibleNumbers().containsAll(POSSIBLE_NUMBERS));
    assertFalse(cell.isLocked());
  }

  private Cell getNewInstance() {
    return newInstance(ROW_INDEX, COLUMN_INDEX, STREAM_INDEX, NO_NUMBER, POSSIBLE_NUMBERS);
  }

  @Test
  public void test_newInstance_withNumber() {
    // setup
    Cell cell = getNewInstanceWithNumber();

    // verification
    assertEquals(NUMBER, cell.getNumber());
    assertTrue(cell.isSet());
    assertTrue(cell.isLocked());
  }

  private Cell getNewInstanceWithNumber() {
    return newInstance(ROW_INDEX, COLUMN_INDEX, STREAM_INDEX, NUMBER, POSSIBLE_NUMBERS);
  }

  @Test
  public void test_copyOf() {
    // setup
    Cell cell = getNewInstanceWithNumber();

    // execution
    Cell copy = copyOf(cell);

    // verification
    assertEquals(cell, copy);
    assertNotSame(cell, copy);
    cell.getPossibleNumbers().add(4);
    assertNotEquals(cell, copy);
  }

  @Test
  public void test_setNumber() {
    // setup
    Cell cell = getNewInstance();

    // execution
    cell.setNumber(NUMBER);

    // verification
    assertEquals(NUMBER, cell.getNumber());
    assertTrue(cell.isSet());
  }

  @Test
  public void test_setNumber_locked() {
    // setup
    Cell cell = getNewInstanceWithNumber();
    int newNumber = 1;

    // execution
    cell.setNumber(newNumber);

    // verification
    assertNotEquals(newNumber, cell.getNumber());
    assertEquals(NUMBER, cell.getNumber());
  }

}
