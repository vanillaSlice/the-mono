#!/usr/bin/env bash

set -e

cd $(dirname $0)/..

pytest tests --cov=dawdle --cov-report=xml --cov-fail-under=90 -W ignore::DeprecationWarning
