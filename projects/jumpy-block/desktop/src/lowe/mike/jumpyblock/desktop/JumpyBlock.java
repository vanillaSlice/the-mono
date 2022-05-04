package lowe.mike.jumpyblock.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import lowe.mike.jumpyblock.JumpyBlockGame;

/**
 * Desktop launcher for <i>Jumpy Block</i> game.
 *
 * @author Mike Lowe
 */
public final class JumpyBlock {

  private static final String[] ICON_PATHS = {
      "icon-16x16.png",
      "icon-32x32.png",
      "icon-64x64.png",
      "icon-128x128.png"
  };

  public static void main(String[] arg) {
    JumpyBlockGame game = new JumpyBlockGame();
    LwjglApplicationConfiguration config = initialiseConfig();
    new LwjglApplication(game, config);
  }

  private static LwjglApplicationConfiguration initialiseConfig() {
    LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
    config.title = JumpyBlockGame.TITLE;
    config.width = JumpyBlockGame.WIDTH;
    config.height = JumpyBlockGame.HEIGHT;
    addIcons(config);
    return config;
  }

  private static void addIcons(LwjglApplicationConfiguration config) {
    for (String iconPath : ICON_PATHS) {
      config.addIcon(iconPath, Files.FileType.Internal);
    }
  }
}
