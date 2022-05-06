package lowe.mike.strimko.model;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.HashMultiset.create;
import static lowe.mike.strimko.model.Constants.MAX_GRID_SIZE;
import static lowe.mike.strimko.model.Constants.MIN_GRID_SIZE;
import static lowe.mike.strimko.model.Constants.NO_NUMBER;
import static lowe.mike.strimko.model.Constants.NO_STREAM_INDEX;

import com.google.common.collect.Multiset;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * {@code Validation} provides methods for validating certain aspects in the data model.
 *
 * <p>Instances of {@code Validation} cannot be created.
 *
 * @author Mike Lowe
 */
final class Validation {

  // don't want instances
  private Validation() {
  }

  /**
   * Validates the size of a {@link Grid} by ensuring that it is between the minimum and maximum
   * sizes defined in {@link Constants}.
   *
   * @param size the size of the {@link Grid}
   * @throws IllegalArgumentException if the size is invalid
   */
  static void checkGridSize(int size) {
    checkArgument(size >= MIN_GRID_SIZE && size <= MAX_GRID_SIZE,
        "Grid size must be between " + MIN_GRID_SIZE + " and " + MAX_GRID_SIZE + " but was "
            + size);
  }

  /**
   * Validates a stream index by ensuring that it is between the minimum stream index and the size
   * of the {@link Grid}.
   *
   * @param streamIndex the stream index
   * @param size the size of the {@link Grid}
   * @throws IllegalArgumentException if the stream index is invalid
   */
  static void checkStreamIndex(int streamIndex, int size) {
    checkArgument(streamIndex >= NO_STREAM_INDEX && streamIndex <= size,
        "Stream index must be between " + NO_STREAM_INDEX + " and " + size + " but was "
            + streamIndex);
  }

  /**
   * Validates a number by ensuring that it is between the minimum number and the size of the {@link
   * Grid}.
   *
   * @param number the number
   * @param size the size of the {@link Grid}
   * @throws IllegalArgumentException if the number is invalid
   */
  static void checkNumber(int number, int size) {
    checkArgument(number >= NO_NUMBER && number <= size,
        "Number must be between " + NO_NUMBER + " and " + size + " but was " + number);
  }

  /**
   * Validates the array of stream indexes.
   *
   * <p>Validation is carried out by ensuring that:
   * <li>each {@link Cell} has a stream</li>
   * <li>there are equal number of {@link Cell}s in each stream</li>
   * <li>{@link Cell}s in a stream are all joined together</li>
   *
   * @param streams the array of stream indexes
   * @throws IllegalArgumentException if the array of stream indexes does not meet the desired
   *     conditions
   */
  static void checkStreams(int[][] streams) {
    Multiset<Integer> streamsCount = getNumberOfCellsInEachStream(streams);

    checkAllCellsBelongToAStream(streamsCount);

    int size = streams.length;

    checkEqualNumberOfCellsInEachStream(streamsCount, size);
    checkCellsInStreamsAreJoined(streams, size);
  }

  private static Multiset<Integer> getNumberOfCellsInEachStream(int[][] streams) {
    Multiset<Integer> streamsCount = create();

    for (int[] row : streams) {
      for (int stream : row) {
        streamsCount.add(stream);
      }
    }

    return streamsCount;
  }

  private static void checkAllCellsBelongToAStream(Multiset<Integer> streamsCount) {
    checkArgument(streamsCount.count(NO_STREAM_INDEX) == 0, "All cells must belong to a stream");
  }

  private static void checkEqualNumberOfCellsInEachStream(Multiset<Integer> streamsCount,
      int size) {
    for (int stream = 1; stream <= size; stream++) {
      checkArgument(streamsCount.count(stream) == size,
          "Each stream does not contain an equal number of cells");
    }
  }

  private static void checkCellsInStreamsAreJoined(int[][] streams, int size) {
    boolean[][] visited = new boolean[size][size];

    for (int rowIndex = 0; rowIndex < size; rowIndex++) {
      for (int columnIndex = 0; columnIndex < size; columnIndex++) {
        checkArgument(
            visited[rowIndex][columnIndex]
                || cellsInStreamAreJoined(streams, size, visited, rowIndex, columnIndex),
            "Cells in a stream must be connected");
      }
    }
  }

  private static boolean cellsInStreamAreJoined(int[][] streams, int size, boolean[][] visited,
      int rowIndex,
      int columnIndex) {
    int stream = streams[rowIndex][columnIndex];
    Multiset<Integer> connections = create();
    countStreamConnections(streams, size, visited, rowIndex, columnIndex, stream, connections);
    return connections.count(stream) == size;
  }

  private static void countStreamConnections(int[][] streams, int size, boolean[][] visited,
      int rowIndex,
      int columnIndex, int streamIndex, Multiset<Integer> connections) {
    if (streams[rowIndex][columnIndex] == streamIndex && !visited[rowIndex][columnIndex]) {
      visited[rowIndex][columnIndex] = true;
      connections.add(streamIndex);

      // check if connected to above left cell
      if (continueVisiting(streams, visited, rowIndex - 1, columnIndex - 1, streamIndex, size)) {
        countStreamConnections(streams, size, visited, rowIndex - 1, columnIndex - 1, streamIndex,
            connections);
      }
      // check if connected to above cell
      if (continueVisiting(streams, visited, rowIndex - 1, columnIndex, streamIndex, size)) {
        countStreamConnections(streams, size, visited, rowIndex - 1, columnIndex, streamIndex,
            connections);
      }
      // check if connected to above right cell
      if (continueVisiting(streams, visited, rowIndex - 1, columnIndex + 1, streamIndex, size)) {
        countStreamConnections(streams, size, visited, rowIndex - 1, columnIndex + 1, streamIndex,
            connections);
      }
      // check if connected to right cell
      if (continueVisiting(streams, visited, rowIndex, columnIndex + 1, streamIndex, size)) {
        countStreamConnections(streams, size, visited, rowIndex, columnIndex + 1, streamIndex,
            connections);
      }
      // check if connected to below right cell
      if (continueVisiting(streams, visited, rowIndex + 1, columnIndex + 1, streamIndex, size)) {
        countStreamConnections(streams, size, visited, rowIndex + 1, columnIndex + 1, streamIndex,
            connections);
      }
      // check if connected to below cell
      if (continueVisiting(streams, visited, rowIndex + 1, columnIndex, streamIndex, size)) {
        countStreamConnections(streams, size, visited, rowIndex + 1, columnIndex, streamIndex,
            connections);
      }
      // check if connected to below left cell
      if (continueVisiting(streams, visited, rowIndex + 1, columnIndex - 1, streamIndex, size)) {
        countStreamConnections(streams, size, visited, rowIndex + 1, columnIndex - 1, streamIndex,
            connections);
      }
      // check if connected to left cell
      if (continueVisiting(streams, visited, rowIndex, columnIndex - 1, streamIndex, size)) {
        countStreamConnections(streams, size, visited, rowIndex, columnIndex - 1, streamIndex,
            connections);
      }
    }
  }

  private static boolean continueVisiting(int[][] streams, boolean[][] visited, int rowIndex,
      int columnIndex,
      int streamIndex, int size) {
    if (rowIndex < 0 || columnIndex < 0 || rowIndex >= size || columnIndex >= size) {
      return false;
    }
    return streams[rowIndex][columnIndex] == streamIndex && !visited[rowIndex][columnIndex];
  }

  /**
   * Validates a {@link Grid} by ensuring that each row, column and stream contains unique numbers.
   *
   * @param grid the {@link Grid} to check
   * @throws IllegalArgumentException if any row, column or stream does not contain unique
   *     numbers
   */
  static void checkUniqueNumbersInGroups(Grid grid) {
    checkUniqueNumbersInGroups(grid.getRows(), "Row");
    checkUniqueNumbersInGroups(grid.getColumns(), "Column");
    checkUniqueNumbersInGroups(grid.getStreams(), "Stream");
  }

  private static void checkUniqueNumbersInGroups(Collection<Collection<Cell>> groups,
      String groupName) {
    for (Collection<Cell> group : groups) {
      checkUniqueNumbersInGroup(group, groupName);
    }
  }

  private static void checkUniqueNumbersInGroup(Collection<Cell> group, String groupName) {
    Set<Integer> foundNumbers = new HashSet<>();
    for (Cell cell : group) {
      if (cell.isSet()) {
        int number = cell.getNumber();
        checkArgument(!foundNumbers.contains(number),
            groupName + " contains " + number + " more than once");
        foundNumbers.add(number);
      }
    }
  }

}
