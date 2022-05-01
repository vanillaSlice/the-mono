package lowe.mike.snake.world;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import lowe.mike.snake.util.Assets;

/**
 * {@code Food} instances represent food in the game to be eaten by the snake.
 *
 * @author Mike Lowe
 */
class Food extends Image {

  /**
   * Creates a new {@code Food} instance.
   */
  Food() {
    super(Assets.getBlock());
  }
}
