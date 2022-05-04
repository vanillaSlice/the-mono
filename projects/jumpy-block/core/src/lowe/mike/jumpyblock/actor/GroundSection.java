package lowe.mike.jumpyblock.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * {@code GroundSection} instances are drawn across the ground of the world.
 *
 * @author Mike Lowe
 */
public final class GroundSection extends Image {

  public final Rectangle bounds = new Rectangle();

  /**
   * Creates a new {@code GroundSection} instance given a {@link Texture}.
   *
   * @param texture the {@link Texture} of the {@code GroundSection}
   */
  public GroundSection(Texture texture) {
    super(texture);
    this.bounds.setSize(getWidth(), getHeight());
  }

  @Override
  public void setPosition(float x, float y) {
    super.setPosition(x, y);
    bounds.setPosition(x, y);
  }
}
