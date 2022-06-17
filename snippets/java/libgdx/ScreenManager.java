/**
 * Like others, I have found it useful when developing games in libGDX to
 * create a class for screen management. This is just a very basic implementation
 * which allows the caller to set the current screen, switch to the previous
 * screen and dispose all screens. For more complex games, extra functionality
 * will need to be added, but for most cases I think this is sufficient.
 */

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import static java.util.Objects.requireNonNull;

/**
 * {@code ScreenManager} is used to manage {@link Screen}s in
 * {@link Game} instances.
 *
 * @author Mike Lowe
 */
public final class ScreenManager implements Disposable {

  private final Game game;
  private final Array<Screen> screens = new Array<Screen>();

  /**
   * Creates a new {@code ScreenManager} with a reference to the
   * {@link Game}.
   *
   * @param game reference to the {@link Game}
   * @throws NullPointerException if {@code game} is {@code null}
   */
  public ScreenManager(final Game game) {
    this.game = requireNonNull(game, "game cannot be null");
  }

  /**
   * Sets the {@link Screen} to display. Note that any existing
   * {@link Screen}s are NOT disposed.
   *
   * @param screen the {@link Screen} to display
   * @throws NullPointerException if {@code screen} is {@code null}
   */
  public void setScreen(final Screen screen) {
    requireNonNull(screen, "screen cannot be null");
    screens.add(screen);
    game.setScreen(screen);
  }

  /**
   * Switches to the previous {@link Screen}, if one exists. Note that
   * this removes and disposes the current {@link Screen}, if one exists.
   */
  public void switchToPreviousScreen() {
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
  public void disposeAndClearAllScreens() {
    for (final Screen screen : screens) {
      screen.dispose();
    }
    screens.clear();
  }

  @Override
  public void dispose() {
    disposeAndClearAllScreens();
  }

}
