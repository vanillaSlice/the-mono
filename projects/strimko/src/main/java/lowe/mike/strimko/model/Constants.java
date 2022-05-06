package lowe.mike.strimko.model;

import static java.lang.System.getProperty;

import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * {@code Constants} defines constant values that are useful for the data model.
 *
 * <p>Instances of {@code Constants} cannot be created.
 *
 * @author Mike Lowe
 */
public final class Constants {

  // don't want instances
  private Constants() {
  }

  public static final int NO_NUMBER = 0;
  public static final int NO_STREAM_INDEX = 0;
  public static final int MIN_STRIMKO_SIZE = 3;
  public static final int MAX_STRIMKO_SIZE = 7;
  public static final int SUDOKU_SIZE = 9;
  public static final int MIN_GRID_SIZE = MIN_STRIMKO_SIZE;
  public static final int MAX_GRID_SIZE = SUDOKU_SIZE;
  public static final String USER_PUZZLE_DIRECTORY = getProperty("user.home") + "/.strimko/puzzles";
  public static final String PUZZLES_RESOURCE_NAME = "/puzzles.txt";

  /**
   * Returns a {@link Collection} of possible sizes a Strimko puzzle can be.
   *
   * @return a {@link Collection} of possible sizes a Strimko puzzle can be
   */
  public static Collection<Integer> getStrimkoPuzzleSizes() {
    Collection<Integer> sizes = new LinkedHashSet<>();
    for (int number = MIN_STRIMKO_SIZE; number <= MAX_STRIMKO_SIZE; number++) {
      sizes.add(number);
    }
    return sizes;
  }

  /**
   * Returns the {@code int} array representing Sudoku streams.
   *
   * @return the {@code int} array representing Sudoku streams
   */
  public static int[][] getSudokuStreams() {
    return new int[][]{{1, 1, 1, 2, 2, 2, 3, 3, 3}, {1, 1, 1, 2, 2, 2, 3, 3, 3},
        {1, 1, 1, 2, 2, 2, 3, 3, 3}, {4, 4, 4, 5, 5, 5, 6, 6, 6}, {4, 4, 4, 5, 5, 5, 6, 6, 6},
        {4, 4, 4, 5, 5, 5, 6, 6, 6}, {7, 7, 7, 8, 8, 8, 9, 9, 9}, {7, 7, 7, 8, 8, 8, 9, 9, 9},
        {7, 7, 7, 8, 8, 8, 9, 9, 9}};
  }

}
