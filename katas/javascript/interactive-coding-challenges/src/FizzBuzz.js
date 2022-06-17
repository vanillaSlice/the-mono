/**
 * PROBLEM:
 * Implement Fizz Buzz.
 */

'use strict';

module.exports = length => {
  const result = [];

  for (var i = 1; i <= length; i++) {
    let str = '';

    if (i % 3 === 0) str += 'Fizz';
    if (i % 5 === 0) str += 'Buzz';
    if (!str) str += i;

    result.push(str);
  }

  return result;
};
