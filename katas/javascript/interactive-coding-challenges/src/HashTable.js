/**
 * PROBLEM:
 * Implement a simple hash table with set, get, and remove methods.
 */

'use strict';

function HashTable() {
  const values = [];

  const getHashCode = key => {
    return key % values.length;
  };

  const get = key => {
    return values[getHashCode(key)];
  };

  const set = (key, value) => {
    values[getHashCode(key)] = value;
  };

  const remove = key => {
    values[getHashCode(key)] = undefined;
  };

  return {
    get,
    set,
    remove,
  };
}

module.exports = HashTable;
