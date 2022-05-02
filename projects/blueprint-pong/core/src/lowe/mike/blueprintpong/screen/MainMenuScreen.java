package lowe.mike.blueprintpong.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import lowe.mike.blueprintpong.Assets;
import lowe.mike.blueprintpong.BlueprintPongGame;

/**
 * Main menu screen to show when the game is first opened.
 *
 * @author Mike Lowe
 */
final class MainMenuScreen extends BaseScreen {

  private static final String PLAY_BUTTON_TEXT = "Play";

  /**
   * Creates a new {@code MainMenuScreen} given {@link Assets}, a {@link SpriteBatch} and a {@link
   * ScreenManager}.
   *
   * @param assets {@link Assets} containing assets used in the {@link Screen}
   * @param spriteBatch {@link SpriteBatch} to add sprites to
   * @param screenManager the {@link ScreenManager} used to manage game {@link Screen}s
   */
  MainMenuScreen(Assets assets, SpriteBatch spriteBatch, ScreenManager screenManager) {
    super(assets, spriteBatch, screenManager);
    Table menu = createMenu();
    this.stage.addActor(menu);
  }

  private Table createMenu() {
    Table table = new Table();
    table.setFillParent(true);
    table.center();

    // add title
    table.row();
    Label titleLabel = ScreenUtils.createLabel(assets.getExtraLargeFont(), BlueprintPongGame.TITLE);
    table.add(titleLabel).expandX();

    // add play button
    table.row();
    TextButton playButton = createPlayButton();
    table.add(playButton).expandX();

    // add settings button
    table.row().padTop(COMPONENT_SPACING);
    TextButton settingsButton =
        ScreenUtils.createSettingsButton(assets, spriteBatch, screenManager);
    table.add(settingsButton).expandX();

    return table;
  }

  private TextButton createPlayButton() {
    TextButton button = ScreenUtils.createTextButton(assets, PLAY_BUTTON_TEXT);
    addPlayButtonListener(button);
    return button;
  }

  private void addPlayButtonListener(final TextButton button) {
    button.addListener(new ChangeListener() {

      @Override
      public void changed(ChangeEvent event, Actor actor) {
        if (button.isChecked()) {
          switchToDifficultyScreen();
          button.setChecked(false);
        }
      }

    });

  }

  private void switchToDifficultyScreen() {
    // don't dispose this screen because we want to be able to return to it
    // from the next screen
    screenManager.setScreen(new DifficultyScreen(assets, spriteBatch, screenManager));
  }
}
