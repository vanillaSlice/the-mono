/*
 * PROBLEM:
 * Given a year, determine if it is a leap year.
 * Leap years occur mostly every 4 years, but every 100 years
 * we skip a leap year unless the year is divisible 400.
 */

const LeapYear = {

  isLeapYear: year => {
    if (year % 4 !== 0) {
      return false;
    } else if (year % 100 === 0) {
      return year % 400 === 0;
    }
    return true;
  }

};

module.exports = LeapYear;
