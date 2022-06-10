#!/usr/bin/env bash

set -e

cd $(dirname $0)/..

pycodestyle *.py dawdle tests
pylint *.py dawdle tests
