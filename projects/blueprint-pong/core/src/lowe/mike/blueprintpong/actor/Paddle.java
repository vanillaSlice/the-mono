package lowe.mike.blueprintpong.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * Represents the paddles in the game.
 *
 * @author Mike Lowe
 */
public final class Paddle extends ScaledImage {

  /*
   * Paddle is split into 8 equal sections.
   */
  public static final int SECTIONS = 8;

  private final float sectionSize;
  private float speed; // in units per second
  private final Vector2 start = new Vector2();
  private final Vector2 end = new Vector2();
  private final Vector2 direction = new Vector2();
  private float distance;
  private boolean isMoving;

  /**
   * Creates a new {@code Paddle} given the {@link Texture}.
   *
   * @param texture the {@link Texture}
   */
  public Paddle(Texture texture) {
    super(texture);
    this.sectionSize = getScaledHeight() / SECTIONS;
  }

  /**
   * @return size of each section of this {@code Paddle}
   */
  public float getSectionSize() {
    return sectionSize;
  }

  /**
   * @param speed the speed of this {@code Paddle} (in units per second)
   */
  public void setSpeed(float speed) {
    this.speed = speed;
  }

  /**
   * @param y the y position this {@code Paddle} should start moving towards
   */
  public void setTargetY(float y) {
    start.y = getY();
    end.y = y;
    direction.y = new Vector2(0, end.y - start.y).nor().y;
    distance = start.dst(0, end.y);
    isMoving = true;
  }

  /**
   * Moves this {@code Paddle} up.
   *
   * @param delta time in seconds since the last frame
   */
  public void moveUp(float delta) {
    setTargetY(getY() + (speed * delta));
  }

  /**
   * Moves this {@code Paddle} down.
   *
   * @param delta time in seconds since the last frame
   */
  public void moveDown(float delta) {
    setTargetY(getY() - (speed * delta));
  }

  /**
   * Updates this {@code Paddle}'s position, if required.
   *
   * @param delta time in seconds since the last frame
   */
  public void updatePosition(float delta) {
    if (!isMoving) {
      return;
    }
    moveBy(0, direction.y * speed * delta);
    if (start.dst(0, getY()) >= distance) {
      setY(end.y);
      isMoving = false;
    }
  }
}
