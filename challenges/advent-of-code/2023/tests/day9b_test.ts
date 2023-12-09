import { assertEquals } from './deps.ts';
import { extrapolateHistory } from '../src/day9b.ts';

Deno.test('extrapolateHistory', () => {
  const input = '0 3 6 9 12 15\n1 3 6 10 15 21\n10 13 16 21 30 45';
  const output = extrapolateHistory(input);
  assertEquals(output, 2);
});
