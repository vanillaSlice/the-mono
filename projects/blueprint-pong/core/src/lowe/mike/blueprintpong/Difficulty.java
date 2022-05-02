package lowe.mike.blueprintpong;

import com.badlogic.gdx.utils.ObjectMap;

/**
 * {@code Difficulty} enum represents the various difficulties of the game.
 *
 * @author Mike Lowe
 */
public enum Difficulty {

  EASY("Easy", 80f), MEDIUM("Medium", 105f), HARD("Hard", 125f);

  private static final ObjectMap<String, Difficulty> stringToEnum
      = new ObjectMap<String, Difficulty>();

  static {
    for (Difficulty difficulty : values()) {
      stringToEnum.put(difficulty.toString(), difficulty);
    }
  }

  private final String string;
  private final float computerPaddleSpeed; // in units per second

  Difficulty(String string, float computerPaddleSpeed) {
    this.string = string;
    this.computerPaddleSpeed = computerPaddleSpeed;
  }

  /**
   * Takes a {@link String} and returns the {@code Difficulty} associated with it.
   *
   * @param string the {@link String} representation of the {@code Difficulty}
   * @return the {@code Difficulty}
   */
  public static Difficulty fromString(String string) {
    return stringToEnum.get(string);
  }

  /**
   * @return the computer paddle speed for this {@code Difficulty} (in units per second)
   */
  public float getComputerPaddleSpeed() {
    return computerPaddleSpeed;
  }

  @Override
  public String toString() {
    return string;
  }
}
