package lowe.mike.snake.screen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import lowe.mike.snake.util.Assets;
import lowe.mike.snake.util.ScreenManager;

/**
 * Splash screen to show while assets are being loaded.
 *
 * @author Mike Lowe
 */
public final class SplashScreen extends BaseScreen {

  /**
   * Creates a new {@code SplashScreen} given a {@link SpriteBatch}.
   *
   * @param spriteBatch the {@link SpriteBatch} to add sprites to
   */
  public SplashScreen(SpriteBatch spriteBatch) {
    super(spriteBatch);
    setBackground();
  }

  private void setBackground() {
    stage.addActor(new Image(Assets.getSplashBackground()));
  }

  @Override
  void update(float delta) {
    if (Assets.isFinishedLoading()) {
      switchToMainMenuScreen();
    }
  }

  private void switchToMainMenuScreen() {
    // dispose this screen and all previous screens because we won't be able to return from the
    // next screen
    ScreenManager.disposeAndClearAllScreens();
    ScreenManager.setScreen(new MainMenuScreen(spriteBatch));
  }

  @Override
  public void onDispose() {
    // dispose this because it won't be used again
    Assets.disposeSplashBackground();
  }
}
