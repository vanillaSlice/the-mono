/*
 * PROBLEM:
 * Given a number n return:
 * - 'Fizz' if n is divisible by 3
 * - 'Buzz' if n is divisible by 5
 * - 'FizzBuzz' if n is divisible by 3 and 5
 * - n if n does not meet any of these conditions
 */

'use strict';

const FizzBuzz = {

  run: n => {
    let result = '';
    if (n % 3 === 0) result = 'Fizz';
    if (n % 5 === 0) result += 'Buzz';
    if (result === '') result = n.toString();
    return result;
  }

};

module.exports = FizzBuzz;
