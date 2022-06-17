package interactive_coding_challenges.arrays_and_strings;

/**
 * PROBLEM:
 * Determine if a string s1 is a rotation of another string s2.
 *
 * @author Mike Lowe
 */
public final class Rotation {

  // don't want instances
  private Rotation() {
  }

  public static boolean isRotation(final String s1, final String s2) {
    if (s1 == null || s2 == null || s1.length() != s2.length()) {
      return false;
    } else if (s1.equals(s2)) {
      return true;
    }
    return (s2 + s2).contains(s1);
    // e.g. s1 = abc, s2 = cab -> cabcab.contains(abc) -> true
  }

}
