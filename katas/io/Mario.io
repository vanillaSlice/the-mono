#!/usr/bin/env io

mario := method(n,
  for(i, 1, n,
    for(j, 1, n, if(j > n - i, write("X"), write(" ")))
    write("  ")
    for(j, 1, n, if(j <= i, write("X"), write(" ")))
    write("\n")
  )
)
