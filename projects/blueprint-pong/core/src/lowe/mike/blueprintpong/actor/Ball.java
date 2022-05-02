package lowe.mike.blueprintpong.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * Represents the ball in the game.
 *
 * @author Mike Lowe
 */
public final class Ball extends ScaledImage {

  private float speed; // in units per second
  private float angle; // in degrees e.g. 0f = left, 90f = up, 180f = right, 270f = down
  private final Vector2 direction = new Vector2();

  /**
   * Creates a new {@code Ball} given the {@link Texture}.
   *
   * @param texture the {@link Texture}
   */
  public Ball(Texture texture) {
    super(texture);
  }

  /**
   * @param speed the speed of this {@code Ball} (in units per second)
   */
  public void setSpeed(float speed) {
    this.speed = speed;
  }

  /**
   * @param angle the angle this {@code Ball} is travelling at
   */
  public void setAngle(float angle) {
    this.angle = angle;
    updateDirection();
  }

  private void updateDirection() {
    float x = -MathUtils.cosDeg(angle);
    float y = MathUtils.sinDeg(angle);
    direction.set(x, y);
  }

  /**
   * @return the angle this {@code Ball} is travelling at
   */
  public float getAngle() {
    return angle;
  }

  /**
   * Updates this {@code Ball}'s position.
   *
   * @param delta time in seconds since the last frame
   */
  public void updatePosition(float delta) {
    float x = direction.x * speed * delta;
    float y = direction.y * speed * delta;
    moveBy(x, y);
  }
}
