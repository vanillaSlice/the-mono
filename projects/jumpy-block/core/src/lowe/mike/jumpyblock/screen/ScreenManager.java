package lowe.mike.jumpyblock.screen;

import com.badlogic.gdx.Screen;
import lowe.mike.jumpyblock.JumpyBlockGame;

/**
 * {@code ScreenManager} is used to manage the current {@link Screen} in the <i>Jumpy Block</i>
 * game.
 *
 * @author Mike Lowe
 */
public final class ScreenManager {

  private final JumpyBlockGame game;
  private Screen currentScreen;

  /**
   * Creates a new {@code ScreenManager} instance given the {@link JumpyBlockGame} to manage the
   * {@link Screen} for.
   *
   * @param game reference to the {@link JumpyBlockGame}
   */
  public ScreenManager(JumpyBlockGame game) {
    this.game = game;
  }

  /**
   * Sets the current {@link Screen}, disposing an existing {@link Screen} if it exists.
   *
   * @param screen the {@link Screen} to display
   */
  public void setScreen(Screen screen) {
    dispose();
    currentScreen = screen;
    game.setScreen(currentScreen);
  }

  /**
   * Disposes the current {@link Screen}, if it exists.
   */
  public void dispose() {
    if (currentScreen != null) {
      currentScreen.dispose();
    }
  }
}
