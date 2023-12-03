import { assertEquals } from './deps.ts';
import { sumGearRatios } from '../src/day3b.ts';

Deno.test('sumGearRatios', () => {
  const input = '467..114..\n...*......\n..35..633.\n......#...\n617*......\n.....+.58.\n..592.....\n......755.\n...$.*....\n.664.598..';
  const output = sumGearRatios(input);
  assertEquals(output, 467835);
});
