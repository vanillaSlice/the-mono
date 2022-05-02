package lowe.mike.blueprintpong;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * {@code Scaling} is used to scale the different components in the game.
 * <p>
 * Instances of {@code Scaling} cannot be created.
 *
 * @author Mike Lowe
 */
public final class Scaling {

  // don't want instances
  private Scaling() {
  }

  // use actual dimensions of background to scale everything else in the game
  private static final float X_SCALE = BlueprintPongGame.VIRTUAL_WIDTH / 1440f;
  private static final float Y_SCALE = BlueprintPongGame.VIRTUAL_HEIGHT / 810f;

  /**
   * @param font the {@link BitmapFont} to scale
   */
  static void scaleFont(BitmapFont font) {
    font.getData().setScale(X_SCALE, Y_SCALE);
  }

  /**
   * @param actor the {@link Actor} to scale
   */
  public static void scaleActor(Actor actor) {
    actor.setScale(X_SCALE, Y_SCALE);
  }
}
