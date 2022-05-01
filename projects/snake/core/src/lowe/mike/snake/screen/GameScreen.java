package lowe.mike.snake.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import java.util.Locale;
import lowe.mike.snake.SnakeGame;
import lowe.mike.snake.util.Assets;
import lowe.mike.snake.util.ScreenManager;
import lowe.mike.snake.util.State;
import lowe.mike.snake.util.Utils;
import lowe.mike.snake.world.World;

/**
 * Screen to show when the game is being played.
 *
 * @author Mike Lowe
 */
final class GameScreen extends BaseScreen {

  private static final float SCORE_LABEL_X = 20f;
  private static final float SCORE_LABEL_Y = 565.5f;
  private static final float UP_BUTTON_X = 148f;
  private static final float UP_BUTTON_Y = 166f;
  private static final float RIGHT_BUTTON_X = 216f;
  private static final float RIGHT_BUTTON_Y = 98f;
  private static final float DOWN_BUTTON_X = 148f;
  private static final float DOWN_BUTTON_Y = 30f;
  private static final float LEFT_BUTTON_X = 80f;
  private static final float LEFT_BUTTON_Y = 98f;
  private static final float PAUSE_BUTTON_X = 308f;
  private static final float PAUSE_BUTTON_Y = 198f;
  private static final float BONUS_LABEL_Y = 565.5f;

  private final World world;
  private final Label scoreLabel;
  private final Label bonusLabel;

  /**
   * Creates a new {@code GameScreen} given a {@link SpriteBatch}.
   *
   * @param spriteBatch the {@link SpriteBatch} to add sprites to
   */
  GameScreen(SpriteBatch spriteBatch) {
    super(spriteBatch);
    setBackground();
    this.world = new World(this.stage);
    addGameFrame();
    this.scoreLabel = createScoreLabel();
    this.stage.addActor(this.scoreLabel);
    addUpButton();
    addRightButton();
    addDownButton();
    addLeftButton();
    addPauseButton();
    this.bonusLabel = Utils.createTextLabel(Assets.getSmallFont(), "");
    initialise();
  }

  private void setBackground() {
    stage.addActor(new Image(Assets.getBackground()));
  }

  private void addGameFrame() {
    stage.addActor(new Image(Assets.getGameFrame()));
  }

  private Label createScoreLabel() {
    Label scoreLabel = Utils.createTextLabel(Assets.getSmallFont(), "0000");
    scoreLabel.setPosition(SCORE_LABEL_X, SCORE_LABEL_Y);
    return scoreLabel;
  }

  private void addUpButton() {
    ImageButton upButton =
        Utils.createImageButton(Assets.getLargeUpArrow(), Assets.getLargeUpArrowPressed());
    upButton.setPosition(UP_BUTTON_X, UP_BUTTON_Y);
    addUpButtonListener(upButton);
    stage.addActor(upButton);
  }

  private void addUpButtonListener(ImageButton upButton) {
    upButton.addListener(new InputListener() {

      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        world.setSnakeDirectionUp();
        return true;
      }
    });
  }

  private void addRightButton() {
    ImageButton rightButton =
        Utils.createImageButton(Assets.getLargeRightArrow(), Assets.getLargeRightArrowPressed());
    rightButton.setPosition(RIGHT_BUTTON_X, RIGHT_BUTTON_Y);
    addRightButtonListener(rightButton);
    stage.addActor(rightButton);
  }

  private void addRightButtonListener(ImageButton rightButton) {
    rightButton.addListener(new InputListener() {

      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        world.setSnakeDirectionRight();
        return true;
      }
    });
  }

  private void addDownButton() {
    ImageButton downButton =
        Utils.createImageButton(Assets.getLargeDownArrow(), Assets.getLargeDownArrowPressed());
    downButton.setPosition(DOWN_BUTTON_X, DOWN_BUTTON_Y);
    addDownButtonListener(downButton);
    stage.addActor(downButton);
  }

  private void addDownButtonListener(ImageButton downButton) {
    downButton.addListener(new InputListener() {

      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        world.setSnakeDirectionDown();
        return true;
      }
    });
  }

  private void addLeftButton() {
    ImageButton leftButton =
        Utils.createImageButton(Assets.getLargeLeftArrow(), Assets.getLargeLeftArrowPressed());
    leftButton.setPosition(LEFT_BUTTON_X, LEFT_BUTTON_Y);
    addLeftButtonListener(leftButton);
    stage.addActor(leftButton);
  }

  private void addLeftButtonListener(ImageButton leftButton) {
    leftButton.addListener(new InputListener() {

      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        world.setSnakeDirectionLeft();
        return true;
      }
    });
  }

  private void addPauseButton() {
    ImageButton pauseButton =
        Utils.createImageButton(Assets.getPause(), Assets.getPausePressed());
    pauseButton.setPosition(PAUSE_BUTTON_X, PAUSE_BUTTON_Y);
    addPauseButtonListener(pauseButton);
    stage.addActor(pauseButton);
  }

  private void addPauseButtonListener(ImageButton pauseButton) {
    pauseButton.addListener(new ChangeListener() {

      @Override
      public void changed(ChangeEvent event, Actor actor) {
        switchToPauseScreen();
      }

    });
  }

  private void switchToPauseScreen() {
    ScreenManager.setScreen(new PauseScreen(spriteBatch, this));
  }

  private void initialise() {
    State.setGameOver(false);
    State.setCurrentScore(0);
  }

  void newGame() {
    world.reset();
    initialise();
  }

  @Override
  void update(float delta) {
    // switch screens if game is over
    if (State.isGameOver()) {
      updateHighScore();
      ScreenManager.setScreen(new GameOverScreen(spriteBatch, this));
      return;
    }
    world.update(delta);
    handleUserInput();
    updateScoreLabel();
    updateBonusLabel();
  }

  private void updateHighScore() {
    if (State.getCurrentScore() > State.getHighScore()) {
      State.setHighScore(State.getCurrentScore());
    }
  }

  private void handleUserInput() {
    if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
      world.setSnakeDirectionUp();
    } else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
      world.setSnakeDirectionRight();
    } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
      world.setSnakeDirectionDown();
    } else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
      world.setSnakeDirectionLeft();
    }
  }

  private void updateScoreLabel() {
    scoreLabel.setText(String.format(Locale.ENGLISH, "%04d", State.getCurrentScore()));
    scoreLabel.pack();
  }

  private void updateBonusLabel() {
    if (world.isBonusFoodShowing()) {
      Utils.updateNumberLabel(bonusLabel, world.getBonusFoodTicksRemaining());
      bonusLabel.pack();
      bonusLabel
          .setPosition(SnakeGame.WIDTH - COMPONENT_SPACING - bonusLabel.getWidth(), BONUS_LABEL_Y);
      stage.addActor(bonusLabel);
    } else {
      bonusLabel.remove();
    }
  }
}
