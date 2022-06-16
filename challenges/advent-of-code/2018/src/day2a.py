#!/usr/bin/env python3

"""
Solution to problem at https://adventofcode.com/2018/day/2.
"""

from collections import Counter
import os

def box_checksum(box_ids):
    two_count = 0
    three_count = 0
    for box_id in box_ids:
        counter = Counter(box_id)
        values = counter.values()
        if 2 in values:
            two_count += 1
        if 3 in values:
            three_count += 1
    return two_count * three_count

if __name__ == '__main__':
    with open(os.path.join(os.path.dirname(__file__), 'input/day2a.txt')) as f:
        print('The answer is: {}'.format(box_checksum(f.read().splitlines())))
