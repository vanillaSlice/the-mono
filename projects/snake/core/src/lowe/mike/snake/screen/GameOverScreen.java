package lowe.mike.snake.screen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import java.util.Locale;
import lowe.mike.snake.util.Assets;
import lowe.mike.snake.util.ScreenManager;
import lowe.mike.snake.util.State;
import lowe.mike.snake.util.Utils;

/**
 * Screen to show when the game is over.
 *
 * @author Mike Lowe
 */
final class GameOverScreen extends BaseScreen {

  private final GameScreen gameScreen;

  /**
   * Creates a new {@code GameOverScreen} given a {@link SpriteBatch} and a reference to the {@link
   * GameScreen}.
   *
   * @param spriteBatch the {@link SpriteBatch} to add sprites to
   * @param gameScreen reference to the {@link GameScreen}
   */
  GameOverScreen(SpriteBatch spriteBatch, GameScreen gameScreen) {
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
    addGameOverLabel(menu);
    addYourScoreLabels(menu);
    addHighScoreLabels(menu);
    addNewGameButton(menu);
    addExitButton(menu);
    stage.addActor(menu);
  }

  private void addGameOverLabel(Table menu) {
    menu.row().padBottom(COMPONENT_SPACING);
    Label gameOverLabel = Utils.createTextLabel(Assets.getSmallFont(), "Game Over");
    menu.add(gameOverLabel);
  }

  private void addYourScoreLabels(Table menu) {
    menu.row().padTop(COMPONENT_SPACING);
    Label yourScoreLabel = Utils.createTextLabel(Assets.getSmallFont(), "Your Score");
    menu.add(yourScoreLabel);
    menu.row();
    Label scoreLabel = Utils.createTextLabel(
        Assets.getSmallFont(),
        String.format(Locale.ENGLISH, "%04d", State.getCurrentScore())
    );
    menu.add(scoreLabel);
  }

  private void addHighScoreLabels(Table menu) {
    menu.row().padTop(COMPONENT_SPACING);
    Label highScoreLabel = Utils.createTextLabel(Assets.getSmallFont(), "High Score");
    menu.add(highScoreLabel);
    menu.row().padBottom(COMPONENT_SPACING);
    Label scoreLabel = Utils.createTextLabel(
        Assets.getSmallFont(),
        String.format(Locale.ENGLISH, "%04d", State.getHighScore())
    );
    menu.add(scoreLabel);
  }

  private void addNewGameButton(Table menu) {
    menu.row().padTop(COMPONENT_SPACING);
    TextButton newGameButton = Utils.createTextButton(Assets.getSmallFont(), "New Game");
    addNewGameButtonListener(newGameButton);
    menu.add(newGameButton);
  }

  private void addNewGameButtonListener(TextButton newGameButtonListener) {
    newGameButtonListener.addListener(new ChangeListener() {

      @Override
      public void changed(ChangeEvent event, Actor actor) {
        ScreenManager.switchToPreviousScreen();
        gameScreen.newGame();
      }

    });
  }

  private void addExitButton(Table menu) {
    menu.row().padTop(COMPONENT_SPACING);
    TextButton exitButton = Utils.createTextButton(Assets.getSmallFont(), "Exit");
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
