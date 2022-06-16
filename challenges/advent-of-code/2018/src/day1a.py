#!/usr/bin/env python3

"""
Solution to problem at https://adventofcode.com/2018/day/1.
"""

from functools import reduce
import os

def chronal_calibration(changes):
    return reduce(sum_changes, changes, 0)

def sum_changes(total, current):
    sign = current[0]
    number = int(current[1:])
    return total + number if sign == '+' else total - number

if __name__ == '__main__':
    with open(os.path.join(os.path.dirname(__file__), 'input/day1a.txt')) as f:
        print('The answer is: {}'.format(chronal_calibration(f.read().splitlines())))
