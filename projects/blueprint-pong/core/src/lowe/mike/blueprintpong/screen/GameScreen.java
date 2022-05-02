package lowe.mike.blueprintpong.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import lowe.mike.blueprintpong.Assets;
import lowe.mike.blueprintpong.BlueprintPongGame;
import lowe.mike.blueprintpong.GamePreferences;
import lowe.mike.blueprintpong.Scaling;
import lowe.mike.blueprintpong.actor.Ball;
import lowe.mike.blueprintpong.actor.Paddle;

/**
 * Screen to show when the game is being played.
 *
 * @author Mike Lowe
 */
final class GameScreen extends BaseScreen {

  private static final String PAUSE_BUTTON_TEXT = "Pause";
  private static final float PADDLE_OFFSET = 20f;
  private static final float PLAYER_PADDLE_SPEED = 200f; // in units per second
  private static final int WINNING_SCORE = 11;
  private static final float SOUND_VOLUME = .2f;

  /*
   * Ball speed changes dependent on where a paddle is hit (in units per second)
   */
  private static final float[] BALL_SPEEDS = {240f, 230f, 225f, 220f, 220f, 225f, 230f, 240f};
  /*
   * Ball angle changes dependent on where a paddle is hit
   */
  private static final float[] BALL_ANGLES = {220f, 205f, 190f, 180f, 180f, 170f, 155f, 140f};

  private final TextButton pauseButton;
  private final Label playerScoreLabel;
  private final Label computerScoreLabel;
  private final Ball ball;
  private final Paddle playerPaddle;
  private final Paddle computerPaddle;
  private boolean playSounds;
  private int playerScore;
  private int computerScore;
  private boolean gameOver;
  private boolean hitWall;
  private boolean hitPaddle;

  /**
   * Creates a new {@code GameScreen} given {@link Assets}, a {@link SpriteBatch} and a {@link
   * ScreenManager}.
   *
   * @param assets {@link Assets} containing assets used in the {@link Screen}
   * @param spriteBatch {@link SpriteBatch} to add sprites to
   * @param screenManager the {@link ScreenManager} used to manage game {@link Screen}s
   */
  GameScreen(Assets assets, SpriteBatch spriteBatch, ScreenManager screenManager) {
    super(assets, spriteBatch, screenManager);
    Image line = createLine();
    this.pauseButton = createPauseButton();
    this.playerScoreLabel = ScreenUtils.createPlayerScoreLabel(this.assets, 0);
    this.computerScoreLabel = ScreenUtils.createComputerScoreLabel(this.assets, 0);
    this.ball = new Ball(this.assets.getBallTexture());
    this.playerPaddle = createPaddle();
    this.playerPaddle.setSpeed(PLAYER_PADDLE_SPEED);
    this.computerPaddle = createPaddle();
    this.stage.addActor(line);
    this.stage.addActor(this.playerScoreLabel);
    this.stage.addActor(this.computerScoreLabel);
    this.stage.addActor(this.ball);
    this.stage.addActor(this.playerPaddle);
    this.stage.addActor(this.computerPaddle);
    this.stage.addActor(this.pauseButton);
    newGame();
  }

  private Image createLine() {
    Image line = new Image(assets.getLineTexture());
    line.setX(BlueprintPongGame.VIRTUAL_WIDTH / 2f);
    Scaling.scaleActor(line);
    return line;
  }

  private TextButton createPauseButton() {
    TextButton button = ScreenUtils.createTextButton(assets, PAUSE_BUTTON_TEXT);
    addPauseButtonListener(button);
    float x = (BlueprintPongGame.VIRTUAL_WIDTH / 2f) - (button.getWidth() / 2f);
    float y = BlueprintPongGame.VIRTUAL_HEIGHT - button.getHeight() - COMPONENT_SPACING;
    button.setPosition(x, y);
    return button;
  }

  private void addPauseButtonListener(final TextButton button) {
    button.addListener(new ChangeListener() {

      @Override
      public void changed(ChangeEvent event, Actor actor) {
        if (button.isChecked()) {
          switchToPauseScreen();
          button.setChecked(false);
        }
      }

    });
  }

  private void switchToPauseScreen() {
    // don't dispose this screen because we want to be able to return to it
    // from the next screen
    screenManager.setScreen(new PauseScreen(assets, spriteBatch, screenManager, this));
  }

  private Paddle createPaddle() {
    return new Paddle(assets.getPaddleTexture());
  }

  void newGame() {
    updatePreferences();
    playerScore = 0;
    updatePlayerScoreLabel();
    computerScore = 0;
    updateComputerScoreLabel();
    gameOver = false;
    resetPlayerPaddle();
    resetComputerPaddle();
    newRound(MathUtils.randomBoolean());
  }

  private void updatePreferences() {
    computerPaddle.setSpeed(GamePreferences.getDifficulty().getComputerPaddleSpeed());
    playSounds = GamePreferences.shouldPlaySounds();
  }

  private void updatePlayerScoreLabel() {
    ScreenUtils.updatePlayerScoreLabel(playerScoreLabel, playerScore);
  }

  private void updateComputerScoreLabel() {
    ScreenUtils.updateComputerScoreLabel(computerScoreLabel, computerScore);
  }

  private void resetPlayerPaddle() {
    resetPaddle(playerPaddle, PADDLE_OFFSET);
  }

  private void resetComputerPaddle() {
    float x = BlueprintPongGame.VIRTUAL_WIDTH - PADDLE_OFFSET - computerPaddle.getScaledWidth();
    resetPaddle(computerPaddle, x);
  }

  private void resetPaddle(Paddle paddle, float x) {
    float y = (BlueprintPongGame.VIRTUAL_HEIGHT / 2f) - (paddle.getScaledHeight() / 2f);
    paddle.setPosition(x, y);
    paddle.setTargetY(y);
  }

  private void newRound(boolean serveToPlayer) {
    setRandomBallPosition();
    setRandomBallAngle(serveToPlayer);
    ball.setSpeed(BALL_SPEEDS[3]);
  }

  private void setRandomBallPosition() {
    float x = (BlueprintPongGame.VIRTUAL_WIDTH / 2f) - (ball.getScaledWidth() / 2f);
    float y = MathUtils.random(0, BlueprintPongGame.VIRTUAL_HEIGHT - ball.getScaledHeight());
    ball.setPosition(x, y);
  }

  private void setRandomBallAngle(boolean serveToPlayer) {
    float angle = BALL_ANGLES[MathUtils.random(BALL_ANGLES.length - 1)];
    if (serveToPlayer) {
      angle = reflectAngleInYAxis(angle);
    }
    ball.setAngle(angle);
  }

  private float reflectAngleInYAxis(float angle) {
    return (540f - angle) % 360f;
  }

  private float reflectAngleInXAxis(float angle) {
    return (720f - angle) % 360f;
  }

  void resumeGame() {
    updatePreferences();
  }

  @Override
  void update(float delta) {
    if (gameOver) {
      switchToGameOverScreen();
    } else {
      handleUserInput(delta);
      ball.updatePosition(delta);
      updateComputerPaddlePosition(delta);
      handleCollisions();
      updateScore();
    }
  }

  private void switchToGameOverScreen() {
    // don't dispose this screen because we want to be able to return to it
    // from the next screen
    screenManager.setScreen(new GameOverScreen(assets, spriteBatch, screenManager, this));
  }

  private void handleUserInput(float delta) {
    if (pauseButton.isPressed()) {
      return;
    } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
      playerPaddle.moveUp(delta);
    } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
      playerPaddle.moveDown(delta);
    } else if (Gdx.input.isTouched()) {
      Vector2 touchCoordinates = new Vector2(0, Gdx.input.getY());
      touchCoordinates = stage.getViewport().unproject(touchCoordinates);
      float y = touchCoordinates.y - (playerPaddle.getScaledHeight() / 2f);
      playerPaddle.setTargetY(y);
    }
    playerPaddle.updatePosition(delta);
    ensurePaddleIsInBounds(playerPaddle);
  }

  private void ensurePaddleIsInBounds(Paddle paddle) {
    if (paddle.getY() < 0) {
      paddle.setY(0);
    } else if (paddle.getY() + paddle.getScaledHeight() > BlueprintPongGame.VIRTUAL_HEIGHT) {
      float y = BlueprintPongGame.VIRTUAL_HEIGHT - paddle.getScaledHeight();
      paddle.setY(y);
    }
  }

  /*
   * Computer paddle follows the ball.
   */
  private void updateComputerPaddlePosition(float delta) {
    float y = ball.getY() + (ball.getScaledHeight() / 2f) - (computerPaddle.getScaledHeight() / 2f);
    computerPaddle.setTargetY(y);
    computerPaddle.updatePosition(delta);
    ensurePaddleIsInBounds(computerPaddle);
  }

  private void handleCollisions() {
    handlePaddleCollision();
    handleWallCollision();
  }

  private void handlePaddleCollision() {
    boolean hitPlayerPaddle = hitPaddle(playerPaddle);
    boolean hitComputerPaddle = hitPaddle(computerPaddle);
    if (!hitPaddle && hitPlayerPaddle) {
      int sectionHit = getPaddleSectionHit(playerPaddle);
      float angle = BALL_ANGLES[sectionHit];
      float speed = BALL_SPEEDS[sectionHit];
      ball.setAngle(angle);
      ball.setSpeed(speed);
      playPaddleHitSound();
      hitPaddle = true;
    } else if (!hitPaddle && hitComputerPaddle) {
      int sectionHit = getPaddleSectionHit(computerPaddle);
      float angle = reflectAngleInYAxis(BALL_ANGLES[sectionHit]);
      float speed = BALL_SPEEDS[sectionHit];
      ball.setAngle(angle);
      ball.setSpeed(speed);
      playPaddleHitSound();
      hitPaddle = true;
    } else if (!hitPlayerPaddle && !hitComputerPaddle) {
      hitPaddle = false;
    }
  }

  private boolean hitPaddle(Paddle paddle) {
    float ballLeft = ball.getX();
    float ballRight = ballLeft + ball.getScaledWidth();
    float ballBottom = ball.getY();
    float ballTop = ballBottom + ball.getScaledHeight();
    float paddleLeft = paddle.getX();
    float paddleRight = paddleLeft + paddle.getScaledWidth();
    float paddleBottom = paddle.getY();
    float paddleTop = paddleBottom + paddle.getScaledHeight();
    return ((ballLeft >= paddleLeft && ballLeft <= paddleRight)
        || (ballRight >= paddleLeft && ballRight <= paddleRight))
        && ((ballBottom >= paddleBottom && ballBottom <= paddleTop)
        || (ballTop >= paddleBottom && ballTop <= paddleTop));
  }

  private int getPaddleSectionHit(Paddle paddle) {
    float ballY = ball.getY();
    float paddleY = paddle.getY() + paddle.getSectionSize();
    for (int i = 0; i < Paddle.SECTIONS; i++) {
      if (ballY <= paddleY) {
        return i;
      }
      paddleY += paddle.getSectionSize();
    }
    return Paddle.SECTIONS - 1;
  }

  private void handleWallCollision() {
    boolean hitTopWall = hitTopWall();
    boolean hitBottomWall = hitBottomWall();
    if (!hitWall && hitTopWall) {
      float angle = reflectAngleInXAxis(ball.getAngle());
      float y = BlueprintPongGame.VIRTUAL_HEIGHT - ball.getScaledHeight();
      ball.setAngle(angle);
      ball.setY(y);
      playWallHitSound();
      hitWall = true;
    } else if (!hitWall && hitBottomWall) {
      float angle = reflectAngleInXAxis(ball.getAngle());
      float y = 0;
      ball.setAngle(angle);
      ball.setY(y);
      playWallHitSound();
      hitWall = true;
    } else if (!hitTopWall && !hitBottomWall) {
      hitWall = false;
    }
  }

  private boolean hitTopWall() {
    return ball.getY() + ball.getScaledHeight() >= BlueprintPongGame.VIRTUAL_HEIGHT;
  }

  private boolean hitBottomWall() {
    return ball.getY() <= 0;
  }

  private void playPaddleHitSound() {
    playSound(assets.getPaddleHitSound());
  }

  private void playWallHitSound() {
    playSound(assets.getWallHitSound());
  }

  private void playSound(Sound sound) {
    if (playSounds) {
      sound.play(SOUND_VOLUME);
    }
  }

  private void updateScore() {
    // has player scored
    if (ball.getX() > BlueprintPongGame.VIRTUAL_WIDTH) {
      playerScore++;
      updatePlayerScoreLabel();
      playPointScoredSound();
      newRound(true);
    }
    // has computer scored
    else if (ball.getX() < -ball.getScaledWidth()) {
      computerScore++;
      updateComputerScoreLabel();
      playPointScoredSound();
      newRound(false);
    }
    if (playerScore == WINNING_SCORE || computerScore == WINNING_SCORE) {
      gameOver = true;
    }
  }

  private void playPointScoredSound() {
    playSound(assets.getPointScoredSound());
  }

  /**
   * @return the computer score
   */
  int getComputerScore() {
    return computerScore;
  }

  /**
   * @return the player score
   */
  int getPlayerScore() {
    return playerScore;
  }

  @Override
  public void pause() {
    switchToPauseScreen();
  }
}
