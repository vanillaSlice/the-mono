package lowe.mike.strimko.model;

import static java.util.Objects.hash;
import static lowe.mike.strimko.model.solver.Solver.solve;

import java.util.Collection;
import java.util.LinkedHashSet;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import lowe.mike.strimko.model.solver.SolvingResult;

/**
 * {@code Puzzle} instances are intended to represent Strimko and Sudoku puzzles.
 *
 * <p>Instance information includes the {@code Puzzle}'s {@link Type}, {@link Difficulty}, {@link
 * Grid} and solution.
 *
 * @author Mike Lowe
 */
public final class Puzzle {

  private final Type type;
  private final Difficulty difficulty;
  private final Grid grid;
  private final int[][] solution;
  private final Collection<Position> hints = new LinkedHashSet<>();
  private final ReadOnlyObjectWrapper<Cell> nextHint = new ReadOnlyObjectWrapper<>();

  /**
   * Creates a new {@code Puzzle} given the {@link Type} and {@link Grid}.
   *
   * @param type the {@link Type}
   * @param grid the {@link Grid}
   * @throws IllegalArgumentException if {@code grid} is not solvable or has multiple solutions
   */
  public Puzzle(Type type, Grid grid) {
    this.type = type;
    this.grid = grid;
    SolvingResult result = solve(this.grid);
    this.difficulty = result.getDifficulty();
    this.solution = result.getSolution();
    this.hints.addAll(result.getHints());
    initialize();
  }

  private void initialize() {
    addCellChangeListeners();
    initializeNextHint();
  }

  private void addCellChangeListeners() {
    for (Cell cell : grid.getCells()) {
      addCellChangeListener(cell);
    }
  }

  private void addCellChangeListener(Cell cell) {
    cell.numberProperty().addListener((observable) -> updateNextHint());
  }

  private void initializeNextHint() {
    updateNextHint();
  }

  private void updateNextHint() {
    nextHint.set(findNextHint());
  }

  private Cell findNextHint() {
    for (Position position : hints) {
      Cell cell = grid.getCell(position);
      if (isCellUnsolved(cell)) {
        return cell;
      }
    }
    return null;
  }

  private boolean isCellUnsolved(Cell cell) {
    int number = cell.getNumber();
    int solutionNumber = getSolutionForCell(cell);
    return number != solutionNumber;
  }

  /**
   * Returns this {@code Puzzle}'s {@link Type}.
   *
   * @return this {@code Puzzle}'s {@link Type}
   */
  public Type getType() {
    return type;
  }

  /**
   * Returns this {@code Puzzle}'s {@link Difficulty}.
   *
   * @return this {@code Puzzle}'s {@link Difficulty}
   */
  public Difficulty getDifficulty() {
    return difficulty;
  }

  /**
   * Returns this {@code Puzzle}'s {@link Grid}.
   *
   * @return this {@code Puzzle}'s {@link Grid}
   */
  public Grid getGrid() {
    return grid;
  }

  /**
   * Returns the solution for the {@link Cell}.
   *
   * @param cell the {@link Cell}
   * @return the solution for the {@link Cell}
   */
  public int getSolutionForCell(Cell cell) {
    return solution[cell.getRowIndex()][cell.getColumnIndex()];
  }

  /**
   * Returns the next {@link Cell} hint or {@code null} if there are no hints left.
   *
   * @return the next {@link Cell} hint or {@code null} if there are no hints left
   */
  public Cell getNextHint() {
    return nextHint.get();
  }

  /**
   * Returns the next hint {@link ReadOnlyObjectProperty}.
   *
   * @return the next hint {@link ReadOnlyObjectProperty}
   */
  public ReadOnlyObjectProperty<Cell> nextHintProperty() {
    return nextHint.getReadOnlyProperty();
  }

  @Override
  public int hashCode() {
    return hash(type, grid);
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
    Puzzle other = (Puzzle) obj;
    if (type != other.type) {
      return false;
    }
    if (grid == null) {
      return other.grid == null;
    } else {
      return grid.equals(other.grid);
    }
  }

}
