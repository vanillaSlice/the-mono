const assert = require('assert');
const FizzBuzz = require('../src/FizzBuzz');

describe('FizzBuzz', () => {
  it('returns FizzBuzz up to 15', () => {
    const expectedResult = [
      '1',
      '2',
      'Fizz',
      '4',
      'Buzz',
      'Fizz',
      '7',
      '8',
      'Fizz',
      'Buzz',
      '11',
      'Fizz',
      '13',
      '14',
      'FizzBuzz',
    ];
    assert.deepEqual(FizzBuzz(15), expectedResult);
  });
});
