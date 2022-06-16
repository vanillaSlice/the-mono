#!/usr/bin/env bash

pytest --cov=$(dirname $0)/../src/ --cov-fail-under=80
