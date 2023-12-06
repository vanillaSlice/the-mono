import { assertEquals } from './deps.ts';
import { marginOfError } from '../src/day6a.ts';

Deno.test('marginOfError', () => {
  const input = 'Time:      7  15   30\nDistance:  9  40  200';
  const output = marginOfError(input);
  assertEquals(output, 288);
});
