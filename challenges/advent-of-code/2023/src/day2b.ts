import { readInput } from './util.ts';

export const sumPower = (input: string): number => {
  return input.split(/\r?\n/)
    .filter(line => line.length)
    .reduce((sum, line) => {
      const maxMatches = (colour: string) => {
        const matches = line.match(new RegExp(`\\d+(?= ${colour})`, 'g'));
        return matches ? Math.max(...matches.map(Number), 0) : 0;
      };
      return sum + (maxMatches('red') * maxMatches('green') * maxMatches('blue'));
    }, 0);
};

if (Deno.args && Deno.args[0] == 'solve') {
  const text = await readInput('day2b.txt');
  console.log(sumPower(text));
}
