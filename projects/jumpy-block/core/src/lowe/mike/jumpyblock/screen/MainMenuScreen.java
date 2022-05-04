package lowe.mike.jumpyblock.screen;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import lowe.mike.jumpyblock.Assets;

/**
 * Main menu screen to show when the game is first opened.
 *
 * @author Mike Lowe
 */
public final class MainMenuScreen extends AbstractScreen {

  private static final int TITLE_LABEL_FONT_SIZE = 56;
  private static final String TITLE_LABEL_TEXT = "Jumpy\nBlock";
  private static final int MESSAGE_LABEL_FONT_SIZE = 28;
  private static final String MESSAGE_LABEL_TEXT =
      Gdx.app.getType() == Application.ApplicationType.Android ?
          "Tap to play" :
          "Click or\npress space\nto play";
  private static final int SPACING = 50;

  private final Label titleLabel;
  private final Label messageLabel;

  /**
   * Creates a new {@code MainMenuScreen} given a {@link ScreenManager}, {@link Assets} and a {@link
   * SpriteBatch}.
   *
   * @param screenManager the {@link ScreenManager} used to manage game {@link Screen}s
   * @param assets {@link Assets} containing assets used in the {@link Screen}
   * @param spriteBatch {@link SpriteBatch} to add sprites to
   */
  public MainMenuScreen(ScreenManager screenManager, Assets assets, SpriteBatch spriteBatch) {
    super(screenManager, assets, spriteBatch);
    this.titleLabel = initialiseLabel(TITLE_LABEL_FONT_SIZE, TITLE_LABEL_TEXT);
    this.messageLabel = initialiseLabel(MESSAGE_LABEL_FONT_SIZE, MESSAGE_LABEL_TEXT);
    this.stage.addActor(this.titleLabel);
    this.stage.addActor(this.messageLabel);
  }

  @Override
  void handleUserInput() {
    if (isJustTouched() || isSpaceJustPressed()) {
      switchToGameScreen();
    }
  }

  private void switchToGameScreen() {
    GameScreen gameScreen = new GameScreen(screenManager, assets, spriteBatch);
    screenManager.setScreen(gameScreen);
  }

  @Override
  void update(float delta) {
    updateTitleLabelPosition();
    updateMessageLabelPosition();
  }

  /*
   * Center the label two thirds of the way up the screen.
   */
  private void updateTitleLabelPosition() {
    float x = (viewport.getWorldWidth() - titleLabel.getWidth()) * .5f;
    float y = (viewport.getWorldHeight() - titleLabel.getHeight()) * .666f;
    titleLabel.setPosition(x, y);
  }

  /*
   * Center the button just underneath the title label.
   */
  private void updateMessageLabelPosition() {
    float x = (viewport.getWorldWidth() - messageLabel.getWidth()) * .5f;
    float y = titleLabel.getY() - messageLabel.getHeight() - SPACING;
    messageLabel.setPosition(x, y);
  }
}
