import { readInput } from './util.ts';

export const extrapolateHistory = (input: string): number => {
  const output = input.split(/\r?\n/)
    .filter(line => line)
    .map(line => line.match(/-?\d+/g)!.map(Number));

  // tidy this up
  const nextValues: number[] = [];
  for (const history of output) {
    const rows: number[][] = [];
    let currentRow = history;
    let i = 0;
    while (!currentRow.every(item => item === 0)) {
      const newRow: number[] = [];
      rows[i] = newRow;
      for (let j = 1; j < currentRow.length; j++) {
        const left = currentRow[j - 1];
        const right = currentRow[j];
        newRow[j - 1] = right - left;
      }
      currentRow = rows[i];
      i++;
    }
    rows[i - 1].unshift(0);
    rows.unshift(history);
    for (let j = rows.length - 1; j > 0; j--) {
      const left = rows[j][0];
      const right = rows[j - 1][0];
      const push = right - left;
      rows[j - 1].unshift(push);
    }
    nextValues.push(history[0]);
  }

  return nextValues.reduce((sum, value) => sum + value, 0);
};

if (Deno.args && Deno.args[0] == 'solve') {
  const text = await readInput('day9b.txt');
  console.log(extrapolateHistory(text)); // 1140
}
