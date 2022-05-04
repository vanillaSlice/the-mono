package lowe.mike.jumpyblock;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lowe.mike.jumpyblock.screen.MainMenuScreen;
import lowe.mike.jumpyblock.screen.ScreenManager;

/**
 * Main class for <i>Jumpy Block</i> game.
 *
 * @author Mike Lowe
 */
public final class JumpyBlockGame extends Game {

  public static final String TITLE = "Jumpy Block";
  public static final int WIDTH = 360;
  public static final int HEIGHT = 640;

  private Assets assets;
  private SpriteBatch spriteBatch;
  private ScreenManager screenManager;

  @Override
  public void create() {
    assets = new Assets();
    spriteBatch = new SpriteBatch();
    initialiseScreenManager();
    assets.music.play();
  }

  private void initialiseScreenManager() {
    screenManager = new ScreenManager(this);
    MainMenuScreen mainMenuScreen = new MainMenuScreen(screenManager, assets, spriteBatch);
    screenManager.setScreen(mainMenuScreen);
  }

  @Override
  public void pause() {
    super.pause();
    assets.music.pause();
  }

  @Override
  public void resume() {
    super.resume();
    assets.music.play();
  }

  @Override
  public void dispose() {
    assets.dispose();
    spriteBatch.dispose();
    screenManager.dispose();
  }
}
