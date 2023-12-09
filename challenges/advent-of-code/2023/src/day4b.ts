import { readInput } from './util.ts';

export const sumScratchCards = (input: string): number => {
  return input.split(/\r?\n/)
    .filter(line => line.length)
    .map(line => line.split(': ')[1])
    .map(withoutPrefix => withoutPrefix.split('|'))
    .map(leftAndRight => leftAndRight.map(group => new Set(group.split(/\s+/g).filter(s => !!s).map(Number))))
    .map(groups => [...groups[1]].filter(numberWeHave => groups[0].has(numberWeHave)))
    .reduce((sum, _, index, winningNumbersWeHave) => sum + aggregateScratchCards(winningNumbersWeHave, index), 0);
};

const aggregateScratchCards = (winningNumbersWeHave: number[][], currentIndex: number): number => {
  if (currentIndex >= winningNumbersWeHave.length) {
    return 0;
  }

  let sum = 1;

  for (let i = currentIndex + 1; i <= currentIndex + winningNumbersWeHave[currentIndex].length; i++) {
    sum += aggregateScratchCards(winningNumbersWeHave, i);
  }

  return sum;
}

if (Deno.args && Deno.args[0] == 'solve') {
  const text = await readInput('day4b.txt');
  console.log(sumScratchCards(text)); // 8736438
}
