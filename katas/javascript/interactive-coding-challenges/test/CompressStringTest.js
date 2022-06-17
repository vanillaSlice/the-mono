const assert = require('assert');
const CompressString = require('../src/CompressString');

describe('CompressString', () => {
  describe('returns compressed string', () => {
    it('\'AAABCCDDDD\' returns \'A3BC2D4\'', () => {
      assert.equal(CompressString('AAABCCDDDD'), 'A3BC2D4');
    });

    it('\'AAABCCDDDDE\' returns \'A3BC2D4E\'', () => {
      assert.equal(CompressString('AAABCCDDDDE'), 'A3BC2D4E');
    });

    it('\'BAAACCDDDD\' returns \'BA3C2D4\'', () => {
      assert.equal(CompressString('BAAACCDDDD'), 'BA3C2D4');
    });

    it('\'AAABAACCDDDD\' returns \'A3BA2C2D4\'', () => {
      assert.equal(CompressString('AAABAACCDDDD'), 'A3BA2C2D4');
    });
  });

  describe('returns original string if compression does not save space', () => {
    it('\'\' returns \'\'', () => {
      assert.equal(CompressString(''), '');
    });

    it('\'aa\' returns \'aa\'', () => {
      assert.equal(CompressString('aa'), 'aa');
    });

    it('\'AABBCC\' returns \'AABBCC\'', () => {
      assert.equal(CompressString('AABBCC'), 'AABBCC');
    });
  });
});
