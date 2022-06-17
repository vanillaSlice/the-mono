/**
 * PROBLEM:
 * Determine if a string is a permutation of another string.
 */

'use strict';

const Permutations = (s1, s2) => {
  if (!s1 || !s2) {
    return false;
  } else if (s1 === s2) {
    return true;
  }
  return sortString(s1) === sortString(s2);
}

const sortString = str => str.split('').sort().join('');

module.exports = Permutations;
