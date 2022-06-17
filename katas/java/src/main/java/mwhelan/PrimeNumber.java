package mwhelan;

/**
 * PROBLEM:
 * Given a number n return if it is prime.
 *
 * @author Mike Lowe
 */
public final class PrimeNumber {

  // don't want instances
  private PrimeNumber() {
  }

  public static boolean isPrime(final int n) {
    if (n < 2) {
      return false;
    }

    final double maxPossibleDivisor = Math.floor(Math.sqrt(n));

    for (int i = 2; i <= maxPossibleDivisor; i++) {
      if (n % i == 0) {
        return false;
      }
    }

    return true;
  }

}
