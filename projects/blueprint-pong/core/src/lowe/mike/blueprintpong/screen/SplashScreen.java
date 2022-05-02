package lowe.mike.blueprintpong.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lowe.mike.blueprintpong.Assets;

/**
 * Splash screen to show while assets are being loaded.
 *
 * @author Mike Lowe
 */
public final class SplashScreen extends BaseScreen {

  /**
   * Creates a new {@code SplashScreen} given {@link Assets}, a {@link SpriteBatch} and a {@link
   * ScreenManager}.
   *
   * @param assets {@link Assets} containing assets used in the {@link Screen}
   * @param spriteBatch {@link SpriteBatch} to add sprites to
   * @param screenManager the {@link ScreenManager} used to manage game {@link Screen}s
   */
  public SplashScreen(Assets assets, SpriteBatch spriteBatch, ScreenManager screenManager) {
    // use splash background texture instead of default
    super(assets, spriteBatch, screenManager, assets.getSplashBackgroundTexture());
  }

  @Override
  void update(float delta) {
    if (assets.isFinishedLoading()) {
      switchToMainMenuScreen();
    }
  }

  private void switchToMainMenuScreen() {
    // dispose this screen and all previous screens because we won't be able to return from the
    // next screen
    screenManager.disposeAndClearAllScreens();
    screenManager.setScreen(new MainMenuScreen(assets, spriteBatch, screenManager));
  }

  @Override
  public void onDispose() {
    // dispose this because it won't be used again
    assets.disposeSplashBackgroundTexture();
  }
}
