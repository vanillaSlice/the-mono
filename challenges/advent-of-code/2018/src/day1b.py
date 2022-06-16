#!/usr/bin/env python3

"""
Solution to problem at https://adventofcode.com/2018/day/1#part2.
"""

from itertools import cycle
import os

def chronal_calibration(changes):
    frequencies_seen = {0}
    current_frequency = 0
    for change in cycle(changes):
        sign = change[0]
        number = int(change[1:])
        if sign == '+':
            current_frequency += number
        else:
            current_frequency -= number
        if current_frequency in frequencies_seen:
            return current_frequency
        frequencies_seen.add(current_frequency)

if __name__ == '__main__':
    with open(os.path.join(os.path.dirname(__file__), 'input/day1b.txt')) as f:
        print('The answer is: {}'.format(chronal_calibration(f.read().splitlines())))
