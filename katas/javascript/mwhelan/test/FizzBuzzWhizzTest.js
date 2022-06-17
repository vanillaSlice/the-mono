var assert = require('assert');
var FizzBuzzWhizz = require('../src/FizzBuzzWhizz.js');

describe('FizzBuzzWhizz', () => {
  describe('#run()', () => {
    it('6 should return Fizz', () => {
      assert.equal('Fizz', FizzBuzzWhizz.run(6));
    });
    it('9 should return Fizz', () => {
      assert.equal('Fizz', FizzBuzzWhizz.run(9));
    });
    it('10 should return Buzz', () => {
      assert.equal('Buzz', FizzBuzzWhizz.run(10));
    });
    it('20 should return Buzz', () => {
      assert.equal('Buzz', FizzBuzzWhizz.run(10));
    });
    it('15 should return FizzBuzz', () => {
      assert.equal('FizzBuzz', FizzBuzzWhizz.run(15));
    });
    it('30 should return FizzBuzz', () => {
      assert.equal('FizzBuzz', FizzBuzzWhizz.run(30));
    });
    it('3 should return FizzWhizz', () => {
      assert.equal('FizzWhizz', FizzBuzzWhizz.run(3));
    });
    it('5 should return BuzzWhizz', () => {
      assert.equal('BuzzWhizz', FizzBuzzWhizz.run(5));
    });
    it('1 should return 1', () => {
      assert.equal(1, FizzBuzzWhizz.run(1));
    });
    it('4 should return 4', () => {
      assert.equal(4, FizzBuzzWhizz.run(4));
    });
    it('2 should return Whizz', () => {
      assert.equal('Whizz', FizzBuzzWhizz.run(2));
    });
    it('7 should return Whizz', () => {
      assert.equal('Whizz', FizzBuzzWhizz.run(7));
    });
  });
});
