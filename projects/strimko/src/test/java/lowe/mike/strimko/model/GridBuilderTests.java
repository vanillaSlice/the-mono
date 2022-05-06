package lowe.mike.strimko.model;

import static lowe.mike.strimko.model.Constants.NO_NUMBER;
import static lowe.mike.strimko.model.Constants.NO_STREAM_INDEX;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import lowe.mike.strimko.model.Grid.GridBuilder;
import org.junit.jupiter.api.Test;

/**
 * {@link GridBuilder} tests.
 *
 * @author Mike Lowe
 */
public final class GridBuilderTests {

  private static final int SIZE = 4;
  private static final int TOTAL_OCCURRENCES = SIZE * SIZE;

  @Test
  public void test_constructor_invalidSize() {
    assertThrows(IllegalArgumentException.class,
        () -> new GridBuilder(-1));
  }

  @Test
  public void test_constructor() {
    // execution
    GridBuilder gridBuilder = new GridBuilder(SIZE);

    // verification
    verifyStreams(gridBuilder);
    verifyNumbers(gridBuilder);
    verifyStreamOccurrences(gridBuilder);
    verifyNumberOccurrences(gridBuilder);
  }

  private void verifyStreams(GridBuilder gridBuilder) {
    for (int rowIndex = 0; rowIndex < SIZE; rowIndex++) {
      for (int columnIndex = 0; columnIndex < SIZE; columnIndex++) {
        assertEquals(NO_STREAM_INDEX, gridBuilder.getStreamIndex(rowIndex, columnIndex));
      }
    }
  }

  private void verifyNumbers(GridBuilder gridBuilder) {
    for (int rowIndex = 0; rowIndex < SIZE; rowIndex++) {
      for (int columnIndex = 0; columnIndex < SIZE; columnIndex++) {
        assertEquals(NO_NUMBER, gridBuilder.getNumber(rowIndex, columnIndex));
      }
    }
  }

  private void verifyStreamOccurrences(GridBuilder gridBuilder) {
    for (int number = 1; number <= SIZE; number++) {
      assertEquals(0, gridBuilder.getStreamIndexOccurrences(number));
    }
    assertEquals(TOTAL_OCCURRENCES, gridBuilder.getStreamIndexOccurrences(NO_STREAM_INDEX));
  }

  private void verifyNumberOccurrences(GridBuilder gridBuilder) {
    for (int number = 1; number <= SIZE; number++) {
      assertEquals(0, gridBuilder.getNumberOccurrences(number));
    }
    assertEquals(TOTAL_OCCURRENCES, gridBuilder.getNumberOccurrences(NO_NUMBER));
  }

  @Test
  public void test_getStreamOccurrences_update() {
    // setup
    GridBuilder gridBuilder = new GridBuilder(SIZE);

    // execution and verification
    // set stream index
    int streamIndex = 1;
    gridBuilder.setStreamIndex(0, 0, streamIndex);
    assertEquals(1, gridBuilder.getStreamIndexOccurrences(streamIndex));
    assertEquals(TOTAL_OCCURRENCES - 1, gridBuilder.getStreamIndexOccurrences(NO_STREAM_INDEX));

    // update stream index
    int updatedIndex = 2;
    gridBuilder.setStreamIndex(0, 0, updatedIndex);
    assertEquals(1, gridBuilder.getStreamIndexOccurrences(updatedIndex));
    assertEquals(0, gridBuilder.getStreamIndexOccurrences(streamIndex));
    assertEquals(TOTAL_OCCURRENCES - 1, gridBuilder.getStreamIndexOccurrences(NO_STREAM_INDEX));

    // clear stream index
    gridBuilder.clearStreamIndex(0, 0);
    assertEquals(0, gridBuilder.getStreamIndexOccurrences(updatedIndex));
    assertEquals(TOTAL_OCCURRENCES, gridBuilder.getStreamIndexOccurrences(NO_STREAM_INDEX));
  }

  @Test
  public void test_getNumberOccurrences_update() {
    // setup
    GridBuilder gridBuilder = new GridBuilder(SIZE);

    // execution and verification
    // set number
    int number = 1;
    gridBuilder.setNumber(0, 0, number);
    assertEquals(1, gridBuilder.getNumberOccurrences(number));
    assertEquals(TOTAL_OCCURRENCES - 1, gridBuilder.getNumberOccurrences(NO_NUMBER));

    // update number
    int updatedNumber = 2;
    gridBuilder.setNumber(0, 0, updatedNumber);
    assertEquals(1, gridBuilder.getNumberOccurrences(updatedNumber));
    assertEquals(0, gridBuilder.getNumberOccurrences(number));
    assertEquals(TOTAL_OCCURRENCES - 1, gridBuilder.getNumberOccurrences(NO_NUMBER));

    // clear number
    gridBuilder.clearNumber(0, 0);
    assertEquals(0, gridBuilder.getNumberOccurrences(updatedNumber));
    assertEquals(TOTAL_OCCURRENCES, gridBuilder.getNumberOccurrences(NO_NUMBER));
  }

  @Test
  public void test_build_invalidStreams() {
    assertThrows(IllegalArgumentException.class,
        () -> new GridBuilder(SIZE).build());
  }

  @Test
  public void test_build() {
    // setup
    GridBuilder gridBuilder = newGridBuilder();

    // execution
    gridBuilder.build();
  }

  private GridBuilder newGridBuilder() {
    int[][] streams = {{1, 2, 2, 1}, {2, 1, 1, 2}, {3, 4, 4, 3}, {4, 3, 3, 4}};
    int[][] numbers = {{1, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
    return new GridBuilder(SIZE).setStreams(streams).setNumbers(numbers);
  }

  @Test
  public void test_clearStreamIndexes() {
    // setup
    GridBuilder gridBuilder = newGridBuilder();

    // execution
    gridBuilder.clearStreamIndexes();

    // verification
    verifyStreams(gridBuilder);
  }

  @Test
  public void test_clearNumbers() {
    // setup
    GridBuilder gridBuilder = newGridBuilder();

    // execution
    gridBuilder.clearNumbers();

    // verification
    verifyNumbers(gridBuilder);
  }

}
