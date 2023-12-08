#!/usr/bin/env bash

if [[ $# -eq 0 ]]; then
  echo "Supply puzzle argument e.g. 1a"
  exit 1
fi

time deno run --allow-read $(dirname $0)/../src/day${1}.ts solve
