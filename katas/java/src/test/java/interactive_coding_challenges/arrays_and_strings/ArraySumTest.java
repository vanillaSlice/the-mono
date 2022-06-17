package interactive_coding_challenges.arrays_and_strings;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

/**
 * @author Mike Lowe
 */
public final class ArraySumTest {

  @Test(expected = NullPointerException.class)
  public void twoSum_null_throwsNullPointerException() {
    ArraySum.twoSum(null, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void twoSum_invalidArraySizeZero_throwsIllegalArgumentException() {
    ArraySum.twoSum(new int[0], 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void twoSum_invalidArraySizeOne_throwsIllegalArgumentException() {
    ArraySum.twoSum(new int[]{1}, 0);
  }

  @Test
  public void twoSum_present_returnsTwoIndicesThatSumToSpecificValue() {
    assertArrayEquals(new int[]{2, 4}, ArraySum.twoSum(new int[]{1, 3, 2, -7, 5}, 7));
  }

  @Test
  public void twoSum_notPresent_returnsMinusOneAsIndices() {
    assertArrayEquals(new int[]{-1, -1}, ArraySum.twoSum(new int[]{1, 3, 2, -7, 5}, 70));
  }

}
