const assert = require('assert');
const DifferentChar = require('../src/DifferentChar');

describe('DifferentChar', () => {
  it('(\'abcd\', \'abcde\') returns \'e\'', () => {
    assert.equal(DifferentChar('abcd', 'abcde'), 'e');
  });

  it('(\'aaabbcdd\', \'abdbacade\') returns \'e\'', () => {
    assert.equal(DifferentChar('aaabbcdd', 'abdbacade'), 'e');
  });
});
