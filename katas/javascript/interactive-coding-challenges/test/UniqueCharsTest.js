const assert = require('assert');
const UniqueChars = require('../src/UniqueChars');

describe('UniqueChars', () => {
  it('\'\' returns true', () => {
    assert.equal(UniqueChars(''), true);
  });

  it('\'foo\' returns false', () => {
    assert.equal(UniqueChars('foo'), false);
  });

  it('\'bar\' returns true', () => {
    assert.equal(UniqueChars('bar'), true);
  });

  it('\'foO\' returns true', () => {
    assert.equal(UniqueChars('foO'), true);
  });
});
