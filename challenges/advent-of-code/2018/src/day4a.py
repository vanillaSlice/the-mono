#!/usr/bin/env python3

"""
Solution to problem at https://adventofcode.com/2018/day/4.
"""

from collections import defaultdict
from datetime import datetime
import os
import re

pattern = re.compile('\\[(?P<timestamp>\\d+\\-\\d+\\-\\d+ \\d+:\\d+)\\] (?P<message>.+)')

def repose_record(records):
    sleep_per_minute = defaultdict(lambda: defaultdict(int))
    total_sleep = defaultdict(int)

    current_guard = 0
    started_sleeping = 0

    for timestamp, message in sorted(parse_records(records).items()):
        current_minute = timestamp.minute

        if message == 'falls asleep':
            started_sleeping = current_minute
        elif message == 'wakes up':
            for i in range(started_sleeping, current_minute):
                sleep_per_minute[current_guard][i] += 1
                total_sleep[current_guard] += 1
        else:
            current_guard = message.split(' ')[1][1:]

    guard_slept_most = max(total_sleep, key=total_sleep.get)
    minute_slept_most = max(sleep_per_minute[guard_slept_most], key=sleep_per_minute[guard_slept_most].get)

    return int(guard_slept_most) * minute_slept_most

def parse_records(records):
    parsed_records = {}
    for record in records:
        m = pattern.match(record)
        timestamp = datetime.strptime(m.group('timestamp'), '%Y-%m-%d %H:%M')
        message = m.group('message')
        parsed_records[timestamp] = message
    return parsed_records

if __name__ == '__main__':
    with open(os.path.join(os.path.dirname(__file__), 'input/day4a.txt')) as f:
        print('The answer is: {}'.format(repose_record(f.read().splitlines())))
