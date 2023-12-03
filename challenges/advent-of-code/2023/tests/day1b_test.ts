import { assertEquals } from './deps.ts';
import { sumCalibrationValues } from '../src/day1b.ts';

Deno.test('sumCalibrationValues', () => {
  const input = 'two1nine\neightwothree\nabcone2threexyz\nxtwone3four\n4nineeightseven2\nzoneight234\n7pqrstsixteen';
  const output = sumCalibrationValues(input);
  assertEquals(output, 281);
});
