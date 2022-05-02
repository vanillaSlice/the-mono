package lowe.mike.blueprintpong;

import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

/**
 * Android launcher for <i>Blueprint Pong</i> game.
 *
 * @author Mike Lowe
 */
public final class AndroidLauncher extends AndroidApplication {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    BlueprintPongGame game = new BlueprintPongGame();
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
