var assert = require('assert');
var LeapYear = require('../src/LeapYear.js');

describe('LeapYear', () => {
  describe('#isLeapYear()', () => {
    it('should return true when passed 4', () => {
      assert.equal(true, LeapYear.isLeapYear(4));
    });
    it('should return true when passed 8', () => {
      assert.equal(true, LeapYear.isLeapYear(8));
    })
    it('should return false when passed 100', () => {
      assert.equal(false, LeapYear.isLeapYear(100));
    });
    it('should return false when passed 200', () => {
      assert.equal(false, LeapYear.isLeapYear(200));
    });
    it('should return true when passed 400', () => {
      assert.equal(true, LeapYear.isLeapYear(400));
    });
    it('should return false when passed 1995', () => {
      assert.equal(false, LeapYear.isLeapYear(1995));
    });
    it('should return true when passed 1996', () => {
      assert.equal(true, LeapYear.isLeapYear(1996));
    });
    it('should return true when passed 2000', () => {
      assert.equal(true, LeapYear.isLeapYear(2000));
    });
    it('should return false when passed 2100', () => {
      assert.equal(false, LeapYear.isLeapYear(2100));
    });
    it('should return false when passed 2103', () => {
      assert.equal(false, LeapYear.isLeapYear(2103));
    });
  });
});
