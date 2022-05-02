package lowe.mike.blueprintpong.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Disposable;
import java.util.Stack;
import lowe.mike.blueprintpong.BlueprintPongGame;

/**
 * {@code ScreenManager} is used to manage {@link Screen}s in the <i>Blueprint Pong</i> game.
 *
 * @author Mike Lowe
 */
public final class ScreenManager implements Disposable {

  private final BlueprintPongGame game;
  private final Stack<Screen> screens = new Stack<Screen>();

  /**
   * Creates a new {@code ScreenManager} with a reference to the {@link BlueprintPongGame}.
   *
   * @param game reference to the {@link BlueprintPongGame}
   */
  public ScreenManager(BlueprintPongGame game) {
    this.game = game;
  }

  /**
   * Sets the {@link Screen} to display. Note that any existing {@link Screen}s are NOT disposed.
   *
   * @param screen the {@link Screen} to display
   */
  public void setScreen(Screen screen) {
    screens.push(screen);
    game.setScreen(screen);
  }

  /**
   * Switches to the previous {@link Screen}, if one exists. Note that this removes and disposes the
   * current {@link Screen}, if one exists.
   */
  public void switchToPreviousScreen() {
    // remove and dispose current screen
    if (!screens.isEmpty()) {
      screens.pop().dispose();
      // switch to previous screen
      if (!screens.isEmpty()) {
        game.setScreen(screens.peek());
      }
    }
  }

  /**
   * Disposes and clears all {@link Screen}s.
   */
  public void disposeAndClearAllScreens() {
    for (Screen screen : screens) {
      screen.dispose();
    }
    screens.clear();
  }

  @Override
  public void dispose() {
    disposeAndClearAllScreens();
  }
}
