var assert = require('assert');
var Anagrams = require('../src/Anagrams.js');

describe('Anagrams', () => {
  describe('#getAnagrams()', () => {
    it('\'\'\ should return [\'\']', () => {
      assert.equal('', Anagrams.getAnagrams(''));
    })
    it('\'a\' should return [\'a\']', () => {
      assert.equal('a', Anagrams.getAnagrams('a'));
    });
    it('\'ab\' should return [\'ab\',\'ba\']', () =>{
      assert.equal('ab,ba', Anagrams.getAnagrams('ab'));
    });
    it('\'abc\' should return [\'abc\',\'acb\',\'bac\',\'bca\',\'cab\',\'cba\']', () => {
      assert.equal('abc,acb,bac,bca,cab,cba', Anagrams.getAnagrams('abc'));
    });
    it('\'aaa\' should return [\'aaa\',\'aaa\',\'aaa\',\'aaa\',\'aaa\',\'aaa\']', () => {
      assert.equal('aaa,aaa,aaa,aaa,aaa,aaa', Anagrams.getAnagrams('aaa'));
    });
  });
});
