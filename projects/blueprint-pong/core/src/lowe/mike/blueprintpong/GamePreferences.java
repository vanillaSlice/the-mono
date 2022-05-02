package lowe.mike.blueprintpong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * {@code GamePreferences} provides access to settings for the game.
 * <p>
 * Instances of {@code GamePreferences} cannot be created.
 *
 * @author Mike Lowe
 */
public final class GamePreferences {

  private static final String DIFFICULTY_KEY = "difficulty";
  private static final Difficulty DIFFICULTY_DEFAULT = Difficulty.EASY;
  private static final String PLAY_SOUNDS_KEY = "play-sounds";
  private static final boolean PLAY_SOUNDS_DEFAULT = true;

  // don't want instances
  private GamePreferences() {
  }

  /**
   * @return the {@link Difficulty} of the game
   */
  public static Difficulty getDifficulty() {
    Difficulty difficulty = Difficulty.fromString(getPreferences().getString(DIFFICULTY_KEY));
    // set and return difficulty if it has not been set
    if (difficulty == null) {
      setDifficulty(DIFFICULTY_DEFAULT);
      return DIFFICULTY_DEFAULT;
    } else {
      return difficulty;
    }
  }

  private static Preferences getPreferences() {
    return Gdx.app.getPreferences(BlueprintPongGame.TITLE);
  }

  /**
   * @param difficulty the {@link Difficulty} to set the game to
   */
  public static void setDifficulty(Difficulty difficulty) {
    getPreferences().putString(DIFFICULTY_KEY, difficulty.toString()).flush();
  }

  /**
   * @return if sounds should be played when playing the game
   */
  public static boolean shouldPlaySounds() {
    return getPreferences().getBoolean(PLAY_SOUNDS_KEY, PLAY_SOUNDS_DEFAULT);
  }

  /**
   * @param playSounds if sounds should be played when playing the game
   */
  public static void setPlaySounds(boolean playSounds) {
    getPreferences().putBoolean(PLAY_SOUNDS_KEY, playSounds).flush();
  }
}
