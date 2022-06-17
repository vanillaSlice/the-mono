package mwhelan;

/**
 * PROBLEM:
 * Given a number n return:
 * - "Fizz" if n is divisible by 3
 * - "Buzz" if n is divisible by 5
 * - "FizzBuzz" if n is divisible by 3 and 5
 * - n as a string if n does not meet any of these conditions
 *
 * @author Mike Lowe
 */
public final class FizzBuzz {

  // don't want instances
  private FizzBuzz() {
  }

  public static String evaluate(final int n) {
    final StringBuilder message = new StringBuilder();

    final boolean isDivisibleBy3 = n % 3 == 0;
    final boolean isDivisibleBy5 = n % 5 == 0;

    if (isDivisibleBy3) {
      message.append("Fizz");
    }
    if (isDivisibleBy5) {
      message.append("Buzz");
    }
    if (message.length() == 0) {
      message.append(n);
    }

    return message.toString();
  }

}
