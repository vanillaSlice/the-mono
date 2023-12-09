import { readInput } from './util.ts';

export const extrapolateHistory = (input: string): number => {
  return input.split(/\r?\n/)
    .filter(line => line)
    .map(line => line.match(/-?\d+/g)!.map(Number))
    .map(history => {
      const extrapolationRows = [history];
      let currentExtrapolationRow = history;
      while (!currentExtrapolationRow.every(value => value === 0)) {
        const newExtrapolationRow: number[] = [];
        for (let i = 1; i < currentExtrapolationRow.length; i++) {
          newExtrapolationRow[i - 1] = currentExtrapolationRow[i] - currentExtrapolationRow[i - 1];
        }
        extrapolationRows[extrapolationRows.length] = newExtrapolationRow;
        currentExtrapolationRow = newExtrapolationRow;
      }
      return extrapolationRows;
    })
    .reduce((previousValues, extrapolationRows) => {
      extrapolationRows[extrapolationRows.length - 1].unshift(0);
      for (let i = extrapolationRows.length - 1; i > 0; i--) {
        extrapolationRows[i - 1].unshift(extrapolationRows[i - 1][0] - extrapolationRows[i][0]);
      }
      previousValues.push(extrapolationRows[0][0]);
      return previousValues;
    }, [] as number[])
    .reduce((sum, nextValue) => sum + nextValue, 0);
};

if (Deno.args && Deno.args[0] == 'solve') {
  const text = await readInput('day9b.txt');
  console.log(extrapolateHistory(text)); // 1140
}
