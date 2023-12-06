import { assertEquals } from './deps.ts';
import { waysToBeatRecord } from '../src/day6b.ts';

Deno.test('waysToBeatRecord', () => {
  const input = 'Time:      7  15   30\nDistance:  9  40  200';
  const output = waysToBeatRecord(input);
  assertEquals(output, 71503);
});
