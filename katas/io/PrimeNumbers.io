#!/usr/bin/env io

isPrime := method(n,
  if(n < 2, return false)
  max := n sqrt floor
  for(i, 2, max, if(n % i == 0, return false))
  return true
)
