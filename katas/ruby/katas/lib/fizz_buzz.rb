#!/usr/bin/env ruby

def fizz_buzz(n)
  raise ArgumentError, 'n is not numeric' unless n.is_a? Numeric
  message = ''
  message = 'Fizz' if n % 3 == 0
  message += 'Buzz' if n % 5 == 0
  message = n.to_s if message == ''
  message
end
