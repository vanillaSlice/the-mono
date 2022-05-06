package lowe.mike.strimko.model;

import static com.google.common.collect.LinkedHashMultimap.create;
import static java.util.Arrays.deepEquals;
import static java.util.Objects.hash;
import static lowe.mike.strimko.model.Cell.newInstance;
import static lowe.mike.strimko.model.Constants.NO_NUMBER;
import static lowe.mike.strimko.model.Constants.NO_STREAM_INDEX;
import static lowe.mike.strimko.model.Validation.checkGridSize;
import static lowe.mike.strimko.model.Validation.checkNumber;
import static lowe.mike.strimko.model.Validation.checkStreamIndex;
import static lowe.mike.strimko.model.Validation.checkStreams;
import static lowe.mike.strimko.model.Validation.checkUniqueNumbersInGroups;

import com.google.common.collect.Multimap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;

/**
 * {@code Grid} instances are intended to provide information about grids which make up {@link
 * Puzzle}s.
 *
 * @author Mike Lowe
 */
public final class Grid {

  private final int size;
  private final Collection<Integer> rangeOfPossibleNumbers = new TreeSet<>();
  private final List<Cell> cells = new ArrayList<>();

  /**
   * Used for easy iteration over cells so it's much simpler to iterate over a specific row, column
   * or stream.
   */
  private final Multimap<Integer, Cell> rows = create();
  private final Multimap<Integer, Cell> columns = create();
  private final Multimap<Integer, Cell> streams = create();

  private final Map<Integer, ReadOnlyIntegerWrapper> numberOccurrences = new HashMap<>();
  private final ReadOnlyBooleanWrapper isSolved = new ReadOnlyBooleanWrapper();

  /**
   * Creates a new {@code Grid} instance from an existing instance.
   *
   * @param grid the existing {@code Grid} instance
   * @return a new {@code Grid} instance copied from an existing instance
   */
  public static Grid copyOf(Grid grid) {
    return new Grid(grid);
  }

  private Grid(int size, int[][] streams, int[][] numbers) {
    this.size = size;
    initialize(streams, numbers);
  }

  private Grid(Grid grid) {
    this.size = grid.getSize();
    initializeCopy(grid);
  }

  ////////////////////
  // INITIALIZATION //
  ////////////////////

  private void initialize(int[][] streams, int[][] numbers) {
    initializeRangeOfPossibleNumbers();
    initializeCells(streams, numbers);
    initializeIsSolved();
    initializeNumberOccurrences();
    initializePossibleNumbers();
    checkUniqueNumbersInGroups(this);
  }

  private void initializeRangeOfPossibleNumbers() {
    for (int number = 1; number <= size; number++) {
      rangeOfPossibleNumbers.add(number);
    }
  }

  private void initializeCells(int[][] streams, int[][] numbers) {
    for (int rowIndex = 0; rowIndex < size; rowIndex++) {
      for (int columnIndex = 0; columnIndex < size; columnIndex++) {
        int streamIndex = streams[rowIndex][columnIndex];
        int number = numbers[rowIndex][columnIndex];
        Cell cell = newInstance(rowIndex, columnIndex, streamIndex, number, rangeOfPossibleNumbers);
        addCellToCollections(cell, rowIndex, columnIndex, streamIndex);
        addCellChangeListener(cell);
      }
    }
  }

  private void addCellToCollections(Cell cell, int rowIndex, int columnIndex, int streamIndex) {
    cells.add(cell);
    rows.put(rowIndex, cell);
    columns.put(columnIndex, cell);
    streams.put(streamIndex, cell);
  }

  private void addCellChangeListener(Cell cell) {
    cell.numberProperty().addListener((observable, oldValue, newValue) -> {
      int oldNumber = (int) oldValue;
      int newNumber = (int) newValue;
      updateIsSolved();
      updateNumberOccurrences(oldNumber, newNumber);
      updatePossibleNumbers(cell, oldNumber);
    });
  }

  private void initializeIsSolved() {
    updateIsSolved();
  }

  private void updateIsSolved() {
    isSolved.set(checkIsSolved());
  }

  private boolean checkIsSolved() {
    return containsAllNumbers(getRows()) && containsAllNumbers(getColumns()) && containsAllNumbers(
        getStreams());
  }

  private boolean containsAllNumbers(Collection<Collection<Cell>> group) {
    for (Collection<Cell> cells : group) {
      Collection<Integer> numbersInGroup = new HashSet<>();
      for (Cell cell : cells) {
        int number = cell.getNumber();
        if (!cell.isSet() || number > size || numbersInGroup.contains(number)) {
          return false;
        }
        numbersInGroup.add(number);
      }
    }
    return true;
  }

  private void initializeNumberOccurrences() {
    for (int number : rangeOfPossibleNumbers) {
      numberOccurrences.put(number, new ReadOnlyIntegerWrapper());
    }
    numberOccurrences.put(NO_NUMBER, new ReadOnlyIntegerWrapper());
    countNumberOccurrences();
  }

  private void countNumberOccurrences() {
    for (Cell cell : getCells()) {
      int number = cell.getNumber();
      ReadOnlyIntegerWrapper numberOccurrence = numberOccurrences.get(number);
      numberOccurrence.set(numberOccurrence.get() + 1);
    }
  }

  private void updateNumberOccurrences(int oldNumber, int newNumber) {
    ReadOnlyIntegerWrapper oldNumberOccurrence = numberOccurrences.get(oldNumber);
    ReadOnlyIntegerWrapper newNumberOccurrence = numberOccurrences.get(newNumber);
    oldNumberOccurrence.set(oldNumberOccurrence.get() - 1);
    newNumberOccurrence.set(newNumberOccurrence.get() + 1);
  }

  private void initializePossibleNumbers() {
    updatePossibleNumbersInAllCells();
  }

  private void updatePossibleNumbersInAllCells() {
    for (Cell cell : getCells()) {
      updatePossibleNumbersInSameGroups(cell);
    }
  }

  private void updatePossibleNumbersInSameGroups(Cell cell) {
    if (cell.isSet()) {
      cell.getPossibleNumbers().clear();
      int number = cell.getNumber();
      int rowIndex = cell.getRowIndex();
      int columnIndex = cell.getColumnIndex();
      int streamIndex = cell.getStreamIndex();
      removePossibleNumberFromCellsInGroup(number, getRow(rowIndex));
      removePossibleNumberFromCellsInGroup(number, getColumn(columnIndex));
      removePossibleNumberFromCellsInGroup(number, getStream(streamIndex));
    }
  }

  private void removePossibleNumberFromCellsInGroup(int possible, Collection<Cell> group) {
    for (Cell cell : group) {
      cell.getPossibleNumbers().remove(possible);
    }
  }

  private void updatePossibleNumbers(Cell cell, int oldNumber) {
    if (oldNumber == NO_NUMBER) {
      updatePossibleNumbersInSameGroups(cell);
    } else {
      resetPossibleNumbersInAllCells();
      updatePossibleNumbersInAllCells();
    }
  }

  private void resetPossibleNumbersInAllCells() {
    for (Cell cell : getCells()) {
      cell.getPossibleNumbers().addAll(rangeOfPossibleNumbers);
    }
  }

  private void initializeCopy(Grid grid) {
    initializeRangeOfPossibleNumbers();
    initializeCopyOfCells(grid);
    initializeIsSolved();
    initializeNumberOccurrences();
  }

  private void initializeCopyOfCells(Grid grid) {
    for (Cell cell : grid.getCells()) {
      Cell copy = Cell.copyOf(cell);
      int rowIndex = copy.getRowIndex();
      int columnIndex = copy.getColumnIndex();
      int streamIndex = copy.getStreamIndex();
      addCellToCollections(copy, rowIndex, columnIndex, streamIndex);
      addCellChangeListener(copy);
    }
  }

  ///////////////////////////
  // END OF INITIALIZATION //
  ///////////////////////////

  /**
   * Returns the size of this {@code Grid}.
   *
   * @return the size of this {@code Grid}
   */
  public int getSize() {
    return size;
  }

  /**
   * Returns the {@link Cell} at the position indicated by the {@code rowIndex} and {@code
   * columnIndex}.
   *
   * @param rowIndex row index of the {@link Cell}
   * @param columnIndex column index of the {@link Cell}
   * @return the {@link Cell} at the position indicated by the {@code rowIndex} and {@code
   *     columnIndex}
   */
  public Cell getCell(int rowIndex, int columnIndex) {
    int position = rowIndex * size + columnIndex;
    return cells.get(position);
  }

  /**
   * Returns the {@link Cell} at the given {@link Position}.
   *
   * @param position the {@link Position} of the {@link Cell}
   * @return the {@link Cell} at the given {@link Position}
   */
  public Cell getCell(Position position) {
    return getCell(position.getRowIndex(), position.getColumnIndex());
  }

  /**
   * Returns the {@link Collection} of all {@link Cell}s.
   *
   * @return the {@link Collection} of all {@link Cell}s
   */
  public Collection<Cell> getCells() {
    return cells;
  }

  /**
   * Returns the {@link Collection} of rows.
   *
   * @return the {@link Collection} of rows
   */
  public Collection<Collection<Cell>> getRows() {
    return rows.asMap().values();
  }

  /**
   * Returns the {@link Collection} of {@link Cell}s with the given {@code rowIndex}.
   *
   * @param rowIndex row index to get {@link Cell}s from
   * @return the {@link Collection} of {@link Cell}s with the given {@code rowIndex}
   */
  public Collection<Cell> getRow(int rowIndex) {
    return rows.get(rowIndex);
  }

  /**
   * Returns the {@link Collection} of columns.
   *
   * @return the {@link Collection} of columns
   */
  public Collection<Collection<Cell>> getColumns() {
    return columns.asMap().values();
  }

  /**
   * Returns the {@link Collection} of {@link Cell}s with the given {@code columnIndex}.
   *
   * @param columnIndex column index to get {@link Cell}s from
   * @return the {@link Collection} of {@link Cell}s with the given {@code columnIndex}
   */
  public Collection<Cell> getColumn(int columnIndex) {
    return columns.get(columnIndex);
  }

  /**
   * Returns the {@link Collection} of streams.
   *
   * @return the {@link Collection} of streams
   */
  public Collection<Collection<Cell>> getStreams() {
    return streams.asMap().values();
  }

  /**
   * Returns the {@link Collection} of {@link Cell}s with the given {@code streamIndex}.
   *
   * @param streamIndex stream index to get {@link Cell}s from
   * @return the {@link Collection} of {@link Cell}s with the given {@code streamIndex}
   */
  public Collection<Cell> getStream(int streamIndex) {
    return streams.get(streamIndex);
  }

  /**
   * Returns the total occurrences of a number in this {@code Grid}.
   *
   * @param number the number to get occurrences for
   * @return the total occurrences of a number in this {@code Grid}
   */
  public int getNumberOccurrences(int number) {
    return numberOccurrenceProperty(number).get();
  }

  /**
   * Returns the {@link ReadOnlyIntegerProperty} indicating the number of times a number occurs in
   * this {@code Grid}.
   *
   * @param number the number to return the property for
   * @return the {@link ReadOnlyIntegerProperty} indicating the number of times a number occurs in
   *     this {@code Grid}
   */
  public ReadOnlyIntegerProperty numberOccurrenceProperty(int number) {
    return numberOccurrences.get(number).getReadOnlyProperty();
  }

  /**
   * Returns {@code true} if this {@code Grid} is solved; {@code false} otherwise.
   *
   * @return {@code true} if this {@code Grid} is solved; {@code false} otherwise
   */
  public boolean isSolved() {
    return isSolved.get();
  }

  /**
   * Returns the {@link ReadOnlyBooleanProperty} indicating if this {@code Grid} has been solved.
   *
   * @return the {@link ReadOnlyBooleanProperty} indicating if this {@code Grid} has been solved
   */
  public ReadOnlyBooleanProperty isSolvedProperty() {
    return isSolved.getReadOnlyProperty();
  }

  /**
   * Resets this {@code Grid} by clearing numbers from {@link Cell}s which are not locked.
   */
  public void reset() {
    for (Collection<Cell> row : getRows()) {
      for (Cell cell : row) {
        cell.clearNumber();
      }
    }
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + cells.hashCode();
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Grid other = (Grid) obj;
    return cells.equals(other.cells);
  }

  /**
   * {@code GridBuilder} instances are used to create {@link Grid} objects. Validation is carried
   * out on the streams when the {@link #build()} method is called to ensure that a valid {@link
   * Grid} is created.
   *
   * @author Mike Lowe
   */
  public static final class GridBuilder {

    private final int size;
    private final int[][] streams;
    private final int[][] numbers;
    private final ReadOnlyIntegerWrapper[][] streamProperties;
    private final ReadOnlyIntegerWrapper[][] numberProperties;
    private final Map<Integer, ReadOnlyIntegerWrapper> streamIndexOccurrences = new HashMap<>();
    private final Map<Integer, ReadOnlyIntegerWrapper> numberOccurrences = new HashMap<>();

    /**
     * Creates a new {@code GridBuilder} instance given the size of the {@link Grid} to create.
     *
     * @param size the size of the {@link Grid} to create
     * @throws IllegalArgumentException if the size passed in is invalid
     */
    public GridBuilder(int size) {
      this.size = size;
      checkGridSize(this.size);
      this.streams = new int[this.size][this.size];
      this.numbers = new int[this.size][this.size];
      this.streamProperties = new ReadOnlyIntegerWrapper[this.size][this.size];
      this.numberProperties = new ReadOnlyIntegerWrapper[this.size][this.size];
      initialize();
    }

    private void initialize() {
      initializeProperties(streamProperties, streamIndexOccurrences);
      initializeProperties(numberProperties, numberOccurrences);
      initializeOccurrences(streamIndexOccurrences, NO_STREAM_INDEX);
      initializeOccurrences(numberOccurrences, NO_NUMBER);
    }

    private void initializeProperties(ReadOnlyIntegerWrapper[][] properties,
        Map<Integer, ReadOnlyIntegerWrapper> occurrences) {
      for (int rowIndex = 0; rowIndex < size; rowIndex++) {
        for (int columnIndex = 0; columnIndex < size; columnIndex++) {
          ReadOnlyIntegerWrapper property = new ReadOnlyIntegerWrapper();
          addPropertyChangeListener(property, occurrences);
          properties[rowIndex][columnIndex] = property;
        }
      }
    }

    private void addPropertyChangeListener(ReadOnlyIntegerWrapper property,
        Map<Integer, ReadOnlyIntegerWrapper> occurrences) {
      property.addListener(
          (observable, oldValue, newValue) -> updateOccurrences(occurrences, (int) oldValue,
              (int) newValue));
    }

    private void initializeOccurrences(Map<Integer, ReadOnlyIntegerWrapper> occurrences,
        int noOccurrences) {
      for (int number = 1; number <= size; number++) {
        occurrences.put(number, new ReadOnlyIntegerWrapper());
      }
      occurrences.put(noOccurrences, new ReadOnlyIntegerWrapper(size * size));
    }

    private void updateOccurrences(Map<Integer, ReadOnlyIntegerWrapper> occurrences, int oldValue,
        int newValue) {
      ReadOnlyIntegerWrapper oldOccurrence = occurrences.get(oldValue);
      ReadOnlyIntegerWrapper newOccurence = occurrences.get(newValue);
      oldOccurrence.set(oldOccurrence.get() - 1);
      newOccurence.set(newOccurence.get() + 1);
    }

    /**
     * Returns the size of this {@code GridBuilder}.
     *
     * @return the size of this {@code GridBuilder}
     */
    public int getSize() {
      return size;
    }

    /**
     * Returns the stream index at the given row index and column index.
     *
     * @param rowIndex the row index
     * @param columnIndex the column index
     * @return the stream index at the given row index and column index
     */
    public int getStreamIndex(int rowIndex, int columnIndex) {
      return streams[rowIndex][columnIndex];
    }

    /**
     * Sets the stream index.
     *
     * @param rowIndex the row index
     * @param columnIndex the column index
     * @param streamIndex the stream index
     * @return this {@code GridBuilder}
     * @throws IllegalArgumentException if the stream index is invalid
     */
    public GridBuilder setStreamIndex(int rowIndex, int columnIndex, int streamIndex) {
      checkStreamIndex(streamIndex, size);
      streams[rowIndex][columnIndex] = streamIndex;
      streamProperties[rowIndex][columnIndex].set(streamIndex);
      return this;
    }

    /**
     * Sets the array of stream indexes.
     *
     * @param streams array of stream indexes
     * @return this {@code GridBuilder}
     * @throws IllegalArgumentException if {@code streams} contains an invalid stream index
     */
    public GridBuilder setStreams(int[][] streams) {
      for (int rowIndex = 0; rowIndex < size; rowIndex++) {
        for (int columnIndex = 0; columnIndex < size; columnIndex++) {
          int streamIndex = streams[rowIndex][columnIndex];
          setStreamIndex(rowIndex, columnIndex, streamIndex);
        }
      }
      return this;
    }

    /**
     * Returns the {@link ReadOnlyIntegerProperty} indicating the stream index.
     *
     * @param rowIndex the row index
     * @param columnIndex the stream index
     * @return the {@link ReadOnlyIntegerProperty} indicating the stream index
     */
    public ReadOnlyIntegerProperty streamIndexProperty(int rowIndex, int columnIndex) {
      return streamProperties[rowIndex][columnIndex].getReadOnlyProperty();
    }

    /**
     * Clears the stream index at the given row index and column index.
     *
     * @param rowIndex the row index
     * @param columnIndex the column index
     * @return this {@code GridBuilder}
     */
    public GridBuilder clearStreamIndex(int rowIndex, int columnIndex) {
      setStreamIndex(rowIndex, columnIndex, NO_STREAM_INDEX);
      return this;
    }

    /**
     * Clears all stream indexes.
     *
     * @return this {@code GridBuilder}
     */
    public GridBuilder clearStreamIndexes() {
      for (int rowIndex = 0; rowIndex < size; rowIndex++) {
        for (int columnIndex = 0; columnIndex < size; columnIndex++) {
          clearStreamIndex(rowIndex, columnIndex);
        }
      }
      return this;
    }

    /**
     * Returns the number at the given row index and stream index.
     *
     * @param rowIndex the row index
     * @param columnIndex the column index
     * @return the number at the given row index and stream index
     */
    public int getNumber(int rowIndex, int columnIndex) {
      return numbers[rowIndex][columnIndex];
    }

    /**
     * Sets the number at a given row and column index.
     *
     * @param rowIndex the row index
     * @param columnIndex the column index
     * @param number the number
     * @return this {@code GridBuilder}
     * @throws IllegalArgumentException if the number is invalid
     */
    public GridBuilder setNumber(int rowIndex, int columnIndex, int number) {
      checkNumber(number, size);
      numbers[rowIndex][columnIndex] = number;
      numberProperties[rowIndex][columnIndex].set(number);
      return this;
    }

    /**
     * Sets the array of numbers.
     *
     * @param numbers array of numbers
     * @return this {@code GridBuilder}
     * @throws IllegalArgumentException if {@code numbers} contains an invalid number
     */
    public GridBuilder setNumbers(int[][] numbers) {
      for (int rowIndex = 0; rowIndex < size; rowIndex++) {
        for (int columnIndex = 0; columnIndex < size; columnIndex++) {
          int number = numbers[rowIndex][columnIndex];
          setNumber(rowIndex, columnIndex, number);
        }
      }
      return this;
    }

    /**
     * Returns the {@link ReadOnlyIntegerProperty} indicating the number.
     *
     * @param rowIndex the row index
     * @param columnIndex the column index
     * @return the {@link ReadOnlyIntegerProperty} indicating the number
     */
    public ReadOnlyIntegerProperty numberProperty(int rowIndex, int columnIndex) {
      return numberProperties[rowIndex][columnIndex].getReadOnlyProperty();
    }

    /**
     * Clears the number at the given row index and column index.
     *
     * @param rowIndex the row index
     * @param columnIndex the column index
     * @return this {@code GridBuilder}
     */
    public GridBuilder clearNumber(int rowIndex, int columnIndex) {
      setNumber(rowIndex, columnIndex, NO_NUMBER);
      return this;
    }

    /**
     * Clears all numbers.
     *
     * @return this {@code GridBuilder}
     */
    public GridBuilder clearNumbers() {
      for (int rowIndex = 0; rowIndex < size; rowIndex++) {
        for (int columnIndex = 0; columnIndex < size; columnIndex++) {
          clearNumber(rowIndex, columnIndex);
        }
      }
      return this;
    }

    /**
     * Returns the total occurrences of a stream index in this {@code GridBuilder}.
     *
     * @param streamIndex the streamIndex to get occurrences for
     * @return the total occurrences of a stream index in this {@code GridBuilder}
     */
    public int getStreamIndexOccurrences(int streamIndex) {
      return streamIndexOccurrenceProperty(streamIndex).get();
    }

    /**
     * Returns the {@link ReadOnlyIntegerProperty} indicating the number of times a stream index
     * occurs in this {@code GridBuilder}.
     *
     * @param streamIndex the streamIndex to return the property for
     * @return the {@link ReadOnlyIntegerProperty} indicating the number of times a stream index
     *     occurs in this {@code GridBuilder}
     */
    public ReadOnlyIntegerProperty streamIndexOccurrenceProperty(int streamIndex) {
      return streamIndexOccurrences.get(streamIndex).getReadOnlyProperty();
    }

    /**
     * Returns the total occurrences of a number in this {@code GridBuilder}.
     *
     * @param number the number to get occurrences for
     * @return the total occurrences of a number in this {@code GridBuilder}
     */
    public int getNumberOccurrences(int number) {
      return numberOccurrenceProperty(number).get();
    }

    /**
     * Returns the {@link ReadOnlyIntegerProperty} indicating the number of times a number occurs in
     * this {@code GridBuilder}.
     *
     * @param number the number to return the property for
     * @return the {@link ReadOnlyIntegerProperty} indicating the number of times a number occurs in
     *     this {@code GridBuilder}
     */
    public ReadOnlyIntegerProperty numberOccurrenceProperty(int number) {
      return numberOccurrences.get(number).getReadOnlyProperty();
    }

    /**
     * Builds a new {@link Grid} instance.
     *
     * @return a new {@link Grid} instance
     * @throws IllegalArgumentException if the array of stream indexes is invalid or if any row,
     *     column or stream does not contain unique numbers
     */
    public Grid build() {
      checkStreams(streams);
      return new Grid(size, streams, numbers);
    }

    @Override
    public int hashCode() {
      return hash(streams, numbers);
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null) {
        return false;
      }
      if (getClass() != obj.getClass()) {
        return false;
      }
      GridBuilder other = (GridBuilder) obj;
      if (!deepEquals(streams, other.streams)) {
        return false;
      }
      return deepEquals(numbers, other.numbers);
    }

  }

}
