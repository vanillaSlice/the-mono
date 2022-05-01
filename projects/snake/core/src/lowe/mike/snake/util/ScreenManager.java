package lowe.mike.snake.util;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Array;

/**
 * {@code ScreenManager} is used to manage {@link Screen}s in {@link Game} instances.
 * <p>
 * Instances of {@code ScreenManager} cannot be created.
 *
 * @author Mike Lowe
 */
public final class ScreenManager {

  private static Game game;
  private static Array<Screen> screens;

  // don't want instances
  private ScreenManager() {
  }

  /**
   * Initialises the {@code ScreenManager} with a reference to the {@link Game}.
   *
   * @param game reference to the {@link Game}
   */
  public static void initialise(Game game) {
    ScreenManager.game = game;
    screens = new Array<Screen>();
  }

  /**
   * Sets the {@link Screen} to display. Note that any existing {@link Screen}s are NOT disposed.
   *
   * @param screen the {@link Screen} to display
   */
  public static void setScreen(Screen screen) {
    screens.add(screen);
    game.setScreen(screen);
  }

  /**
   * Switches to the previous {@link Screen}, if one exists. Note that this removes and disposes the
   * current {@link Screen}, if one exists.
   */
  public static void switchToPreviousScreen() {
    // remove and dispose current screen
    if (screens.size != 0) {
      screens.pop().dispose();
      // switch to previous screen
      if (screens.size != 0) {
        game.setScreen(screens.peek());
      }
    }
  }

  /**
   * Disposes and clears all {@link Screen}s.
   */
  public static void disposeAndClearAllScreens() {
    for (Screen screen : screens) {
      screen.dispose();
    }
    screens.clear();
  }
}
