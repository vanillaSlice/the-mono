package lowe.mike.blueprintpong.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import lowe.mike.blueprintpong.Assets;

/**
 * Screen to show when the game is paused.
 *
 * @author Mike Lowe
 */
final class PauseScreen extends BaseScreen {

  private static final String RESUME_BUTTON_TEXT = "Resume";
  private static final String RESTART_BUTTON_TEXT = "Restart";

  private final GameScreen gameScreen;

  /**
   * Creates a new {@code PauseScreen} given {@link Assets}, a {@link SpriteBatch}, a {@link
   * ScreenManager} and a reference to the {@link GameScreen}.
   *
   * @param assets {@link Assets} containing assets used in the {@link Screen}
   * @param spriteBatch {@link SpriteBatch} to add sprites to
   * @param screenManager the {@link ScreenManager} used to manage game {@link Screen}s
   * @param gameScreen reference to the {@link GameScreen}
   */
  PauseScreen(Assets assets,
      SpriteBatch spriteBatch,
      ScreenManager screenManager,
      GameScreen gameScreen) {
    super(assets, spriteBatch, screenManager);
    this.gameScreen = gameScreen;
    Table menu = createMenu();
    this.stage.addActor(menu);
  }

  private Table createMenu() {
    Table table = new Table();
    table.setFillParent(true);
    table.center();

    // add resume button
    table.row().padBottom(COMPONENT_SPACING);
    TextButton resumeButton = createResumeButton();
    table.add(resumeButton).expandX();

    // add restart button
    table.row().padBottom(COMPONENT_SPACING);
    TextButton restartButton = createRestartButton();
    table.add(restartButton).expandX();

    // add settings button
    table.row().padBottom(COMPONENT_SPACING);
    TextButton settingsButton =
        ScreenUtils.createSettingsButton(assets, spriteBatch, screenManager);
    table.add(settingsButton);

    // add exit button
    table.row();
    TextButton exitButton = ScreenUtils.createExitButton(assets, spriteBatch, screenManager);
    table.add(exitButton).expandX();

    return table;
  }

  private TextButton createResumeButton() {
    TextButton button = ScreenUtils.createTextButton(assets, RESUME_BUTTON_TEXT);
    addResumeButtonListener(button);
    return button;
  }

  private void addResumeButtonListener(final TextButton button) {
    button.addListener(new ChangeListener() {

      @Override
      public void changed(ChangeEvent event, Actor actor) {
        if (button.isChecked()) {
          switchToGameScreenAndResume();
          button.setChecked(false);
        }
      }

    });
  }

  private void switchToGameScreenAndResume() {
    screenManager.switchToPreviousScreen();
    gameScreen.resumeGame();
  }

  private TextButton createRestartButton() {
    TextButton button = ScreenUtils.createTextButton(assets, RESTART_BUTTON_TEXT);
    addRestartButtonListener(button);
    return button;
  }

  private void addRestartButtonListener(final TextButton button) {
    button.addListener(new ChangeListener() {

      @Override
      public void changed(ChangeEvent event, Actor actor) {
        if (button.isChecked()) {
          switchToGameScreenAndRestart();
          button.setChecked(false);
        }
      }

    });
  }

  private void switchToGameScreenAndRestart() {
    screenManager.switchToPreviousScreen();
    gameScreen.newGame();
  }
}
