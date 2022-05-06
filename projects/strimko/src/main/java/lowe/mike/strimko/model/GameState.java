package lowe.mike.strimko.model;

import static java.util.Objects.hash;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import lowe.mike.strimko.model.Grid.GridBuilder;

/**
 * {@code GameState} instances contain information about the current state of the game.
 *
 * @author Mike Lowe
 */
public final class GameState {

  private final ReadOnlyObjectWrapper<Puzzle> puzzle = new ReadOnlyObjectWrapper<>();
  private final ReadOnlyObjectWrapper<GridBuilder> gridBuilder = new ReadOnlyObjectWrapper<>();

  /**
   * Returns the {@link Puzzle} being played.
   *
   * @return the {@link Puzzle} being played
   */
  public Puzzle getPuzzle() {
    return puzzle.get();
  }

  /**
   * Sets the {@link Puzzle} being played.
   *
   * @param puzzle the {@link Puzzle} being played
   */
  public void setPuzzle(Puzzle puzzle) {
    if (puzzle != null) {
      this.puzzle.set(puzzle);
    }
  }

  /**
   * Returns the {@link ReadOnlyObjectProperty} of the {@link Puzzle} being played.
   *
   * @return the {@link ReadOnlyObjectProperty} of the {@link Puzzle} being played
   */
  public ReadOnlyObjectProperty<Puzzle> puzzleProperty() {
    return puzzle.getReadOnlyProperty();
  }

  /**
   * Returns the {@link GridBuilder}.
   *
   * @return the {@link GridBuilder}
   */
  public GridBuilder getGridBuilder() {
    return gridBuilder.get();
  }

  /**
   * Sets the {@link GridBuilder}.
   *
   * @param gridBuilder the {@link GridBuilder}
   */
  public void setGridBuilder(GridBuilder gridBuilder) {
    if (gridBuilder != null) {
      this.gridBuilder.set(gridBuilder);
    }
  }

  /**
   * Returns the {@link ReadOnlyObjectProperty} of the {@link GridBuilder}.
   *
   * @return the {@link ReadOnlyObjectProperty} of the {@link GridBuilder}
   */
  public ReadOnlyObjectProperty<GridBuilder> gridBuilderProperty() {
    return gridBuilder.getReadOnlyProperty();
  }

  @Override
  public int hashCode() {
    return hash(puzzle, gridBuilder);
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
    GameState other = (GameState) obj;
    if (getPuzzle() == null) {
      if (other.getPuzzle() != null) {
        return false;
      }
    } else if (!getPuzzle().equals(other.getPuzzle())) {
      return false;
    }
    if (getGridBuilder() == null) {
      return other.getGridBuilder() == null;
    } else {
      return getGridBuilder().equals(other.getGridBuilder());
    }
  }

}
