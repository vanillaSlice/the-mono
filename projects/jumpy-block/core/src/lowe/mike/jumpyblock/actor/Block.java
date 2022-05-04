package lowe.mike.jumpyblock.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * {@code Block} instances are the characters to be controlled by the player.
 *
 * @author Mike Lowe
 */
public final class Block extends Image {

  private static final int MOMENTUM = 200;
  private static final int JUMP = 525;

  public final Rectangle bounds = new Rectangle();
  public final Vector2 velocity = new Vector2();
  public boolean isFalling;
  public boolean isDead;

  /**
   * Creates a new {@code Block} instance given a {@link Texture}.
   *
   * @param texture the {@link Texture} of the {@code Block}
   */
  public Block(Texture texture) {
    super(texture);
    this.bounds.setSize(getWidth(), getHeight());
  }

  @Override
  public void setPosition(float x, float y) {
    super.setPosition(x, y);
    bounds.setPosition(x, y);
  }

  /**
   * Add momentum to this {@code Block}'s velocity.
   */
  public void addMomentum() {
    velocity.x = MOMENTUM;
  }

  /**
   * Make this {@code Block} jump.
   */
  public void jump() {
    velocity.y = JUMP;
  }
}
