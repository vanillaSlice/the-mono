package interactive_coding_challenges.arrays_and_strings;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Mike Lowe
 */
public final class RotationTest {

  @Test
  public void isRotation_null_returnsFalse() {
    assertFalse(Rotation.isRotation(null, "test"));
    assertFalse(Rotation.isRotation("test", null));
    assertFalse(Rotation.isRotation(null, null));
  }

  @Test
  public void isRotation_stringsDifferingInSize_returnFalse() {
    assertFalse(Rotation.isRotation("", " "));
    assertFalse(Rotation.isRotation("ab", "ab "));
    assertFalse(Rotation.isRotation("abc", "cabd"));
  }

  @Test
  public void isRotation_notPresent_returnsFalse() {
    assertFalse(Rotation.isRotation("abc", "cba"));
    assertFalse(Rotation.isRotation("test", "etts"));
    assertFalse(Rotation.isRotation("123", "456"));
  }

  @Test
  public void isRotation_present_returnsTrue() {
    assertTrue(Rotation.isRotation("", ""));
    assertTrue(Rotation.isRotation("abc", "abc"));
    assertTrue(Rotation.isRotation("test ", "st te"));
    assertTrue(Rotation.isRotation("foobarbaz", "barbazfoo"));
  }

}
