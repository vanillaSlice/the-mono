/**
 * PROBLEM:
 * Given an array, find the two indices that sum to a specific value.
 */

'use strict';

const ArraySum = {
  find: (array, sum) => {
    const counts = {};

    for (let i = 0; i < array.length; i++) {
      const number = array[i];
      if (counts.hasOwnProperty(number)) {
        return [counts[number], i];
      }
        
      const remaining = sum - number;
      if (!counts.hasOwnProperty(remaining)) {
        counts[remaining] = i;
      }
    }

    return [];
  }
};

module.exports = ArraySum;
