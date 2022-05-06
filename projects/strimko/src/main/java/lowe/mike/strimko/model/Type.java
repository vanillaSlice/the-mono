package lowe.mike.strimko.model;

/**
 * {@code Type} enum represents the type of {@link Puzzle}.
 *
 * @author Mike Lowe
 */
public enum Type {

  STRIMKO("Strimko"), SUDOKU("Sudoku");

  private final String displayName;

  Type(String displayName) {
    this.displayName = displayName;
  }

  @Override
  public String toString() {
    return displayName;
  }

}
