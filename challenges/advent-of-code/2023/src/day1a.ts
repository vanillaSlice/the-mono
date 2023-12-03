import { readInput } from './util.ts';

export const sumCalibrationValues = (input: string): number => {
  return input.split(/\r?\n/)
    .filter(line => line.length)
    .reduce((sum, line) => {
      const digits = line.match(/\d/g)!;
      const calibrationValue = Number(digits[0] + digits[digits.length - 1]);
      return sum + calibrationValue;
    }, 0);
};

if (Deno.args && Deno.args[0] == 'solve') {
  const text = await readInput('day1a.txt');
  console.log(sumCalibrationValues(text));
}
