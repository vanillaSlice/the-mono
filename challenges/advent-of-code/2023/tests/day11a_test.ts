import { assertEquals } from './deps.ts';
import { sumLengths } from '../src/day11a.ts';

Deno.test('sumLengths', () => {
  const input = '...#......\n.......#..\n#.........\n..........\n......#...\n.#........\n.........#\n..........\n.......#..\n#...#.....';
  const output = sumLengths(input);
  assertEquals(output, 374);
});
