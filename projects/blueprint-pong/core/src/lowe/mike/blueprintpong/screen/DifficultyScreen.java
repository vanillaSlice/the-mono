package lowe.mike.blueprintpong.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import lowe.mike.blueprintpong.Assets;

/**
 * Difficulty screen to show just before the player enters the game.
 *
 * @author Mike Lowe
 */
final class DifficultyScreen extends BaseScreen {

  private static final String DIFFICULTY_LABEL_TEXT = "Select Difficulty";
  private static final String PLAY_BUTTON_TEXT = "Play";

  /**
   * Creates a new {@code DifficultyScreen} given {@link Assets}, a {@link SpriteBatch} and a {@link
   * ScreenManager}.
   *
   * @param assets {@link Assets} containing assets used in the {@link Screen}
   * @param spriteBatch {@link SpriteBatch} to add sprites to
   * @param screenManager the {@link ScreenManager} used to manage game {@link Screen}s
   */
  DifficultyScreen(Assets assets, SpriteBatch spriteBatch, ScreenManager screenManager) {
    super(assets, spriteBatch, screenManager);
    Table menu = createMenu();
    this.stage.addActor(menu);
  }

  private Table createMenu() {
    Table table = new Table();
    table.setFillParent(true);
    table.center();

    // add difficulty label
    table.row();
    Label difficultyLabel = ScreenUtils.createLabel(assets.getLargeFont(), DIFFICULTY_LABEL_TEXT);
    table.add(difficultyLabel).expandX();

    // add difficulty buttons
    table.row().padBottom(COMPONENT_SPACING);
    HorizontalGroup difficultyButtonGroup = createDifficultyButtonGroup();
    table.add(difficultyButtonGroup).expandX();

    // add back and play buttons
    table.row().padTop(COMPONENT_SPACING);
    HorizontalGroup backAndPlayButtonGroup = createBackAndPlayButtonGroup();
    table.add(backAndPlayButtonGroup).expandX();

    return table;
  }

  private HorizontalGroup createDifficultyButtonGroup() {
    HorizontalGroup group = new HorizontalGroup();
    group.space(COMPONENT_SPACING);
    Array<TextButton> buttons = ScreenUtils.createDifficultyButtonGroup(assets).getButtons();
    for (TextButton button : buttons) {
      group.addActor(button);
    }
    return group;
  }

  private HorizontalGroup createBackAndPlayButtonGroup() {
    HorizontalGroup group = new HorizontalGroup();
    group.space(COMPONENT_SPACING);
    TextButton backButton = ScreenUtils.createBackButton(assets, screenManager);
    group.addActor(backButton);
    TextButton playButton = createPlayButton();
    group.addActor(playButton);
    return group;
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
          switchToGameScreen();
          button.setChecked(false);
        }
      }

    });
  }

  private void switchToGameScreen() {
    // dispose this screen and all previous screens because we won't be able to return from the
    // next screen
    screenManager.disposeAndClearAllScreens();
    screenManager.setScreen(new GameScreen(assets, spriteBatch, screenManager));
  }
}
