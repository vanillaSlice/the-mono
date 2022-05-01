package lowe.mike.snake.screen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import lowe.mike.snake.SnakeGame;
import lowe.mike.snake.util.Assets;
import lowe.mike.snake.util.ScreenManager;
import lowe.mike.snake.util.State;
import lowe.mike.snake.util.Utils;

/**
 * Main menu screen to show when the game is first opened.
 *
 * @author Mike Lowe
 */
final class MainMenuScreen extends BaseScreen {

  /**
   * Creates a new {@code MainMenuScreen} given a {@link SpriteBatch}.
   *
   * @param spriteBatch the {@link SpriteBatch} to add sprites to
   */
  MainMenuScreen(SpriteBatch spriteBatch) {
    super(spriteBatch);
    setBackground();
    addMenu();
    playMusic();
  }

  private void setBackground() {
    stage.addActor(new Image(Assets.getBackground()));
  }

  private void addMenu() {
    Table menu = Utils.createMenu();
    addTitleLabel(menu);
    addPlayButton(menu);
    addSettingsButton(menu);
    stage.addActor(menu);
  }

  private void addTitleLabel(Table menu) {
    menu.row().padBottom(COMPONENT_SPACING);
    Label titleLabel = Utils.createTextLabel(Assets.getLargeFont(), SnakeGame.TITLE);
    menu.add(titleLabel).expandX();
  }

  private void addPlayButton(Table menu) {
    menu.row().padTop(COMPONENT_SPACING);
    TextButton playButton = createPlayButton();
    menu.add(playButton).expandX();
  }

  private TextButton createPlayButton() {
    TextButton playButton = Utils.createTextButton(Assets.getMediumFont(), "Play");
    addPlayButtonListener(playButton);
    return playButton;
  }

  private void addPlayButtonListener(TextButton playButton) {
    playButton.addListener(new ChangeListener() {

      @Override
      public void changed(ChangeEvent event, Actor actor) {
        switchToGameScreen();
      }

    });
  }

  private void switchToGameScreen() {
    // dispose this screen and all previous screens because we won't be able to return from the
    // next screen
    ScreenManager.disposeAndClearAllScreens();
    ScreenManager.setScreen(new GameScreen(spriteBatch));
  }

  private void addSettingsButton(Table menu) {
    menu.row().padTop(COMPONENT_SPACING);
    TextButton settingsButton = createSettingsButton();
    menu.add(settingsButton).expandX();
  }

  private TextButton createSettingsButton() {
    TextButton settingsButton = Utils.createTextButton(Assets.getMediumFont(), "Settings");
    addSettingsButtonListener(settingsButton);
    return settingsButton;
  }

  private void addSettingsButtonListener(TextButton settingsButton) {
    settingsButton.addListener(new ChangeListener() {

      @Override
      public void changed(ChangeEvent event, Actor actor) {
        switchToSettingsScreen();
      }

    });
  }

  private void switchToSettingsScreen() {
    // don't dispose this screen because we want to be able to return to it
    // from the next screen
    ScreenManager.setScreen(new SettingsScreen(spriteBatch));
  }

  private void playMusic() {
    Utils.playMusic(Assets.getMusic(), State.shouldPlayMusic());
  }
}
