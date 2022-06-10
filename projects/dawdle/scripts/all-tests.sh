#!/usr/bin/env bash

set -e

cd $(dirname $0)

./lint.sh
./test.sh
