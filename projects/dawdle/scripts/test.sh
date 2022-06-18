#!/usr/bin/env bash

set -e

cd $(dirname $0)/..

pytest tests --cov=dawdle --cov-report=term-missing --cov-fail-under=90
