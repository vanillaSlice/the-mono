#!/usr/bin/env python3

"""
Solution to problem at https://adventofcode.com/2018/day/3#part2.
"""

from collections import defaultdict, namedtuple
import os
import re

pattern = re.compile('#(?P<id>\\d+) @ (?P<left>\\d+),(?P<top>\\d+): (?P<width>\\d+)x(?P<height>\\d+)')

Claim = namedtuple('Claim', ['id', 'left', 'top', 'width', 'height'])

def overlap(claims):
    claimed = defaultdict(set)
    no_overlaps = set()
    for claim in map(parse_claim, claims):
        no_overlaps.add(claim.id)
        for x in range(claim.left, claim.left + claim.width):
            for y in range(claim.top, claim.top + claim.height):
                if claimed[(x, y)]:
                    no_overlaps.discard(claim.id)
                    no_overlaps -= claimed[(x, y)]
                claimed[(x, y)].add(claim.id)
    return no_overlaps.pop()

def parse_claim(claim):
    m = pattern.match(claim)
    return Claim(
        id=int(m.group('id')),
        left=int(m.group('left')),
        top=int(m.group('top')),
        width=int(m.group('width')),
        height=int(m.group('height'))
    )

if __name__ == '__main__':
    with open(os.path.join(os.path.dirname(__file__), 'input/day3b.txt')) as f:
        print('The answer is: {}'.format(overlap(f.read().splitlines())))
