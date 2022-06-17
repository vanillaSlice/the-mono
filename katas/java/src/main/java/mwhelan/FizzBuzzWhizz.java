package mwhelan;

/**
 * PROBLEM:
 * Given a number n return:
 * - "Fizz" if n is divisible by 3
 * - "Buzz" if n is divisible by 5
 * - "FizzBuzz" if n is divisible by 3 and 5
 * - "Whizz" if n is a prime number
 * - "FizzWhizz" if n is divisible by 3 and a prime number (i.e. 3 itself)
 * - "BuzzWhizz" if n is divisible by 5 and a prime number (i.e. 5 itself)
 * - n as a string if n does not meet any of these conditions
 *
 * @author Mike Lowe
 */
public final class FizzBuzzWhizz {

  // don't want instances
  private FizzBuzzWhizz() {
  }

  public static String evaluate(final int n) {
    final StringBuilder message = new StringBuilder();

    final boolean isDivisibleBy3 = n % 3 == 0;
    final boolean isDivisibleBy5 = n % 5 == 0;
    final boolean isPrime = isPrime(n);

    if (isDivisibleBy3) {
      message.append("Fizz");
    }
    if (isDivisibleBy5) {
      message.append("Buzz");
    }
    if (isPrime) {
      message.append("Whizz");
    }
    if (message.length() == 0) {
      message.append(n);
    }

    return message.toString();
  }

  private static boolean isPrime(final int n) {
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
