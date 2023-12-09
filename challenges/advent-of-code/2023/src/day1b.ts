import { readInput } from './util.ts';

const digitMap: Record<string, string> = {
  one: '1',
  two: '2',
  three: '3',
  four: '4',
  five: '5',
  six: '6',
  seven: '7',
  eight: '8',
  nine: '9',
};

export const sumCalibrationValues = (input: string): number => {
  return input.split(/\r?\n/)
    .filter(line => line.length)
    .reduce((sum, line) => {
      const digits = [...line].reduce((acc, _, index) => {
        const substring = line.substring(index);
        const matches = Object.entries(digitMap)
          .filter(([key, value]) => substring.startsWith(key) || substring.startsWith(value))
          .map(([_, value]) => value);
        return [...acc, ...matches];
      }, [] as string[]);
      const calibrationValue = Number(digits[0] + digits[digits.length - 1]);
      return sum + calibrationValue;
    }, 0);
};

if (Deno.args && Deno.args[0] == 'solve') {
  const text = await readInput('day1b.txt');
  console.log(sumCalibrationValues(text)); // 53340
}
