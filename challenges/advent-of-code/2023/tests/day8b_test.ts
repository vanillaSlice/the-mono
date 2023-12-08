import { assertEquals } from './deps.ts';
import { countSteps } from '../src/day8b.ts';

Deno.test('countSteps', () => {
  const input = 'LR\n\n11A = (11B, XXX)\n11B = (XXX, 11Z)\n11Z = (11B, XXX)\n22A = (22B, XXX)\n22B = (22C, 22C)\n22C = (22Z, 22Z)\n22Z = (22B, 22B)\nXXX = (XXX, XXX)';
  const output = countSteps(input);
  assertEquals(output, 6);
});
