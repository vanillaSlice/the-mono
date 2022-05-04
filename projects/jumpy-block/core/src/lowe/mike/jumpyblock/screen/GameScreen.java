package lowe.mike.jumpyblock.screen;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import java.util.Locale;
import java.util.Random;
import lowe.mike.jumpyblock.Assets;
import lowe.mike.jumpyblock.JumpyBlockGame;
import lowe.mike.jumpyblock.actor.Block;
import lowe.mike.jumpyblock.actor.GroundSection;
import lowe.mike.jumpyblock.actor.Wall;

/**
 * Game screen to show when the game is being played.
 *
 * @author Mike Lowe
 */
final class GameScreen extends AbstractScreen {

  private static final String BEST_PREFERENCES_KEY = "best";
  private static final int SCORE_LABEL_FONT_SIZE = 36;
  private static final int SCORE_LABEL_Y_OFFSET = 50;
  private static final int GRAVITY = -25;
  private static final Vector2 BLOCK_STARTING_POSITION = new Vector2(50, 400);
  private static final int FIRST_WALL_X_POSITION = 400;
  private static final int MAXIMUM_WALL_Y_POSITION = -200;
  private static final int MINIMUM_WALL_Y_POSITION = -500;
  private static final int WALL_SPACING = 250;
  private static final Random RANDOM = new Random();
  private static final int GAME_OVER_LABEL_FONT_SIZE = 32;
  private static final String GAME_OVER_LABEL_TEXT = "Game Over";
  private static final int GAME_OVER_SCORE_LABEL_FONT_SIZE = 30;
  private static final String GAME_OVER_SCORE_LABEL_TEXT = "Score  %d";
  private static final int GAME_OVER_BEST_LABEL_FONT_SIZE = 30;
  private static final String GAME_OVER_BEST_LABEL_TEXT = "Best  %d";
  private static final int REPLAY_LABEL_FONT_SIZE = 24;
  private static final String REPLAY_LABEL_TEXT =
      Gdx.app.getType() == Application.ApplicationType.Android ?
          "Tap to replay" :
          "Click or\npress space\nto replay";
  private static final int LABEL_SPACING = 30;
  private static final float NEW_GAME_DELAY = .5f;

  private final Preferences preferences;
  private int best;
  private int score;
  private final Label scoreLabel;
  private final Block block;
  private final Group ground = new Group();
  private final Group walls = new Group();
  private final Label gameOverLabel;
  private final Label gameOverScoreLabel;
  private final Label gameOverBestLabel;
  private final Label replayLabel;
  private boolean gameOver;
  private float timeSinceDeath;

  /**
   * Creates a new {@code GameScreen} given a {@link ScreenManager}, {@link Assets} and a {@link
   * SpriteBatch}.
   *
   * @param screenManager the {@link ScreenManager} used to manage game {@link Screen}s
   * @param assets {@link Assets} containing assets used in the {@link Screen}
   * @param spriteBatch {@link SpriteBatch} to add sprites to
   */
  public GameScreen(ScreenManager screenManager, Assets assets, SpriteBatch spriteBatch) {
    super(screenManager, assets, spriteBatch);
    this.preferences = Gdx.app.getPreferences(JumpyBlockGame.TITLE);
    this.best = this.preferences.getInteger(BEST_PREFERENCES_KEY);
    this.scoreLabel = initialiseLabel(SCORE_LABEL_FONT_SIZE);
    this.block = new Block(assets.blockTexture);
    this.gameOverLabel = initialiseLabel(GAME_OVER_LABEL_FONT_SIZE, GAME_OVER_LABEL_TEXT);
    this.gameOverScoreLabel = initialiseLabel(GAME_OVER_SCORE_LABEL_FONT_SIZE);
    this.gameOverBestLabel = initialiseLabel(GAME_OVER_BEST_LABEL_FONT_SIZE);
    this.replayLabel = initialiseLabel(REPLAY_LABEL_FONT_SIZE, REPLAY_LABEL_TEXT);
    this.stage.addActor(this.walls);
    this.stage.addActor(this.ground);
    this.stage.addActor(this.block);
    this.stage.addActor(this.scoreLabel);
    this.stage.addActor(this.gameOverLabel);
    this.stage.addActor(this.gameOverScoreLabel);
    this.stage.addActor(this.gameOverBestLabel);
    this.stage.addActor(this.replayLabel);
    startNewGame();
  }

  private void startNewGame() {
    score = 0;
    scoreLabel.setVisible(true);
    resetBlock();
    resetGround();
    resetWalls();
    gameOverLabel.setVisible(false);
    gameOverScoreLabel.setVisible(false);
    gameOverBestLabel.setVisible(false);
    replayLabel.setVisible(false);
    gameOver = false;
    timeSinceDeath = 0;
  }

  /*
   * Reset block back to initial state.
   */
  private void resetBlock() {
    block.velocity.setZero();
    block.setPosition(BLOCK_STARTING_POSITION.x, BLOCK_STARTING_POSITION.y);
    block.addMomentum();
    block.isFalling = false;
    block.isDead = false;
  }

  /*
   * Shift all ground sections left to the beginning of the world.
   */
  private void resetGround() {
    float x = 0;
    for (Actor actor : ground.getChildren()) {
      actor.setPosition(x, 0);
      x += actor.getWidth();
    }
  }

  /*
   * Shifts all wall sections left to the beginning of the world.
   */
  private void resetWalls() {
    float x = FIRST_WALL_X_POSITION;
    for (Actor actor : walls.getChildren()) {
      Wall wall = (Wall) actor;
      wall.setPosition(x, getRandomWallYPosition());
      wall.isPassed = false;
      x += wall.getWidth() + WALL_SPACING;
    }
  }

  private int getRandomWallYPosition() {
    return MAXIMUM_WALL_Y_POSITION -
        (RANDOM.nextInt(MAXIMUM_WALL_Y_POSITION - MINIMUM_WALL_Y_POSITION));
  }

  @Override
  public void resize(int width, int height) {
    super.resize(width, height);
    addGroundSections();
    addWalls();
  }

  /*
   * Get number of ground sections needed to cover ground and add
   * sections accordingly.
   */
  private void addGroundSections() {
    int needed = (int) Math.ceil(camera.viewportWidth / assets.groundSectionTexture.getWidth()) + 1;
    while (needed > ground.getChildren().size) {
      addGroundSection();
    }
  }

  private void addGroundSection() {
    GroundSection groundSection = new GroundSection(assets.groundSectionTexture);
    repositionGroundSection(groundSection);
    ground.addActor(groundSection);
  }

  /*
   * Repositions ground section after the last ground section (if one exists).
   * For example, assume that [1, 2, 3] represents the sections that make up the ground
   * and a new section 4 is added. 4's position will then be set after 3.
   */
  private void repositionGroundSection(GroundSection groundSection) {
    float x = 0;
    if (ground.hasChildren()) {
      Actor last = ground.getChildren().peek();
      x = last.getX() + last.getWidth();
    }
    groundSection.setPosition(x, 0);
  }

  /*
   * Get number of walls needed to fill screen and add accordingly.
   */
  private void addWalls() {
    int needed =
        (int) Math.ceil(camera.viewportWidth / (assets.wallTexture.getWidth() + WALL_SPACING)) + 1;
    while (needed > walls.getChildren().size) {
      addWall();
    }
  }

  private void addWall() {
    Wall wall = new Wall(assets.wallTexture);
    repositionWall(wall);
    walls.addActor(wall);
  }

  /*
   * Repositions wall after the last wall (if one exists). For example, assume
   * that [1, 2, 3] represents the walls in the game and a new wall 4 is added.
   * 4's position will then be set after 3.
   */
  private void repositionWall(Wall wall) {
    float x = FIRST_WALL_X_POSITION;
    if (walls.hasChildren()) {
      Actor last = walls.getChildren().peek();
      x = last.getX() + last.getWidth() + WALL_SPACING;
    }
    wall.setPosition(x, getRandomWallYPosition());
  }

  @Override
  void handleUserInput() {
    if (isJustTouched() || isSpaceJustPressed()) {
      if (!gameOver) {
        block.jump();
      } else if (timeSinceDeath >= NEW_GAME_DELAY) {
        startNewGame();
      }
    }
  }

  @Override
  void update(float delta) {
    updateBlockPosition(delta);
    updateCamera();
    repositionGround();
    repositionWalls();
    handleCollisions();
    updateLabels();
    updateTimeSinceDeath(delta);
  }

  private void updateBlockPosition(float delta) {
    // block is dead so don't need to do anything
    if (block.isDead) {
      return;
    }

    // always add gravity
    block.velocity.y += GRAVITY;

    // calculate new position
    int x = (int) (block.getX() + (block.velocity.x * delta));
    int y = (int) (block.getY() + (block.velocity.y * delta));

    // don't let block go through the ceiling
    int ceiling = (int) (camera.viewportHeight - block.getHeight());
    if (y > ceiling) {
      y = ceiling;
    }

    block.setPosition(x, y);
  }

  private void updateCamera() {
    camera.position.x = (int) block.getX() + (camera.viewportWidth * .5f) - block.getWidth();
    camera.update();
  }

  /*
   * Makes the first ground section the last ground section if it has disappeared off screen.
   * For example, assume that [1, 2, 3, 4] represents the sections that make up the ground.
   * When 1 is no longer visible on the screen it will be moved to the end like so [2, 3, 4, 1].
   */
  private void repositionGround() {
    GroundSection first = (GroundSection) (ground.getChildren().first());
    if ((first.getX() + first.getWidth()) < (camera.position.x - (camera.viewportWidth / 2))) {
      repositionGroundSection(first);
      ground.removeActor(first);
      ground.addActor(first);
    }
  }

  /*
   * Makes the first wall the last wall if it has disappeared off screen. For example, assume
   * that [1, 2, 3, 4] represents the walls in the game. When 1 is no longer visible on the
   * screen it will be moved to the end like so [2, 3, 4, 1].
   */
  private void repositionWalls() {
    Wall first = (Wall) (walls.getChildren().first());
    if ((first.getX() + first.getWidth()) < (camera.position.x - (camera.viewportWidth / 2))) {
      repositionWall(first);
      first.isPassed = false;
      walls.removeActor(first);
      walls.addActor(first);
    }
  }

  private void handleCollisions() {
    handleGroundCollision();
    handleWallCollision();
  }

  /*
   * Check if block has collided with a ground section. If so, it is game over.
   */
  private void handleGroundCollision() {
    // block is already dead so we don't need to handle the collision again
    if (block.isDead) {
      return;
    }

    for (Actor actor : ground.getChildren()) {
      GroundSection groundSection = (GroundSection) actor;
      if (block.bounds.overlaps(groundSection.bounds)) {
        block.isDead = true;
        gameOver();
      }
    }
  }

  private void gameOver() {
    block.velocity.setZero();
    gameOver = true;
    updateBestScore();
  }

  private void updateBestScore() {
    if (score > best) {
      best = score;
      preferences.putInteger(BEST_PREFERENCES_KEY, best);
      preferences.flush();
    }
  }

  /*
   * Check if block has collided with a wall. If so, it is game over. A check is
   * also carried out to see if a point has been scored.
   */
  private void handleWallCollision() {
    // block is already falling so we don't need to handle the collision again
    if (block.isFalling) {
      return;
    }

    for (Actor actor : walls.getChildren()) {
      Wall wall = (Wall) actor;
      if (block.bounds.overlaps(wall.topWallBounds) || block.bounds
          .overlaps(wall.bottomWallBounds)) {
        block.isFalling = true;
        gameOver();
      } else if (block.bounds.overlaps(wall.scoreBounds) && !wall.isPassed) {
        score++;
        wall.isPassed = true;
      }
    }
  }

  private void updateLabels() {
    if (gameOver) {
      scoreLabel.setVisible(false);
      updateGameOverLabel();
      updateGameOverScoreLabel();
      updateGameOverBestLabel();
      updateReplayLabel();
    } else {
      updateScoreLabel();
    }
  }

  private void updateGameOverLabel() {
    float x = camera.position.x - (gameOverLabel.getWidth() * .5f);
    float multiplier = Gdx.app.getType() == Application.ApplicationType.Android ? .666f : .75f;
    float y = (viewport.getWorldHeight() * multiplier) - gameOverLabel.getHeight();
    gameOverLabel.setPosition(x, y);
    gameOverLabel.setVisible(true);
  }

  private void updateGameOverScoreLabel() {
    gameOverScoreLabel.setText(String.format(Locale.ENGLISH, GAME_OVER_SCORE_LABEL_TEXT, score));
    gameOverScoreLabel.pack();
    float x = camera.position.x - (gameOverScoreLabel.getWidth() * .5f);
    float y = gameOverLabel.getY() - gameOverScoreLabel.getHeight() - LABEL_SPACING;
    gameOverScoreLabel.setPosition(x, y);
    gameOverScoreLabel.setVisible(true);
  }

  private void updateGameOverBestLabel() {
    gameOverBestLabel.setText(String.format(Locale.ENGLISH, GAME_OVER_BEST_LABEL_TEXT, best));
    gameOverBestLabel.pack();
    float x = camera.position.x - (gameOverBestLabel.getWidth() * .5f);
    float y = gameOverScoreLabel.getY() - gameOverBestLabel.getHeight() - LABEL_SPACING;
    gameOverBestLabel.setPosition(x, y);
    gameOverBestLabel.setVisible(true);
  }

  private void updateReplayLabel() {
    float x = camera.position.x - (replayLabel.getWidth() * .5f);
    float y = gameOverBestLabel.getY() - replayLabel.getHeight() - LABEL_SPACING;
    replayLabel.setPosition(x, y);
    replayLabel.setVisible(true);
  }

  private void updateScoreLabel() {
    scoreLabel.setText(Integer.toString(score));
    float x = camera.position.x - (scoreLabel.getWidth() / 2);
    float y = camera.position.y + (camera.viewportHeight / 2) - scoreLabel.getHeight()
        - SCORE_LABEL_Y_OFFSET;
    scoreLabel.setPosition(x, y);
  }

  private void updateTimeSinceDeath(float delta) {
    if (gameOver) {
      timeSinceDeath += delta;
    }
  }
}
