var assert = require('assert');
var Fibonacci = require('../src/Fibonacci.js');

describe('Fibonacci', () => {
  describe('#calculate()', () => {
    it('should return 0 when passed 0', () => {
      assert.equal(0, Fibonacci.calculate(0));
    });
    it('should return 1 when passed 1', () => {
      assert.equal(1, Fibonacci.calculate(1));
    });
    it('should return 1 when passed 2', () => {
      assert.equal(1, Fibonacci.calculate(2));
    });
    it('should return 2 when passed 3', () => {
      assert.equal(2, Fibonacci.calculate(3));
    });
    it('should return 3 when passed 4', () => {
      assert.equal(3, Fibonacci.calculate(4));
    });
    it('should return 5 when passed 5', () => {
      assert.equal(5, Fibonacci.calculate(5));
    });
    it('should return 8 when passed 6', () => {
      assert.equal(8, Fibonacci.calculate(6));
    });
    it('should return 13 when passed 7', () => {
      assert.equal(13, Fibonacci.calculate(7));
    });
    it('should return 21 when passed 8', () => {
      assert.equal(21, Fibonacci.calculate(8));
    });
    it('should return 34 when passed 9', () => {
      assert.equal(34, Fibonacci.calculate(9));
    });
  });
});
