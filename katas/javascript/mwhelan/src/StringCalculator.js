/*
 * PROBLEM:
 * Create a simple string calculator. The rules are:
 * - the method takes in a string containing 0...n numbers
 * - '' returns 0
 * - '1' returns 1, '2' returns 2
 * - the default delimiters are ',' and '\n' so '1,2,3' returns 6 and '1\n2\n3' returns 6
 * - a custom delimiter can be used by passing in '//[delimiter]\n1...' so '//;\n2;2;1' returns 5
 * - numbers bigger than 1000 should be ignored
 */

'use strict';

const StringCalculator = () => {

  const prefixRegex = /^\/\/.+\n/;
  const standardDelimiters = ',|\n';

  function add(input) {
    if (!input) return 0;

    const delimiterRegex = getDelimiterRegex(input);

    return input
      .replace(prefixRegex, '')
      .split(delimiterRegex)
      .reduce((total, current) => {
        const toAdd = current > 1000 ? 0 : Number(current);
        return total + toAdd;
      }, 0);
  }

  function getDelimiterRegex(input) {
    const customDelimiter = getCustomDelimiter(input);
    const delimiter = customDelimiter || standardDelimiters;
    const delimiterRegex = new RegExp(delimiter, 'g');
    return delimiterRegex;
  }

  function getCustomDelimiter(input) {
    const matches = input.match(prefixRegex);
    if (!matches) return '';
    const match = matches[0];
    const customDelimiter = match.slice(2, match.length - 1);
    return customDelimiter;
  }

  return { add };

};

module.exports = StringCalculator();
