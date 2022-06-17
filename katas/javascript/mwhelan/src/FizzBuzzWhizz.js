/**
 * PROBLEM:
 * Given a number n return:
 * - 'Fizz' if n is divisible by 3
 * - 'Buzz' if n is divisible by 5
 * - 'FizzBuzz' if n is divisible by 3 and 5
 * - 'Whizz' if n is a prime number
 * - 'FizzWhizz' if n is divisible by 3 and a prime number (i.e. 3 itself)
 * - 'BuzzWhizz' if n is divisible by 5 and a prime number (i.e. 5 itself)
 * - n if n does not meet any of these conditions
 */

'use strict';

const FizzBuzzWhizz = {

  run: n => {
    let result = '';
    if (n % 3 == 0) result += 'Fizz';
    if (n % 5 == 0) result += 'Buzz';
    if (isPrime(n)) result += 'Whizz';
    if (result == '') result += n.toString();
    return result;
  }

};

function isPrime(n) {
  if (n < 2) return false;
  for (let i = 2, sqrt = Math.sqrt(n); i <= sqrt; i++) {
    if (n % i == 0) {
      return false;
    }
  }
  return true;
}

module.exports = FizzBuzzWhizz;
