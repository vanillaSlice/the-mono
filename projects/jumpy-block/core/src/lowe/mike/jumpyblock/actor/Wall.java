package lowe.mike.jumpyblock.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * {@code Wall} instances represent the wall obstacles in the world.
 *
 * @author Mike Lowe
 */
public final class Wall extends Group {

  private static final int SPACING = 150;

  public final Rectangle topWallBounds = new Rectangle();
  public final Rectangle bottomWallBounds = new Rectangle();
  public final Rectangle scoreBounds = new Rectangle();
  public boolean isPassed;

  private final Actor topWall;
  private final Actor bottomWall;

  /**
   * Creates a new {@code Wall} instance given a {@link Texture}.
   *
   * @param texture the {@link Texture} of the {@code Wall}
   */
  public Wall(Texture texture) {
    this.topWall = new Image(texture);
    this.topWallBounds.setSize(this.topWall.getWidth(), this.topWall.getHeight());
    this.bottomWall = new Image(texture);
    this.bottomWallBounds.setSize(this.bottomWall.getWidth(), this.bottomWall.getHeight());
    this.scoreBounds.setHeight(SPACING);
    addActor(this.topWall);
    addActor(this.bottomWall);
    setWidth(texture.getWidth());
    setHeight(texture.getHeight() * 2 + SPACING);
  }

  @Override
  public void setPosition(float x, float y) {
    super.setPosition(x, y);
    updateTopWallPosition();
    updateTopWallBounds();
    updateBottomWallBounds();
    updateScoreBounds();
  }

  private void updateTopWallPosition() {
    float x = bottomWall.getX();
    float y = bottomWall.getY() + bottomWall.getHeight() + SPACING;
    topWall.setPosition(x, y);
  }

  private void updateTopWallBounds() {
    float x = getX();
    float y = getY() + bottomWall.getHeight() + SPACING;
    topWallBounds.setPosition(x, y);
  }

  private void updateBottomWallBounds() {
    float x = getX();
    float y = getY();
    bottomWallBounds.setPosition(x, y);
  }

  private void updateScoreBounds() {
    float x = getX() + bottomWall.getWidth();
    float y = getY() + bottomWall.getHeight();
    scoreBounds.setPosition(x, y);
  }
}
