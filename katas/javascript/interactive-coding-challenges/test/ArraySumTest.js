const assert = require('assert');
const ArraySum = require('../src/ArraySum.js');

describe('ArraySum', () => {
  describe('it finds two indices that some to a specific value', () => {
    it('[1, 1], 2 returns [0, 1]', () => {
      assert.equal(ArraySum.find([1, 1], 2), '0,1');
    });

    it('[1, 3, 2, -7, 5], 7 returns [2, 4]', () => {
      assert.equal(ArraySum.find([1, 3, 2, -7, 5], 7), '2,4');
    });

    it('[1, 1, 1, 1, 1, 1, 1, 10], 11 returns [0, 7]', () => {
      assert.equal(ArraySum.find([1, 1, 1, 1, 1, 1, 1, 10], 11), '0,7');
    });
  });

  describe('it returns [] when no sum can be found', () => {
    it('[1, 2, 3, 4, 5], 10 returns []', () => {
      assert.equal(ArraySum.find([1, 2, 3, 4, 5], 10), '');
    });

    it('[], 0 returns []', () => {
      assert.equal(ArraySum.find([], 0), '');
    });

    it('[1], 1 returns []', () => {
      assert.equal(ArraySum.find([1], 1), '');
    });
  });
});
