import { assertEquals } from './deps.ts';
import { sumLengths } from '../src/day11b.ts';

Deno.test('sumLengths', () => {
  const input = '...#......\n.......#..\n#.........\n..........\n......#...\n.#........\n.........#\n..........\n.......#..\n#...#.....';
  const output = sumLengths(input, 100);
  assertEquals(output, 8410);
});
