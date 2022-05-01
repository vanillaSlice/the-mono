package lowe.mike.snake.world;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import lowe.mike.snake.util.State;

/**
 * {@code World} represents the world in the game containing the snake, food, etc.
 *
 * @author Mike Lowe
 */
public final class World {

  static final Rectangle BOUNDS = new Rectangle(20f, 240f, 320f, 320f);
  static final int GRID_CELL_WIDTH = 16;
  static final int GRID_CELL_HEIGHT = 16;

  private static final int ROWS = 20;
  private static final int COLUMNS = 20;
  private static final float TICK_INTERVAL_INCREMENT = .075f;
  private static final int BONUS_FOOD_APPEARANCE_INTERVAL = 5;
  private static final int BONUS_FOOD_TICKS = 20;

  private final Stage stage;
  private final Snake snake;
  private final Food food;
  private final BonusFood bonusFood;
  private float tick;
  private int eaten;
  private int nextBonusFoodAppearance = BONUS_FOOD_APPEARANCE_INTERVAL;
  private int bonusFoodTicksRemaining;

  /**
   * Creates a new {@code World} instance.
   *
   * @param stage the {@link Stage} to add {@link Actor}s to
   */
  public World(Stage stage) {
    this.stage = stage;
    this.snake = new Snake();
    this.food = new Food();
    this.bonusFood = new BonusFood();
    this.stage.addActor(this.snake);
    this.stage.addActor(this.food);
    setRandomPosition(food);
  }

  private void setRandomPosition(Food food) {
    float x;
    float y;
    do {
      x = (MathUtils.random(COLUMNS - 1) * GRID_CELL_WIDTH) + BOUNDS.x;
      y = (MathUtils.random(ROWS - 1) * GRID_CELL_HEIGHT) + BOUNDS.y;
    } while (isPositionOccupied(x, y));
    food.setPosition(x, y);
  }

  private boolean isPositionOccupied(float x, float y) {
    return snake.isBodyPartOccupyingPosition(x, y) || snake.isHeadOccupyingPosition(x, y) ||
        isFoodOccupyingPosition(x, y) || isBonusFoodOccupyingPosition(x, y);
  }

  private boolean isFoodOccupyingPosition(float x, float y) {
    return food.getX() == x && food.getY() == y;
  }

  private boolean isBonusFoodOccupyingPosition(float x, float y) {
    return isBonusFoodShowing() && bonusFood.getX() == x && bonusFood.getY() == y;
  }

  /**
   * @return if the {@link BonusFood} is showing
   */
  public boolean isBonusFoodShowing() {
    return bonusFood.getStage() != null;
  }

  /**
   * Resets this {@code World} to its initial state.
   */
  public void reset() {
    snake.reset();
    setRandomPosition(food);
    bonusFood.remove();
    tick = 0f;
    eaten = 0;
    nextBonusFoodAppearance = BONUS_FOOD_APPEARANCE_INTERVAL;
    bonusFoodTicksRemaining = 0;
  }

  /**
   * @return the number of bonus ticks remaining
   */
  public int getBonusFoodTicksRemaining() {
    return bonusFoodTicksRemaining;
  }

  /**
   * Set the {@link Snake}'s direction to go up, if possible.
   */
  public void setSnakeDirectionUp() {
    snake.setCurrentDirection(Snake.Direction.UP);
  }

  /**
   * Set the {@link Snake}'s direction to go right, if possible.
   */
  public void setSnakeDirectionRight() {
    snake.setCurrentDirection(Snake.Direction.RIGHT);
  }

  /**
   * Set the {@link Snake}'s direction to go down, if possible.
   */
  public void setSnakeDirectionDown() {
    snake.setCurrentDirection(Snake.Direction.DOWN);
  }

  /**
   * Set the {@link Snake}'s direction to go left, if possible.
   */
  public void setSnakeDirectionLeft() {
    snake.setCurrentDirection(Snake.Direction.LEFT);
  }

  /**
   * Updates this {@code World}'s state.
   *
   * @param delta time in seconds since the last frame
   */
  public void update(float delta) {
    float tickInterval = (Level.MAXIMUM + 1 - State.getLevel()) * TICK_INTERVAL_INCREMENT;
    if (tick >= tickInterval) {
      tick -= tickInterval;
      snake.updatePositionAndState();
      checkSnakeAteFood();
      checkSnakeAteBonusFood();
      updateBonusFoodAppearance();
    } else {
      tick += delta;
    }

    if (snake.isDying()) {
      snake.updateDeathSequence(delta);
    } else if (snake.isDead()) {
      State.setGameOver(true);
    }

    if (isBonusFoodShowing()) {
      bonusFood.updateFlash(delta);
    }
  }

  private void checkSnakeAteFood() {
    if (isFoodOccupyingPosition(snake.getHead().getX(), snake.getHead().getY())) {
      setRandomPosition(food);
      State.setCurrentScore(State.getCurrentScore() + State.getLevel());
      snake.addBodyPart();
      eaten++;
    }
  }

  private void checkSnakeAteBonusFood() {
    if (isBonusFoodOccupyingPosition(snake.getHead().getX(), snake.getHead().getY())) {
      State.setCurrentScore(State.getCurrentScore() + bonusFoodTicksRemaining);
      snake.addBodyPart();
      removeBonusFood();
    }
  }

  private void removeBonusFood() {
    bonusFood.remove();
    nextBonusFoodAppearance += BONUS_FOOD_APPEARANCE_INTERVAL;
  }

  private void updateBonusFoodAppearance() {
    if (isBonusFoodShowing() && bonusFoodTicksRemaining <= 0) {
      removeBonusFood();
    } else if (isBonusFoodShowing()) {
      bonusFoodTicksRemaining--;
    } else if (!isBonusFoodShowing() && eaten == nextBonusFoodAppearance) {
      addBonusFood();
    }
  }

  private void addBonusFood() {
    stage.addActor(bonusFood);
    bonusFoodTicksRemaining = BONUS_FOOD_TICKS;
    setRandomPosition(bonusFood);
  }
}
