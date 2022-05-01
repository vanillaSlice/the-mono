package lowe.mike.snake.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import lowe.mike.snake.SnakeGame;
import lowe.mike.snake.world.Level;

/**
 * {@code State} contains current state information and settings for the game.
 * <p>
 * Instances of {@code State} cannot be created.
 *
 * @author Mike Lowe
 */
public final class State {

  private static Preferences preferences;
  private static boolean shouldPlayMusic;
  private static int level;
  private static int highScore;
  private static int currentScore;
  private static boolean isGameOver;

  // don't want instances
  private State() {
  }

  /**
   * Initialises the {@code State} by loading in preferences.
   */
  public static void initialise() {
    preferences = Gdx.app.getPreferences(SnakeGame.TITLE);
    shouldPlayMusic = preferences.getBoolean("play-music", true);
    level = preferences.getInteger("level", Level.MINIMUM);
    highScore = preferences.getInteger("high-score");
    currentScore = 0;
    isGameOver = false;
  }

  /**
   * @return if music should be played when playing the game
   */
  public static boolean shouldPlayMusic() {
    return shouldPlayMusic;
  }

  /**
   * @param shouldPlayMusic if music should be played when playing the game
   */
  public static void setShouldPlayMusic(boolean shouldPlayMusic) {
    if (State.shouldPlayMusic != shouldPlayMusic) {
      State.shouldPlayMusic = shouldPlayMusic;
      preferences.putBoolean("play-music", State.shouldPlayMusic).flush();
    }
  }

  /**
   * @return the level to play the game in
   */
  public static int getLevel() {
    return level;
  }

  /**
   * @param level the level to play the game in
   */
  public static void setLevel(int level) {
    if (State.level != level) {
      State.level = level;
      preferences.putInteger("level", State.level).flush();
    }
  }

  /**
   * @return the high score
   */
  public static int getHighScore() {
    return highScore;
  }

  /**
   * @param highScore the high score
   */
  public static void setHighScore(int highScore) {
    if (State.highScore != highScore) {
      State.highScore = highScore;
      preferences.putInteger("high-score", State.highScore).flush();
    }
  }

  /**
   * @return the current score
   */
  public static int getCurrentScore() {
    return currentScore;
  }

  /**
   * @param currentScore the current score
   */
  public static void setCurrentScore(int currentScore) {
    State.currentScore = currentScore;
  }

  /**
   * @return if the game is over
   */
  public static boolean isGameOver() {
    return isGameOver;
  }

  /**
   * @param isGameOver if the game is over
   */
  public static void setGameOver(boolean isGameOver) {
    State.isGameOver = isGameOver;
  }
}
