#!/usr/bin/env ruby

class Numeric
  def is_prime?
    return false if self < 2
    max_possible_divisor = Math.sqrt(self)
    i = 2
    until i > max_possible_divisor
      return false if self % i == 0
      i += 1
    end
    true
  end
end
