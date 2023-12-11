import { assertEquals } from './deps.ts';
import { farthestSteps } from '../src/day10a.ts';

Deno.test('farthestSteps - simple loop', () => {
  const input = '.....\n.S-7.\n.|.|.\n.L-J.\n.....';
  const output = farthestSteps(input);
  assertEquals(output, 4);
});

Deno.test('farthestSteps - complex loop', () => {
  const input = '..F7.\n.FJ|.\nSJ.L7\n|F--J\nLJ...';
  const output = farthestSteps(input);
  assertEquals(output, 8);
});
