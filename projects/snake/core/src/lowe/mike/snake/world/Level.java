package lowe.mike.snake.world;

/**
 * {@code Level} stores the minimum and maximum levels the game can be played in.
 * <p>
 * Instances of {@code Level} cannot be created.
 *
 * @author Mike Lowe
 */
public final class Level {

  // don't want instances
  private Level() {
  }

  public static final int MINIMUM = 1;
  public static final int MAXIMUM = 9;
}
