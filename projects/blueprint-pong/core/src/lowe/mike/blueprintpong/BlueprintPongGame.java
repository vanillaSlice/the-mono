package lowe.mike.blueprintpong;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lowe.mike.blueprintpong.screen.ScreenManager;
import lowe.mike.blueprintpong.screen.SplashScreen;

/**
 * Main class for <i>Blueprint Pong</i> game.
 *
 * @author Mike Lowe
 */
public final class BlueprintPongGame extends Game {

  public static final String TITLE = "Blueprint Pong";
  public static final float VIRTUAL_WIDTH = 320f;
  public static final float VIRTUAL_HEIGHT = 180f;

  private Assets assets;
  private SpriteBatch spriteBatch;
  private ScreenManager screenManager;

  @Override
  public void create() {
    assets = new Assets();
    spriteBatch = new SpriteBatch();
    screenManager = new ScreenManager(this);
    screenManager.setScreen(new SplashScreen(assets, spriteBatch, screenManager));
  }

  @Override
  public void dispose() {
    assets.dispose();
    spriteBatch.dispose();
    screenManager.dispose();
  }
}
