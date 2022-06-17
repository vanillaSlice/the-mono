var assert = require('assert');
var StringCalculator = require('../src/StringCalculator.js');

describe('StringCalculator', () => {
  describe('#add()', () => {
    it('\'\' should return 0', () => {
      assert.equal(0, StringCalculator.add(''));
    });
    it('\'1\' should return 1', () => {
      assert.equal(1, StringCalculator.add('1'));
    });
    it('\'2\' should return 2', () => {
      assert.equal(2, StringCalculator.add('2'));
    });
    it('\'1,2,3\' should return 6', () => {
      assert.equal(6, StringCalculator.add('1,2,3'));
    });
    it('\'1\\n2\\n3\' should return 6', () => {
      assert.equal(6, StringCalculator.add('1\n2\n3'));
    });
    it('\'1000,1000,1000,1000,1000\' should return 5000', () => {
      assert.equal(5000, StringCalculator.add('1000,1000,1000,1000,1000'));
    });
    it('\'1000\\n1000\\n1000\\n1000\\n1000\' should return 5000', () => {
      assert.equal(5000, StringCalculator.add('1000\n1000\n1000\n1000\n1000'));
    });
    it('\'1000,1001,9000,10000,2000\' should return 1000', () => {
      assert.equal(1000, StringCalculator.add('1000,1001,9000,10000,2000'));
    });
    it('\'//;\\n2;2;1\' should return 5', () => {
      assert.equal(5, StringCalculator.add('//;\n2;2;1'));
    });
    it('\'//;;\\n1000;;1001;;9000;;10000;;2000\' should return 1000', () => {
      assert.equal(1000, StringCalculator.add('//;;\n1000;;1001;;9000;;10000;;2000'));
    });
  });
});
