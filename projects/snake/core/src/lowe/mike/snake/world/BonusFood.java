package lowe.mike.snake.world;

/**
 * {@code BonusFood} instances represent bonus food that appears periodically in the game.
 * <p>
 * These are differentiated from regular {@link Food} by flashing at regular intervals.
 *
 * @author Mike Lowe
 */
final class BonusFood extends Food {

  private static final float FLASH_TICK_INTERVAL = .05f;

  private float flashTick;

  /**
   * Makes this {@code BonusFood} flash when appropriate.
   *
   * @param delta time in seconds since the last frame
   */
  void updateFlash(float delta) {
    if (flashTick >= FLASH_TICK_INTERVAL) {
      flashTick -= FLASH_TICK_INTERVAL;
      setVisible(!isVisible());
    } else {
      flashTick += delta;
    }
  }
}
