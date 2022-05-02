package lowe.mike.blueprintpong.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import lowe.mike.blueprintpong.BlueprintPongGame;

/**
 * Desktop launcher for <i>Blueprint Pong</i> game.
 *
 * @author Mike Lowe
 */
public final class BlueprintPong {

  private static final int WIDTH = 960;
  private static final int HEIGHT = 540;
  private static final String[] ICON_PATHS = {
      "icon-16x16.png",
      "icon-32x32.png",
      "icon-64x64.png",
      "icon-128x128.png"
  };

  public static void main(String[] arg) {
    BlueprintPongGame game = new BlueprintPongGame();
    LwjglApplicationConfiguration config = initialiseConfig();
    new LwjglApplication(game, config);
  }

  private static LwjglApplicationConfiguration initialiseConfig() {
    LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
    config.title = BlueprintPongGame.TITLE;
    config.width = WIDTH;
    config.height = HEIGHT;
    addIcons(config);
    return config;
  }

  private static void addIcons(LwjglApplicationConfiguration config) {
    for (String iconPath : ICON_PATHS) {
      config.addIcon(iconPath, Files.FileType.Internal);
    }
  }
}
