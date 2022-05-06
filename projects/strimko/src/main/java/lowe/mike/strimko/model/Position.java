package lowe.mike.strimko.model;

import static java.util.Objects.hash;

/**
 * {@code Position} instances contain the row index and the column index for a {@link Cell}.
 *
 * <p>{@code Position} instances are immutable.
 *
 * @author Mike Lowe
 */
public final class Position {

  private final int rowIndex;
  private final int columnIndex;

  /**
   * Creates a new {@code Position} instance given the row index and column index.
   *
   * @param rowIndex the row index
   * @param columnIndex the column index
   */
  public Position(int rowIndex, int columnIndex) {
    this.rowIndex = rowIndex;
    this.columnIndex = columnIndex;
  }

  /**
   * Returns the row index.
   *
   * @return the row index
   */
  public int getRowIndex() {
    return rowIndex;
  }

  /**
   * Returns the column index.
   *
   * @return the column index
   */
  public int getColumnIndex() {
    return columnIndex;
  }

  @Override
  public int hashCode() {
    return hash(rowIndex, columnIndex);
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
    Position other = (Position) obj;
    if (rowIndex != other.rowIndex) {
      return false;
    }
    return columnIndex == other.columnIndex;
  }

}
