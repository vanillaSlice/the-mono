package lowe.mike.blueprintpong.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import lowe.mike.blueprintpong.Scaling;

/**
 * Extension of {@link Image} that performs scaling tasks.
 *
 * @author Mike Lowe
 */
class ScaledImage extends Image {

  private final float scaledWidth;
  private final float scaledHeight;

  /**
   * Creates a new {@code ScaledImage} given a {@link Texture}.
   *
   * @param texture the {@link Texture}
   */
  ScaledImage(Texture texture) {
    super(texture);
    Scaling.scaleActor(this);
    this.scaledWidth = getWidth() * getScaleX();
    this.scaledHeight = getHeight() * getScaleY();
  }

  /**
   * @return the scaled width
   */
  public final float getScaledWidth() {
    return scaledWidth;
  }

  /**
   * @return the scaled height
   */
  public final float getScaledHeight() {
    return scaledHeight;
  }
}
