#!/usr/bin/env bash

pytest --cov=$(dirname $0)/../imagesearch/ --cov-fail-under=90 -W ignore::DeprecationWarning
