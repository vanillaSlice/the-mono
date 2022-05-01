package lowe.mike.snake.world;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import lowe.mike.snake.util.Assets;

/**
 * {@code Snake} instances represent the snake to be controlled by the player.
 *
 * @author Mike Lowe
 */
final class Snake extends Actor {

  private static final int INITIAL_NUMBER_OF_BODY_PARTS = 5;
  private static final float FLASH_TICK_INTERVAL = .1f;
  private static final int NUMBER_OF_FLASHES = 10;

  /**
   * Directions the {@code Snake} can travel.
   */
  enum Direction {
    UP, RIGHT, DOWN, LEFT
  }

  private final Image head;
  private final Array<Image> bodyParts = new Array<Image>();
  private Direction currentDirection = Direction.RIGHT;
  private Direction lastDirection = currentDirection;
  private boolean isDying;
  private boolean isDead;
  private float flashTick;
  private int timesFlashed;

  /**
   * Creates a new {@code Snake} instance.
   */
  Snake() {
    this.head = new Image(Assets.getBlock());
    initialise();
  }

  private void initialise() {
    head.setPosition(World.BOUNDS.x, World.BOUNDS.y);
    initialiseBodyParts();
  }

  private void initialiseBodyParts() {
    for (int i = 1; i <= INITIAL_NUMBER_OF_BODY_PARTS; i++) {
      Image bodyPart = new Image(Assets.getBlock());
      float x = head.getX() - (i * World.GRID_CELL_WIDTH);
      float y = head.getY();
      bodyPart.setPosition(x, y);
      bodyParts.add(bodyPart);
    }
  }

  /**
   * Resets this {@code Snake} to its initial state.
   */
  void reset() {
    bodyParts.clear();
    currentDirection = Direction.RIGHT;
    lastDirection = currentDirection;
    isDying = false;
    isDead = false;
    flashTick = 0f;
    timesFlashed = 0;
    setVisible(true);
    initialise();
  }

  /**
   * @return this {@code Snake}'s head
   */
  Image getHead() {
    return head;
  }

  /**
   * @return if this {@code Snake} is dying
   */
  boolean isDying() {
    return isDying;
  }

  /**
   * @return if this {@code Snake} is dead
   */
  boolean isDead() {
    return isDead;
  }

  /**
   * Updates this {@code Snake}'s position and state.
   */
  void updatePositionAndState() {
    // don't need to update anything if the snake is dying or dead
    if (isDying || isDead) {
      return;
    }
    Vector2 nextHeadPosition = calculateNextHeadPosition();
    if (isBodyPartOccupyingPosition(nextHeadPosition.x, nextHeadPosition.y)) {
      isDying = true;
    } else {
      updatePosition(nextHeadPosition);
      lastDirection = currentDirection;
    }
  }

  private Vector2 calculateNextHeadPosition() {
    Vector2 position = new Vector2(head.getX(), head.getY());
    switch (currentDirection) {
      case UP:
        position.y = head.getY() + World.GRID_CELL_HEIGHT;
        break;
      case RIGHT:
        position.x = head.getX() + World.GRID_CELL_WIDTH;
        break;
      case DOWN:
        position.y = head.getY() - World.GRID_CELL_HEIGHT;
        break;
      case LEFT:
        position.x = head.getX() - World.GRID_CELL_WIDTH;
        break;
    }
    ensurePositionIsInBounds(position);
    return position;
  }

  private void ensurePositionIsInBounds(Vector2 position) {
    if (position.x < World.BOUNDS.x) {
      position.x = World.BOUNDS.width + World.BOUNDS.x - World.GRID_CELL_WIDTH;
    } else if (position.x >= World.BOUNDS.width + World.BOUNDS.x) {
      position.x = World.BOUNDS.x;
    } else if (position.y < World.BOUNDS.y) {
      position.y = World.BOUNDS.height + World.BOUNDS.y - World.GRID_CELL_HEIGHT;
    } else if (position.y >= World.BOUNDS.height + World.BOUNDS.y) {
      position.y = World.BOUNDS.y;
    }
  }

  /**
   * @param x the x position
   * @param y the y position
   * @return if this {@code Snake}'s head is occupying the given position
   */
  boolean isHeadOccupyingPosition(float x, float y) {
    return head.getX() == x && head.getY() == y;
  }

  /**
   * @param x the x position
   * @param y the y position
   * @return if any of this {@code Snake}'s body parts are occupying the given position
   */
  boolean isBodyPartOccupyingPosition(float x, float y) {
    for (Image bodyPart : bodyParts) {
      if (bodyPart.getX() == x && bodyPart.getY() == y) {
        return true;
      }
    }
    return false;
  }

  private void updatePosition(Vector2 nextHeadPosition) {
    // shift body parts forward
    for (int i = bodyParts.size - 1; i >= 1; i--) {
      Image nextBodyPart = bodyParts.get(i - 1);
      Image currentBodyPart = bodyParts.get(i);
      currentBodyPart.setPosition(nextBodyPart.getX(), nextBodyPart.getY());
    }
    // make sure first body part moves to head position
    bodyParts.first().setPosition(head.getX(), head.getY());
    // update the head position
    head.setPosition(nextHeadPosition.x, nextHeadPosition.y);
  }

  /**
   * Sets the {@link Direction} of this {@code Snake}. Note that the {@code Snake} cannot turn
   * around to the opposite direction. So, for example, if the {@code Snake} is traveling upwards,
   * then is cannot go downwards.
   *
   * @param currentDirection the {@link Direction}
   */
  void setCurrentDirection(Direction currentDirection) {
    // don't let snake turn around into the opposite direction
    if (currentDirection == Direction.UP && lastDirection != Snake.Direction.DOWN ||
        currentDirection == Direction.RIGHT && lastDirection != Snake.Direction.LEFT ||
        currentDirection == Direction.DOWN && lastDirection != Snake.Direction.UP ||
        currentDirection == Direction.LEFT && lastDirection != Snake.Direction.RIGHT) {
      this.currentDirection = currentDirection;
    }
  }

  /**
   * Adds a new body part to the end of this {@code Snake}.
   */
  void addBodyPart() {
    Image newBodyPart = new Image(Assets.getBlock());
    Image lastBodyPart = bodyParts.peek();
    newBodyPart.setPosition(lastBodyPart.getX(), lastBodyPart.getY());
    bodyParts.add(newBodyPart);
  }

  /**
   * Updates this {@code Snake}'s death sequence.
   *
   * @param delta time in seconds since the last frame
   */
  void updateDeathSequence(float delta) {
    // if snake has flashed the required amount of times, then it has finished dying
    if (timesFlashed >= NUMBER_OF_FLASHES) {
      isDead = true;
      isDying = false;
    }
    // make snake flash when appropriate
    if (flashTick >= FLASH_TICK_INTERVAL) {
      flashTick -= FLASH_TICK_INTERVAL;
      setVisible(!isVisible());
      if (isVisible()) {
        timesFlashed++;
      }
    } else {
      flashTick += delta;
    }
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    super.draw(batch, parentAlpha);
    head.draw(batch, parentAlpha);
    for (Image bodyPart : bodyParts) {
      bodyPart.draw(batch, parentAlpha);
    }
  }
}
