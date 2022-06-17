/*
 * PROBLEM:
 * Create a simple word counter that takes in a string and returns an object
 * of unique words and the number of times each word occurs.
 * e.g. 'hello,world,hello' should return {hello: 2, world: 1}.
 * A custom delimiter may be used, a comma will be used as the default.
 */

'use strict';

const WordCounter = () => {

  const defaultDelimiter = ',';

  function count(input, delimiter) {
    return input
      .split(delimiter || defaultDelimiter)
      .reduce((count, word) => {
        if (!word) return count;
        if (!count.hasOwnProperty(word)) count[word] = 0;
        count[word]++;
        return count;
      }, {});
  }

  return { count };
}

module.exports = WordCounter();
