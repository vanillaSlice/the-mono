/**
 * PROBLEM:
 * Find the single different char between two strings.
 */

'use strict';

function countChars(str, counts) {
  for (let i = 0; i < str.length; i++) {
    const char = str[i];
    if (counts.hasOwnProperty(char)) {
      counts[char]++;
    } else {
      counts[char] = 1;
    }
  }
}

module.exports = (s1, s2) => {
  const counts = {};

  countChars(s1, counts);
  countChars(s2, counts);

  for (let key in counts) {
    if (counts.hasOwnProperty(key)) {
      if (counts[key] === 1) {
        return key;
      }
    }
  }
};
