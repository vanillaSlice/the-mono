import { readInput } from './util.ts';

export const sumPossibleGames = (input: string): number => {
  return input.split(/\r?\n/)
    .filter(line => line.length)
    .reduce((sum, line, index) => {
      const impossible = line.split(';').some(roll =>
        Number(roll.match(/\d+(?= red)/g) ?? 0) > 12 ||
        Number(roll.match(/\d+(?= green)/g) ?? 0) > 13 ||
        Number(roll.match(/\d+(?= blue)/g) ?? 0) > 14);

      if (!impossible) {
        sum += index + 1;
      }

      return sum;
    }, 0);
};

if (Deno.args && Deno.args[0] == 'solve') {
  const text = await readInput('day2a.txt');
  console.log(sumPossibleGames(text));
}
