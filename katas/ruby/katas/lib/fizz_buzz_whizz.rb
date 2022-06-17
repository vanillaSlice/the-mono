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

def fizz_buzz_whizz(n)
  raise ArgumentError, 'n is not numeric' unless n.is_a? Numeric
  message = ''
  message = 'Fizz' if n % 3 == 0
  message += 'Buzz' if n % 5 == 0
  message += 'Whizz' if n.is_prime?
  message = n.to_s if message ==''
  message
end
