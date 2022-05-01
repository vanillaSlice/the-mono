package lowe.mike.snake.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import lowe.mike.snake.SnakeGame;

/**
 * Provides a base class for the {@link Screen}s in the game.
 *
 * @author Mike Lowe
 */
class BaseScreen extends ScreenAdapter {

  static final float COMPONENT_SPACING = 20f;

  final SpriteBatch spriteBatch;
  final Stage stage;

  private final OrthographicCamera camera = new OrthographicCamera();
  private final Viewport viewport;

  /**
   * Creates a new {@code BaseScreen} given a {@link SpriteBatch}.
   *
   * @param spriteBatch the {@link SpriteBatch} to add sprites to
   */
  BaseScreen(SpriteBatch spriteBatch) {
    this.spriteBatch = spriteBatch;
    this.camera.setToOrtho(false);
    this.viewport = new FitViewport(SnakeGame.WIDTH, SnakeGame.HEIGHT, this.camera);
    this.stage = new Stage(this.viewport, this.spriteBatch);
  }

  @Override
  public final void show() {
    Gdx.input.setInputProcessor(stage);
  }

  @Override
  public final void resize(int width, int height) {
    viewport.update(width, height);
  }

  @Override
  public final void render(float delta) {
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    update(delta);
    spriteBatch.setProjectionMatrix(camera.combined);
    stage.act(delta);
    stage.draw();
  }

  /**
   * Method that subclasses can override to determine how to update the {@link Screen} in each
   * frame.
   *
   * @param delta time in seconds since the last frame
   */
  void update(float delta) {
  }

  @Override
  public final void dispose() {
    stage.dispose();
    onDispose();
  }

  /**
   * Method that subclasses can override to determine what to dispose.
   */
  void onDispose() {
  }
}
