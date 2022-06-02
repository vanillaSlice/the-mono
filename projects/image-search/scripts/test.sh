#!/usr/bin/env bash

pytest --cov=$(dirname $0)/../imagesearch/ --cov-report=xml --cov-fail-under=90 -W ignore::DeprecationWarning
