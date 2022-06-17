/*
 * PROBLEM:
 * Given a number n return the Fibonacci number at this
 * position.
 */

'use strict';

const Fibonacci = {

  calculate: n => {
    if (n == 0) return 0;
    if (n == 1 || n == 2) return 1;
    let current = 2;
    let n1 = 1;
    let n2 = 1;
    for (let i = 3; i <= n; i++) {
      current = n1 + n2;
      n2 = n1;
      n1 = current;
    }
    return current;
  }

  //
  // Recursive solution 
  //
  // calculate: function calculate(n) {
  //   if (n == 0) return 0;
  //   if (n == 1 || n == 2) return 1;
  //   return calculate(n - 1) + calculate(n - 2);
  // }

};

module.exports = Fibonacci;
