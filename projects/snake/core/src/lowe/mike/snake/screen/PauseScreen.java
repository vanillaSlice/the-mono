package lowe.mike.snake.screen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import lowe.mike.snake.util.Assets;
import lowe.mike.snake.util.ScreenManager;
import lowe.mike.snake.util.Utils;

/**
 * Pause screen to show when the game is paused.
 *
 * @author Mike Lowe
 */
final class PauseScreen extends BaseScreen {

  private final GameScreen gameScreen;

  /**
   * Creates a new {@code PauseScreen} given a {@link SpriteBatch} and a reference to the {@link
   * GameScreen}.
   *
   * @param spriteBatch the {@link SpriteBatch} to add sprites to
   * @param gameScreen reference to the {@link GameScreen}
   */
  PauseScreen(SpriteBatch spriteBatch, GameScreen gameScreen) {
    super(spriteBatch);
    this.gameScreen = gameScreen;
    setBackground();
    addMenu();
  }

  private void setBackground() {
    stage.addActor(new Image(Assets.getBackground()));
  }

  private void addMenu() {
    Table menu = Utils.createMenu();
    addResumeButton(menu);
    addRestartButton(menu);
    addSettingsButton(menu);
    addExitButton(menu);
    stage.addActor(menu);
  }

  private void addResumeButton(Table menu) {
    menu.row();
    TextButton resumeButton = Utils.createTextButton(Assets.getMediumFont(), "Resume");
    addResumeButtonListener(resumeButton);
    menu.add(resumeButton);
  }

  private void addResumeButtonListener(TextButton resumeButton) {
    resumeButton.addListener(new ChangeListener() {

      @Override
      public void changed(ChangeEvent event, Actor actor) {
        ScreenManager.switchToPreviousScreen();
      }

    });
  }

  private void addRestartButton(Table menu) {
    menu.row().padTop(COMPONENT_SPACING);
    TextButton restartButton = Utils.createTextButton(Assets.getMediumFont(), "Restart");
    addRestartButtonListener(restartButton);
    menu.add(restartButton);
  }

  private void addRestartButtonListener(TextButton restartButton) {
    restartButton.addListener(new ChangeListener() {

      @Override
      public void changed(ChangeEvent event, Actor actor) {
        ScreenManager.switchToPreviousScreen();
        gameScreen.newGame();
      }

    });
  }

  private void addSettingsButton(Table menu) {
    menu.row().padTop(COMPONENT_SPACING);
    TextButton settingsButton = Utils.createTextButton(Assets.getMediumFont(), "Settings");
    addSettingsButtonListener(settingsButton);
    menu.add(settingsButton);
  }

  private void addSettingsButtonListener(TextButton settingsButton) {
    settingsButton.addListener(new ChangeListener() {

      @Override
      public void changed(ChangeEvent event, Actor actor) {
        ScreenManager.setScreen(new SettingsScreen(spriteBatch));
      }

    });
  }

  private void addExitButton(Table menu) {
    menu.row().padTop(COMPONENT_SPACING);
    TextButton exitButton = Utils.createTextButton(Assets.getMediumFont(), "Exit");
    addExitButtonListener(exitButton);
    menu.add(exitButton);
  }

  private void addExitButtonListener(TextButton exitButton) {
    exitButton.addListener(new ChangeListener() {

      @Override
      public void changed(ChangeEvent event, Actor actor) {
        ScreenManager.disposeAndClearAllScreens();
        ScreenManager.setScreen(new MainMenuScreen(spriteBatch));
      }

    });
  }
}
