package lowe.mike.snake;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lowe.mike.snake.screen.SplashScreen;
import lowe.mike.snake.util.Assets;
import lowe.mike.snake.util.ScreenManager;
import lowe.mike.snake.util.State;

/**
 * Main class for <i>Snake</i> game.
 *
 * @author Mike Lowe
 */
public final class SnakeGame extends Game {

  public static final String TITLE = "Snake";
  public static final int WIDTH = 360;
  public static final int HEIGHT = 640;

  private SpriteBatch spriteBatch;

  @Override
  public void create() {
    State.initialise();
    Assets.initialise();
    ScreenManager.initialise(this);
    spriteBatch = new SpriteBatch();
    ScreenManager.setScreen(new SplashScreen(spriteBatch));
  }

  @Override
  public void dispose() {
    Assets.dispose();
    ScreenManager.disposeAndClearAllScreens();
    spriteBatch.dispose();
  }
}
