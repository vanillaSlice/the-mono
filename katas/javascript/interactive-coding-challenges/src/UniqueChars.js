/**
 * PROBLEM:
 * Implement an algorithm to determine if a string has all
 * unique characters.
 */

'use strict';

const UniqueChars = str => {
  const counts = {};
  
  for (let i = 0, length = str.length; i < length; i++) {
    const char = str[i];
    if (counts.hasOwnProperty(char)) {
      return false;
    } else {
      counts[char] = i;
    }
  }

  return true;
}

module.exports = UniqueChars;
