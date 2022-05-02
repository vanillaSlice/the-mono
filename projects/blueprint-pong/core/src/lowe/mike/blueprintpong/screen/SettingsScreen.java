package lowe.mike.blueprintpong.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import lowe.mike.blueprintpong.Assets;
import lowe.mike.blueprintpong.Difficulty;
import lowe.mike.blueprintpong.GamePreferences;

/**
 * Settings screen to show settings that the user can change.
 *
 * @author Mike Lowe
 */
final class SettingsScreen extends BaseScreen {

  private static final String SETTINGS_LABEL_TEXT = "Settings";
  private static final String DIFFICULTY_LABEL_TEXT = "Difficulty";
  private static final String SOUNDS_LABEL_TEXT = "Sounds";
  private static final String ON_BUTTON_TEXT = "On";
  private static final String OFF_BUTTON_TEXT = "Off";

  /**
   * Creates a new {@code SettingsScreen} given {@link Assets}, a {@link SpriteBatch} and a {@link
   * ScreenManager}.
   *
   * @param assets {@link Assets} containing assets used in the {@link Screen}
   * @param spriteBatch {@link SpriteBatch} to add sprites to
   * @param screenManager the {@link ScreenManager} used to manage game {@link Screen}s
   */
  SettingsScreen(Assets assets, SpriteBatch spriteBatch, ScreenManager screenManager) {
    super(assets, spriteBatch, screenManager);
    Table menu = createMenu();
    this.stage.addActor(menu);
  }

  private Table createMenu() {
    Table table = new Table();
    table.setFillParent(true);
    table.center();

    // number of columns in table = number difficulty buttons + difficulty label
    int colSpan = Difficulty.values().length + 1;

    // add settings label
    table.row();
    Label settingsLabel = ScreenUtils.createLabel(assets.getLargeFont(), SETTINGS_LABEL_TEXT);
    table.add(settingsLabel).expandX().colspan(colSpan);

    // add difficulty buttons
    table.row();
    Label difficultyLabel = ScreenUtils.createLabel(assets.getMediumFont(), DIFFICULTY_LABEL_TEXT);
    ButtonGroup<TextButton> difficultyButtonGroup = ScreenUtils.createDifficultyButtonGroup(assets);
    addButtonGroup(table, difficultyLabel, difficultyButtonGroup);

    // add sound buttons
    table.row().padTop(COMPONENT_SPACING);
    Label soundsLabel = ScreenUtils.createLabel(assets.getMediumFont(), SOUNDS_LABEL_TEXT);
    ButtonGroup<TextButton> soundButtonGroup = createSoundButtonGroup();
    addButtonGroup(table, soundsLabel, soundButtonGroup);

    // add back button
    table.row().padTop(COMPONENT_SPACING);
    TextButton backButton = ScreenUtils.createBackButton(assets, screenManager);
    table.add(backButton).expandX().colspan(colSpan);

    return table;
  }

  private ButtonGroup<TextButton> createSoundButtonGroup() {
    Array<TextButton> buttons = createSoundButtons();
    ButtonGroup<TextButton> buttonGroup = new ButtonGroup<TextButton>(buttons.toArray());
    buttonGroup.setMaxCheckCount(1);
    return buttonGroup;
  }

  private Array<TextButton> createSoundButtons() {
    Array<TextButton> buttons = new Array<TextButton>(TextButton.class);

    boolean playSounds = GamePreferences.shouldPlaySounds();

    TextButton onButton = ScreenUtils.createTextButton(assets, ON_BUTTON_TEXT);
    TextButton offButton = ScreenUtils.createTextButton(assets, OFF_BUTTON_TEXT);

    onButton.setChecked(playSounds);
    offButton.setChecked(!playSounds);

    addSoundButtonListener(onButton, true);
    addSoundButtonListener(offButton, false);

    buttons.addAll(onButton, offButton);

    return buttons;
  }

  private void addSoundButtonListener(final TextButton button, final boolean playSounds) {
    button.addListener(new ChangeListener() {

      @Override
      public void changed(ChangeEvent event, Actor actor) {
        if (button.isChecked()) {
          GamePreferences.setPlaySounds(playSounds);
        }
      }

    });
  }

  private void addButtonGroup(Table table, Label label, ButtonGroup<TextButton> buttonGroup) {
    table.add(label).expandX().align(Align.center);
    for (TextButton button : buttonGroup.getButtons()) {
      table.add(button).expandX().align(Align.left);
    }
  }
}
