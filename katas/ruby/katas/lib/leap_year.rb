#!/usr/bin/env ruby

def is_leap_year?(year)
  raise ArgumentError, 'year is not numeric' unless year.is_a? Numeric
  return false if year % 4 != 0
  return year % 400 == 0 if year % 100 == 0
  true
end
