package lowe.mike.snake.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import lowe.mike.snake.SnakeGame;

/**
 * Desktop launcher for <i>Snake</i> game.
 *
 * @author Mike Lowe
 */
public final class Snake {

  private static final String[] ICON_PATHS = {
      "icon-16x16.png",
      "icon-32x32.png",
      "icon-64x64.png",
      "icon-128x128.png"
  };

  public static void main(String[] arg) {
    SnakeGame game = new SnakeGame();
    LwjglApplicationConfiguration config = initialiseConfig();
    new LwjglApplication(game, config);
  }

  private static LwjglApplicationConfiguration initialiseConfig() {
    LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
    config.title = SnakeGame.TITLE;
    config.width = SnakeGame.WIDTH;
    config.height = SnakeGame.HEIGHT;
    addIcons(config);
    return config;
  }

  private static void addIcons(LwjglApplicationConfiguration config) {
    for (String iconPath : ICON_PATHS) {
      config.addIcon(iconPath, Files.FileType.Internal);
    }
  }
}
