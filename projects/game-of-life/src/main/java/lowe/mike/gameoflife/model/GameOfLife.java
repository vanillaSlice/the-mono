package lowe.mike.gameoflife.model;

import static java.util.Objects.requireNonNull;

import java.util.Random;
import javafx.animation.Animation;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyLongProperty;
import javafx.beans.property.ReadOnlyLongWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

/**
 * {@code GameOfLife} instances run <i>The Game of Life</i>.
 *
 * @author Mike Lowe
 */
public class GameOfLife {

  private static final Random RANDOM = new Random();

  private final Grid grid;
  private final ReadOnlyLongWrapper generation = new ReadOnlyLongWrapper();
  private Timeline timeline;
  private final ObjectProperty<Speed> speed = new SimpleObjectProperty<>(Speed.SLOW);

  /**
   * Creates a new {@code GameOfLife} instance given the underlying {@link Grid}.
   *
   * @param grid the underlying {@link Grid}
   * @throws NullPointerException if {@code grid} is {@code null}
   */
  public GameOfLife(Grid grid) {
    this.grid = requireNonNull(grid, "grid is null");
    updateTimeline();
    addSpeedPropertyListener();
    grid.randomGeneration(RANDOM);
  }

  private void updateTimeline() {
    Duration duration = new Duration(speed.get().getMilliseconds());
    EventHandler<ActionEvent> eventHandler = event -> next();
    KeyFrame keyFrame = new KeyFrame(duration, eventHandler);
    timeline = new Timeline(keyFrame);
    timeline.setCycleCount(Animation.INDEFINITE);
  }

  /**
   * Transitions into the next generation.
   */
  public void next() {
    grid.nextGeneration();
    generation.set(getGeneration() + 1);
  }

  private void addSpeedPropertyListener() {
    speed.addListener((observable, oldValue, newValue) -> {
      boolean shouldPlay = timeline.getStatus() == Status.RUNNING;
      pause();
      updateTimeline();
      if (shouldPlay) {
        play();
      }
    });
  }

  /**
   * Returns the current generation.
   *
   * @return the current generation
   */
  public long getGeneration() {
    return generation.get();
  }

  /**
   * Returns the generation {@link ReadOnlyLongProperty}.
   *
   * @return the generation {@link ReadOnlyLongProperty}
   */
  public ReadOnlyLongProperty generationProperty() {
    return generation.getReadOnlyProperty();
  }

  /**
   * Returns the {@link Grid}.
   *
   * @return the {@link Grid}
   */
  public Grid getGrid() {
    return grid;
  }

  /**
   * Sets the {@link Speed} of the game.
   *
   * @param speed the {@link Speed} of the game
   * @throws NullPointerException if {@code speed} is {@code null}
   */
  public void setSpeed(Speed speed) {
    this.speed.set(requireNonNull(speed, "speed is null"));
  }

  /**
   * Plays the game.
   */
  public void play() {
    timeline.play();
  }

  /**
   * Pauses the game.
   */
  public void pause() {
    timeline.pause();
  }

  /**
   * Clears the current game.
   */
  public void clear() {
    pause();
    grid.clear();
    generation.set(0);
  }

  /**
   * Clears the current game and randomly generates a new one.
   */
  public void reset() {
    clear();
    grid.randomGeneration(RANDOM);
  }
}
