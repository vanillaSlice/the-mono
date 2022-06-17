var assert = require('assert');
var FizzBuzz = require('../src/FizzBuzz.js');

describe('FizzBuzz', () => {
  describe('#run()', () => {
    it('should return \'1\' when passed 1', () => {
      assert.equal('1', FizzBuzz.run(1));
    });
    it('should return \'2\' when passed 2', () => {
      assert.equal('2', FizzBuzz.run(2));
    });
    it('should return \'Fizz\' when passed 3', () => {
      assert.equal('Fizz', FizzBuzz.run(3));
    });
    it('should return \'4\' when passed 4', () => {
      assert.equal('4', FizzBuzz.run(4));
    });
    it('should return \'Buzz\' when passed 5', () => {
      assert.equal('Buzz', FizzBuzz.run(5));
    });
    it('should return \'Fizz\' when passed 6', () => {
      assert.equal('Fizz', FizzBuzz.run(6));
    });
    it('should return \'Fizz\' when passed 9', () => {
      assert.equal('Fizz', FizzBuzz.run(9));
    });
    it('should return \'Buzz\' when passed 10', () => {
      assert.equal('Buzz', FizzBuzz.run(10));
    });
    it('should return \'FizzBuzz\' when passed 15', () => {
      assert.equal('FizzBuzz', FizzBuzz.run(15));
    });
    it('should return \'FizzBuzz\' when passed 30', () => {
      assert.equal('FizzBuzz', FizzBuzz.run(30));
    });
  });
});
