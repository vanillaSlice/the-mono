#!/usr/bin/env python3

"""
Solution to problem at https://adventofcode.com/2018/day/5.
"""

import os

def alchemical_reduction(polymer):
    resulting_polymer = ''
    for char in polymer:
        if resulting_polymer and same_char_not_same_case(char, resulting_polymer[-1]):
            resulting_polymer = resulting_polymer[:-1]
        else:
            resulting_polymer += char
    return len(resulting_polymer)

def same_char_not_same_case(c1, c2):
    return abs(ord(c1) - ord(c2)) == 32

if __name__ == '__main__':
    with open(os.path.join(os.path.dirname(__file__), 'input/day5a.txt')) as f:
        print('The answer is: {}'.format(alchemical_reduction(f.readline())))
