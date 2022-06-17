#!/usr/bin/env io

fibonacci := method(n,
  if(n < 0, Exception raise("n must be greater than or equal to 0 but was " ..(n asString)))
  if(n == 0 or n == 1, return n)
  result := 0
  prev1 := 1
  prev2 := 0
  for(i, 2, n,
    result = prev1 + prev2
    prev2 = prev1
    prev1 = result
  )
  return result
)
