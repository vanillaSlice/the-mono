/*
 * PROBLEM:
 * Write a method to generate all anagrams for an input string.
 */

'use strict'

const Anagrams = {

  getAnagrams: function getAnagrams(str) {
    if (!str) return [''];
    const anagrams = [];
    for (let i = 0, length = str.length; i < length; i++) {
      const firstChar = str[i];
      const firstHalf = str.slice(0, i);
      const secondHalf = str.slice(i + 1);
      const subAnagrams = getAnagrams(firstHalf + secondHalf);
      subAnagrams.forEach(subAnagram => anagrams.push(firstChar + subAnagram));
    }
    return anagrams;
  }

};

module.exports = Anagrams;
