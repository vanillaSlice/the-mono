import { readInput } from './util.ts';

export const sumPower = (input: string): number => {
  return input.split(/\r?\n/)
    .filter(line => line.length)
    .reduce((sum, line) => {
      let maxRed = 0;
      let maxGreen = 0;
      let maxBlue = 0;

      line.split(';').forEach((roll) => {
        maxRed = Math.max(maxRed, Number(roll.match(/\d+(?= red)/g) ?? 0));
        maxGreen = Math.max(maxGreen, Number(roll.match(/\d+(?= green)/g) ?? 0));
        maxBlue = Math.max(maxBlue, Number(roll.match(/\d+(?= blue)/g) ?? 0));
      });

      return sum + (maxRed * maxGreen * maxBlue);
    }, 0);
};

if (Deno.args && Deno.args[0] == 'solve') {
  const text = await readInput('day2b.txt');
  console.log(sumPower(text));
}
