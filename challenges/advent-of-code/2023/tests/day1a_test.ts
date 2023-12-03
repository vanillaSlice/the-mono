import { assertEquals } from './deps.ts';
import { sumCalibrationValues } from '../src/day1a.ts';

Deno.test('sumCalibrationValues', () => {
  const input = '1abc2\npqr3stu8vwx\na1b2c3d4e5f\ntreb7uchet';
  const output = sumCalibrationValues(input);
  assertEquals(output, 142);
});
