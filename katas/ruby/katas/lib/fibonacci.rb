#!/usr/bin/env ruby

def fibonacci(n)
  raise ArgumentError, 'n is not numeric' unless n.is_a? Numeric
  raise ArgumentError, "n should not be less than 0 but was #{n}" if n < 0

  return n if n == 0 or n == 1

  prev_1 = 1
  prev_2 = 0
  i = 2
  until i > n
    result = prev_1 + prev_2
    prev_2 = prev_1
    prev_1 = result
    i += 1
  end
  result
end
