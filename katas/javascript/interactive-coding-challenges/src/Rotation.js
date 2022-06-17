/**
 * PROBLEM:
 * Determine if a string s1 is a rotation of another string s2.
 */

'use strict';

module.exports = (s1, s2) => {
  if (s1.length !== s2.length) {
    return false;
  } else if (s1 === s2) {
    return true;
  }
  return (s2 + s2).indexOf(s1) > -1;
};
