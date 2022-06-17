package mwhelan;

/**
 * PROBLEM:
 * Given a number n return the Fibonacci number at this
 * position.
 *
 * @author Mike Lowe
 */
public final class Fibonacci {

  // don't want instances
  private Fibonacci() {
  }

  /*
   * Non-recursive solution.
   */

  public static int calculate(final int n) {
    if (n < 0) {
      throw new IllegalArgumentException("n cannot be less than 0 but was " + n);
    }
    if (n < 2) {
      return n;
    }

    int result = 1;
    int prev1 = 1;
    int prev2 = 0;
    for (int j = 2; j <= n; j++) {
      result = prev1 + prev2;
      prev2 = prev1;
      prev1 = result;
    }
    return result;
  }

  /*
   * Recursive solution (not as efficient).
   */

//  public static int calculate(final int n) {
//    if (n < 0) {
//      throw new IllegalArgumentException("n cannot be less than 0 but was " + n);
//    }
//    if (n < 2) {
//      return n;
//    }
//
//    return calculate(n - 1) + calculate(n - 2);
//  }

}
