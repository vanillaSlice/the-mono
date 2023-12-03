import { assertEquals } from './deps.ts';
import { sumPartNumbers } from '../src/day3a.ts';

Deno.test('sumPartNumbers', () => {
  const input = '467..114..\n...*......\n..35..633.\n......#...\n617*......\n.....+.58.\n..592.....\n......755.\n...$.*....\n.664.598..';
  const output = sumPartNumbers(input);
  assertEquals(output, 4361);
});
