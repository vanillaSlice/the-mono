/**
 * PROBLEM:
 * Compress a string such that 'AAABCCDDDD' becomes 'A3BC2D4'.
 * Only compress the string if it saves space.
 */

'use strict';

module.exports = input => {
  let currentChar = '';
  let currentCount = 0;
  let compressed = '';

  for (let i = 0; i < input.length; i++) {
    const char = input[i];

    if (char !== currentChar) {
      compressed += currentChar + (currentCount > 1 ? currentCount : '');
      currentChar = char;
      currentCount = 1;
    } else {
      currentCount++;
    }

    if (i === input.length - 1) {
      compressed += currentChar + (currentCount > 1 ? currentCount : '');
    }
  }

  return (compressed.length < input.length) ? compressed : input;
};
