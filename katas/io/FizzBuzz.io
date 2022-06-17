#!/usr/bin/env io

fizzBuzz := method(n,
  message := ""
  if(n % 3 == 0, message = "Fizz")
  if(n % 5 == 0, message = message .."Buzz")
  if(message == "", message = n asString)
  return message
)
