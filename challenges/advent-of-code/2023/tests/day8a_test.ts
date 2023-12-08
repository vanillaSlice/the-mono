import { assertEquals } from './deps.ts';
import { countSteps } from '../src/day8a.ts';

Deno.test('countSteps - no repeats', () => {
  const input = 'RL\n\nAAA = (BBB, CCC)\nBBB = (DDD, EEE)\nCCC = (ZZZ, GGG)\nDDD = (DDD, DDD)\nEEE = (EEE, EEE)\nGGG = (GGG, GGG)\nZZZ = (ZZZ, ZZZ)';
  const output = countSteps(input);
  assertEquals(output, 2);
});

Deno.test('countSteps - with repeats', () => {
  const input = 'LLR\n\nAAA = (BBB, BBB)\nBBB = (AAA, ZZZ)\nZZZ = (ZZZ, ZZZ)';
  const output = countSteps(input);
  assertEquals(output, 6);
});
