package lowe.mike.strimko.model;

import static java.util.Objects.hash;
import static javafx.collections.FXCollections.observableSet;
import static lowe.mike.strimko.model.Constants.NO_NUMBER;

import java.util.Collection;
import java.util.HashSet;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.collections.ObservableSet;

/**
 * {@code Cell} instances are to intended to provide information about cells that make up a {@link
 * Grid}.
 *
 * <p>Information about the {@code Cell} includes the row index, the column index, the stream
 * index, the number contained in the {@code Cell}, if the {@code Cell} is set, an {@link
 * ObservableSet} of possible numbers and if the {@code Cell} is locked.
 *
 * @author Mike Lowe
 */
public final class Cell {

  private final int rowIndex;
  private final int columnIndex;
  private final int streamIndex;
  private final ReadOnlyIntegerWrapper number = new ReadOnlyIntegerWrapper();
  private final ReadOnlyBooleanWrapper isSet = new ReadOnlyBooleanWrapper();
  private final ObservableSet<Integer> possibleNumbers = observableSet(new HashSet<>());
  private final boolean isLocked;

  /**
   * Creates a new {@code Cell} instance given the row index, the column index, the stream index,
   * the number and a {@link Collection} of possible numbers.
   *
   * @param rowIndex the row index
   * @param columnIndex the column index
   * @param streamIndex the stream index
   * @param number the number
   * @param possibleNumbers a {@link Collection} of possible numbers
   * @return a {@code Cell} instance
   */
  public static Cell newInstance(int rowIndex, int columnIndex, int streamIndex, int number,
      Collection<Integer> possibleNumbers) {
    return new Cell(rowIndex, columnIndex, streamIndex, number, possibleNumbers);
  }

  /**
   * Creates a new {@code Cell} instance from an existing instance.
   *
   * @param cell the existing {@code Cell} instance
   * @return a new {@code Cell} instance copied from an existing instance
   */
  public static Cell copyOf(Cell cell) {
    return new Cell(cell);
  }

  private Cell(int rowIndex, int columnIndex, int streamIndex, int number,
      Collection<Integer> possibleNumbers) {
    this.rowIndex = rowIndex;
    this.columnIndex = columnIndex;
    this.streamIndex = streamIndex;
    this.number.set(number);
    bindIsSetPropertyToNumberProperty();
    this.possibleNumbers.addAll(possibleNumbers);
    this.isLocked = isSet();
  }

  private void bindIsSetPropertyToNumberProperty() {
    isSet.bind(isNumberSet());
  }

  private BooleanBinding isNumberSet() {
    return number.greaterThan(NO_NUMBER);
  }

  private Cell(Cell cell) {
    this.rowIndex = cell.getRowIndex();
    this.columnIndex = cell.getColumnIndex();
    this.streamIndex = cell.getStreamIndex();
    this.number.set(cell.getNumber());
    bindIsSetPropertyToNumberProperty();
    this.possibleNumbers.addAll(cell.getPossibleNumbers());
    this.isLocked = cell.isLocked;
  }

  /**
   * Returns this {@code Cell}'s row index.
   *
   * @return this {@code Cell}'s row index
   */
  public int getRowIndex() {
    return rowIndex;
  }

  /**
   * Returns this {@code Cell}'s column index.
   *
   * @return this {@code Cell}'s column index
   */
  public int getColumnIndex() {
    return columnIndex;
  }

  /**
   * Returns this {@code Cell}'s stream index.
   *
   * @return this {@code Cell}'s stream index
   */
  public int getStreamIndex() {
    return streamIndex;
  }

  /**
   * Returns this {@code Cell}'s number, 0 if it is not set.
   *
   * @return this {@code Cell}'s number, 0 if it is not set
   */
  public int getNumber() {
    return number.get();
  }

  /**
   * Sets the number of this {@code Cell}, if it is not locked.
   *
   * @param number the number to set
   */
  public void setNumber(int number) {
    if (!isLocked) {
      this.number.set(number);
    }
  }

  /**
   * Returns this {@code Cell}'s number as an {@link ReadOnlyIntegerProperty}.
   *
   * @return this {@code Cell}'s number as an {@link ReadOnlyIntegerProperty}
   */
  public ReadOnlyIntegerProperty numberProperty() {
    return number.getReadOnlyProperty();
  }

  /**
   * Clears the number from this {@code Cell}, if it is not locked.
   */
  public void clearNumber() {
    setNumber(NO_NUMBER);
  }

  /**
   * Returns {@code true} if this {@code Cell}'s number has been set; {@code false} otherwise.
   *
   * @return {@code true} if this {@code Cell}'s number has been set; {@code false} otherwise
   */
  public boolean isSet() {
    return isSet.get();
  }

  /**
   * Returns this {@code Cell}'s {@link ReadOnlyBooleanProperty} indicating if the number has been
   * set.
   *
   * @return this {@code Cell}'s {@link ReadOnlyBooleanProperty} indicating if the number has been
   *     set
   */
  public ReadOnlyBooleanProperty setProperty() {
    return isSet.getReadOnlyProperty();
  }

  /**
   * Returns this {@code Cell}'s {@link ObservableSet} of possible numbers.
   *
   * @return this {@code Cell}'s {@link ObservableSet} of possible numbers
   */
  public ObservableSet<Integer> getPossibleNumbers() {
    return possibleNumbers;
  }

  /**
   * Returns {@code true} if this {@code Cell} is locked; {@code false} otherwise.
   *
   * @return {@code true} if this {@code Cell} is locked; {@code false} otherwise
   */
  public boolean isLocked() {
    return isLocked;
  }

  @Override
  public int hashCode() {
    return hash(getRowIndex(), getColumnIndex(), getStreamIndex(), getNumber(),
        getPossibleNumbers(), isLocked());
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
    Cell other = (Cell) obj;
    if (getRowIndex() != other.getRowIndex()) {
      return false;
    }
    if (getColumnIndex() != other.getColumnIndex()) {
      return false;
    }
    if (getStreamIndex() != other.getStreamIndex()) {
      return false;
    }
    if (getNumber() != other.getNumber()) {
      return false;
    }
    if (getPossibleNumbers() == null) {
      if (other.getPossibleNumbers() != null) {
        return false;
      }
    } else if (!getPossibleNumbers().equals(other.getPossibleNumbers())) {
      return false;
    }
    return isLocked() == other.isLocked();
  }

}
