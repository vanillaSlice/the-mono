package lowe.mike.snake;

import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

/**
 * Android launcher for <i>Snake</i> game.
 *
 * @author Mike Lowe
 */
public final class AndroidLauncher extends AndroidApplication {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    SnakeGame game = new SnakeGame();
    AndroidApplicationConfiguration config = initialiseConfig();
    initialize(game, config);
  }

  private static AndroidApplicationConfiguration initialiseConfig() {
    AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
    config.useAccelerometer = false;
    config.useCompass = false;
    return config;
  }
}
