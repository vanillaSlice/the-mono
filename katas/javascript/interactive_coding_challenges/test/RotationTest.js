const assert = require('assert');
const Rotation = require('../src/Rotation');

describe('Rotation', () => {
  describe('any strings that differ in size return false', () => {
    it('(\'\', \'o\')', () => {
      assert.equal(Rotation('', 'o'), false);
    });

    it('(\'o\', \'\')', () => {
      assert.equal(Rotation('o', ''), false);
    });

    it('(\'o\', \'oo\')', () => {
      assert.equal(Rotation('o', 'oo'), false);
    });
  });

  describe('equal strings return true', () => {
    it('(\'\', \'\')', () => {
      assert.equal(Rotation('', ''), true);
    });

    it('(\'foobar\', \'foobar\')', () => {
      assert.equal(Rotation('foobar', 'foobar'), true);
    });
  });

  describe('rotations return true', () => {
    it('(\'foo\', \'oof\')', () => {
      assert.equal(Rotation('foo', 'oof'), true);
    });
  });

  describe('none rotations return false', () => {
    it('(\'foo\', \'bar\')', () => {
      assert.equal(Rotation('foo', 'bar'), false);
    });

    it('(\'foobarbaz\', \'foofoobar\')', () => {
      assert.equal(Rotation('foobarbaz', 'foofoobar'), false);
    });
  });
});
