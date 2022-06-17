#!/usr/bin/env io

fizzBuzzWhizz := method(n,
  message := ""
  if(n % 3 == 0, message = "Fizz")
  if(n % 5 == 0, message = message .."Buzz")
  if(isPrime(n), message = message .."Whizz")
  if(message == "", message = n asString)
  return message
)

isPrime := method(n,
  if(n < 2, return false)
  max := n sqrt floor
  for(i, 2, max, if(n % i == 0, return false))
  return true
)
