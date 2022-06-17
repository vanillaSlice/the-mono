const assert = require('assert');
const Permutations = require('../src/Permutations');

describe('Permutations', () => {
  describe('one or more empty strings return false', () => {
    it('(\'\', \'second\')', () => {
      assert.equal(Permutations('', 'second'), false);
    });

    it('(\'first\', \'\')', () => {
      assert.equal(Permutations('first', ''), false);
    });

    it('(\'\', \'\')', () => {
      assert.equal(Permutations('', ''), false);
    });
  });

  describe('none permutations return false', () => {
    it('(\'abc\', \'def\')', () => {
      assert.equal(Permutations('abc', 'def'), false);
    });

    it('(\'bin\', \'niB\')', () => {
      assert.equal(Permutations('bin', 'niB'), false);
    });
  });

  describe('permutations return true', () => {
    it('(\'abc\', \'abc\')', () => {
      assert.equal(Permutations('abc', 'abc'), true);
    });

    it('(\'act\', \'cat\')', () => {
      assert.equal(Permutations('act', 'cat'), true);
    });

    it('(\'A c T\', \'cTA  \')', () => {
      assert.equal(Permutations('A c T', 'cTA  '), true);
    });
  });
});
