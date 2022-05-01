package lowe.mike.snake.screen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import lowe.mike.snake.SnakeGame;
import lowe.mike.snake.util.Assets;
import lowe.mike.snake.util.ScreenManager;
import lowe.mike.snake.util.State;
import lowe.mike.snake.util.Utils;
import lowe.mike.snake.world.Level;

/**
 * Settings screen to show settings that the user can change.
 *
 * @author Mike Lowe
 */
final class SettingsScreen extends BaseScreen {

  private static final int COL_SPAN = 3;

  /**
   * Creates a new {@code SettingsScreen} given a {@link SpriteBatch}.
   *
   * @param spriteBatch the {@link SpriteBatch} to add sprites to
   */
  SettingsScreen(SpriteBatch spriteBatch) {
    super(spriteBatch);
    setBackground();
    addMenu();
  }

  private void setBackground() {
    stage.addActor(new Image(Assets.getBackground()));
  }

  private void addMenu() {
    Table menu = Utils.createMenu();
    addMusicLabel(menu);
    addMusicButtons(menu);
    addLevelLabel(menu);
    addLevelButtons(menu);
    addBackButton(menu);
    stage.addActor(menu);
  }

  private void addMusicLabel(Table menu) {
    menu.row();
    Label musicLabel = Utils.createTextLabel(Assets.getMediumFont(), "Music");
    menu.add(musicLabel).expandX().colspan(COL_SPAN);
  }

  private void addMusicButtons(Table menu) {
    menu.row().padBottom(COMPONENT_SPACING);
    Group musicButtonGroup = createMusicButtonGroup();
    menu.add(musicButtonGroup).expandX().colspan(COL_SPAN);
  }

  private Group createMusicButtonGroup() {
    HorizontalGroup horizontalGroup = new HorizontalGroup();
    horizontalGroup.space(COMPONENT_SPACING * 2);

    // create the buttons
    boolean shouldPlayMusic = State.shouldPlayMusic();
    TextButton onButton = Utils.createCheckableTextButton(Assets.getMediumFont(), "On");
    TextButton offButton = Utils.createCheckableTextButton(Assets.getMediumFont(), "Off");

    // add to button group
    ButtonGroup<TextButton> buttonGroup = new ButtonGroup<TextButton>();
    buttonGroup.setMaxCheckCount(1);
    buttonGroup.add(onButton);
    buttonGroup.add(offButton);

    // add to horizontal group
    horizontalGroup.addActor(onButton);
    horizontalGroup.addActor(offButton);

    // check which button should be checked
    onButton.setChecked(shouldPlayMusic);
    offButton.setChecked(!shouldPlayMusic);

    // add button listeners
    addMusicButtonListener(onButton, true);
    addMusicButtonListener(offButton, false);

    return horizontalGroup;
  }

  private void addMusicButtonListener(final TextButton musicButton,
      final boolean shouldPlayMusic) {
    musicButton.addListener(new ChangeListener() {

      @Override
      public void changed(ChangeEvent event, Actor actor) {
        if (musicButton.isChecked()) {
          State.setShouldPlayMusic(shouldPlayMusic);
          Utils.playMusic(Assets.getMusic(), shouldPlayMusic);
        }
      }

    });
  }

  private void addLevelLabel(Table menu) {
    menu.row().padTop(COMPONENT_SPACING);
    Label levelLabel = Utils.createTextLabel(Assets.getMediumFont(), "Level");
    menu.add(levelLabel).expandX().colspan(COL_SPAN);
  }

  private void addLevelButtons(Table menu) {
    menu.row().padBottom(COMPONENT_SPACING);
    Label numberLabel = Utils.createNumberLabel(Assets.getMediumFont(), State.getLevel());

    float width = SnakeGame.WIDTH / COL_SPAN;

    // create left arrow
    ImageButton leftArrowButton = createArrowButton(
        Assets.getSmallLeftArrow(),
        Assets.getSmallLeftArrowPressed(),
        numberLabel,
        -1
    );
    leftArrowButton.align(Align.right);
    menu.add(leftArrowButton).width(width);

    // add number label in the middle
    menu.add(numberLabel).width(width);

    // create right arrow
    ImageButton rightArrowButton = createArrowButton(
        Assets.getSmallRightArrow(),
        Assets.getSmallRightArrowPressed(),
        numberLabel,
        1
    );
    rightArrowButton.align(Align.left);
    menu.add(rightArrowButton).width(width);
  }

  private ImageButton createArrowButton(TextureRegion up,
      TextureRegion down,
      Label numberLabel,
      int levelIncrement) {
    ImageButton arrowButton = Utils.createImageButton(up, down);
    addArrowButtonListener(arrowButton, numberLabel, levelIncrement);
    return arrowButton;
  }

  private void addArrowButtonListener(ImageButton arrowButton,
      final Label numberLabel,
      final int levelIncrement) {
    arrowButton.addListener(new ChangeListener() {

      @Override
      public void changed(ChangeEvent event, Actor actor) {
        int level = State.getLevel() + levelIncrement;
        if (level >= Level.MINIMUM && level <= Level.MAXIMUM) {
          State.setLevel(level);
          Utils.updateNumberLabel(numberLabel, level);
        }
      }

    });
  }

  private void addBackButton(Table menu) {
    menu.row().padTop(COMPONENT_SPACING);
    TextButton backButton = createBackButton();
    menu.add(backButton).expandX().colspan(COL_SPAN);
  }

  private TextButton createBackButton() {
    TextButton backButton = Utils.createTextButton(Assets.getMediumFont(), "Back");
    addBackButtonListener(backButton);
    return backButton;
  }

  private void addBackButtonListener(TextButton backButton) {
    backButton.addListener(new ChangeListener() {

      @Override
      public void changed(ChangeEvent event, Actor actor) {
        ScreenManager.switchToPreviousScreen();
      }

    });
  }
}
