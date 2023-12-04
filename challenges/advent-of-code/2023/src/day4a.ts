import { readInput } from './util.ts';

export const sumCardPoints = (input: string): number => {
  return input.split(/\r?\n/)
    .filter(line => line.length)
    .map(line => line.split(': ')[1])
    .map(withoutPrefix => withoutPrefix.split('|'))
    .map(leftAndRight => leftAndRight.map(group => new Set(group.split(/\s+/g).filter(s => !!s).map(Number))))
    .map(groups => ({
      winningNumbers: groups[0],
      numbersWeHave: groups[1],
    }))
    .map(card => [...card.numbersWeHave].filter(numberWeHave => card.winningNumbers.has(numberWeHave)))
    .filter(winningNumbersWeHave => !!winningNumbersWeHave.length)
    .reduce((sum, winningNumbersWeHave) => sum += Math.pow(2, winningNumbersWeHave.length - 1), 0);
};

if (Deno.args && Deno.args[0] == 'solve') {
  const text = await readInput('day4a.txt');
  console.log(sumCardPoints(text));
}
