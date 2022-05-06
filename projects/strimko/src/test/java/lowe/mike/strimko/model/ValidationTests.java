package lowe.mike.strimko.model;

import static lowe.mike.strimko.model.Constants.MAX_GRID_SIZE;
import static lowe.mike.strimko.model.Constants.MIN_GRID_SIZE;
import static lowe.mike.strimko.model.Constants.NO_NUMBER;
import static lowe.mike.strimko.model.Constants.NO_STREAM_INDEX;
import static lowe.mike.strimko.model.Validation.checkGridSize;
import static lowe.mike.strimko.model.Validation.checkNumber;
import static lowe.mike.strimko.model.Validation.checkStreamIndex;
import static lowe.mike.strimko.model.Validation.checkStreams;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import lowe.mike.strimko.model.Grid.GridBuilder;
import org.junit.jupiter.api.Test;

/**
 * {@link Validation} tests.
 *
 * @author Mike Lowe
 */
public final class ValidationTests {

  @Test
  public void test_checkGridSize_lessThanMin() {
    test_checkGridSize_invalid(MIN_GRID_SIZE - 1);
  }

  private void test_checkGridSize_invalid(int size) {
    assertThrows(IllegalArgumentException.class,
        () -> checkGridSize(size));
  }

  @Test
  public void test_checkGridSize_greaterThanMax() {
    test_checkGridSize_invalid(MAX_GRID_SIZE + 1);
  }

  /**
   * Expect no exceptions to ensure validity.
   */
  @Test
  public void test_checkGridSize() {
    checkGridSize(MIN_GRID_SIZE);
    checkGridSize(MAX_GRID_SIZE);
    checkGridSize(MAX_GRID_SIZE - MIN_GRID_SIZE);
  }

  @Test
  public void test_checkStreamIndex_lessThanMin() {
    test_checkStreamIndex_invalid(NO_STREAM_INDEX - 1, 3);
  }

  private void test_checkStreamIndex_invalid(int streamIndex, int size) {
    assertThrows(IllegalArgumentException.class,
        () -> checkStreamIndex(streamIndex, size));
  }

  @Test
  public void test_checkStreamIndex_greaterThanMax() {
    test_checkStreamIndex_invalid(4, 3);
  }

  /**
   * Expect no exceptions to ensure validity.
   */
  @Test
  public void test_checkStreamIndex() {
    int size = 3;
    checkStreamIndex(NO_STREAM_INDEX, size);
    checkStreamIndex(size, size);
    checkStreamIndex(size - 1, size);
  }

  @Test
  public void test_checkNumber_lessThanMin() {
    test_checkNumber_invalid(NO_NUMBER - 1, 3);
  }

  private void test_checkNumber_invalid(int number, int size) {
    assertThrows(IllegalArgumentException.class,
        () -> checkNumber(number, size));
  }

  @Test
  public void test_checkNumber_greaterThanMax() {
    test_checkNumber_invalid(4, 3);
  }

  /**
   * Expect no exceptions to ensure validity.
   */
  @Test
  public void test_checkNumber() {
    int size = 3;
    checkNumber(NO_NUMBER, size);
    checkNumber(size, size);
    checkNumber(size - 1, size);
  }

  @Test
  public void test_checkStreams_unsetStreams() {
    // setup
    int[][] streams = new int[3][3];
    String message = "All cells must belong to a stream";

    // execution
    test_checkStreams_invalid(streams, message);
  }

  private void test_checkStreams_invalid(int[][] streams, String message) {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> checkStreams(streams));
    assertEquals(message, exception.getMessage());
  }

  @Test
  public void test_checkStreams_unevenStreams() {
    // setup
    int[][] streams = {{1, 1, 1}, {1, 1, 1}, {2, 2, 3}};
    String message = "Each stream does not contain an equal number of cells";

    // execution
    test_checkStreams_invalid(streams, message);
  }

  @Test
  public void test_checkStreams_nonConnectedStreams() {
    // setup
    int[][] streams = {{3, 1, 1}, {2, 2, 2}, {1, 3, 3}};
    String message = "Cells in a stream must be connected";

    // execution
    test_checkStreams_invalid(streams, message);
  }

  /**
   * Expect no exceptions to ensure validity.
   */
  @Test
  public void test_checkStreams() {
    // setup
    int[][] streams = {{1, 2, 1}, {2, 1, 2}, {3, 3, 3}};

    // execution
    checkStreams(streams);
  }

  @Test
  public void test_checkUniqueNumbersInGroups_invalidRow() {
    int[][] numbers = {{1, 1, 0}, {0, 0, 0}, {0, 0, 0}};
    String message = "Row contains 1 more than once";
    test_checkUniqueNumbersInGroups_invalid(numbers, message);
  }

  @Test
  public void test_checkUniqueNumbersInGroups_invalidColumn() {
    int[][] numbers = {{1, 0, 0}, {0, 2, 0}, {0, 2, 0}};
    String message = "Column contains 2 more than once";
    test_checkUniqueNumbersInGroups_invalid(numbers, message);
  }

  @Test
  public void test_checkUniqueNumbersInGroups_invalidStream() {
    int[][] numbers = {{1, 0, 0}, {0, 0, 3}, {0, 3, 0}};
    String message = "Stream contains 3 more than once";
    test_checkUniqueNumbersInGroups_invalid(numbers, message);
  }

  private void test_checkUniqueNumbersInGroups_invalid(int[][] numbers, String message) {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> newGridBuilder().setNumbers(numbers).build());
    assertEquals(message, exception.getMessage());
  }

  private GridBuilder newGridBuilder() {
    int[][] streams = {{1, 1, 2}, {1, 2, 3}, {2, 3, 3}};
    return new GridBuilder(3).setStreams(streams);
  }

  /**
   * Expect no exceptions to ensure validity.
   */
  @Test
  public void test_checkUniqueNumbersInGroups() {
    // setup
    int[][] numbers = {{1, 0, 0}, {0, 0, 2}, {0, 3, 0}};

    // execution
    newGridBuilder().setNumbers(numbers).build();
  }

}
