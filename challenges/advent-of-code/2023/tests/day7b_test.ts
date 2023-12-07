import { assertEquals } from './deps.ts';
import { totalWinnings } from '../src/day7b.ts';

Deno.test('totalWinnings', () => {
  const input = '32T3K 765\nT55J5 684\nKK677 28\nKTJJT 220\nQQQJA 483';
  const output = totalWinnings(input);
  assertEquals(output, 5905);
});
