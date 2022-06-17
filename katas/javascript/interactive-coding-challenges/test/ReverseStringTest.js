const assert = require('assert');
const ReverseString = require('../src/ReverseString');

describe('ReverseString', () => {
  it('\'\' returns \'\'', () => {
    assert.equal(ReverseString(''), '');
  });

  it('\'foo\' returns \'oof\'', () => {
    assert.equal(ReverseString('foo'), 'oof');
  });

  it('\'foo bar\' returns \'rab oof\'', () => {
    assert.equal(ReverseString('foo bar'), 'rab oof');
  });
});
