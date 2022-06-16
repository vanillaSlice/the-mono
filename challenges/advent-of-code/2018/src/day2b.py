#!/usr/bin/env python3

"""
Solution to problem at https://adventofcode.com/2018/day/2#part2.
"""

from collections import defaultdict
import os

def common_chars(box_ids):
    common_at_index = defaultdict(set)
    for box_id in box_ids:
        for i in range(len(box_id)):
            str_without_i = box_id[:i] + box_id[i + 1:]
            if str_without_i in common_at_index[i]:
                return str_without_i
            common_at_index[i].add(str_without_i)

if __name__ == '__main__':
    with open(os.path.join(os.path.dirname(__file__), 'input/day2b.txt')) as f:
        print('The answer is: {}'.format(common_chars(f.read().splitlines())))
