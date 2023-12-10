import { readInput } from './util.ts';

export const farthestSteps = (input: string): number => {
  const grid = input.split(/\r?\n/)
    .filter(line => line)
    .map(line => line.split(''));

  const norths = ['S', '|', '7', 'F'];
  const getNorth = (rowIndex: number, columnIndex: number) => grid[rowIndex - 1]?.[columnIndex];
  const easts = ['S', '-', 'J', '7'];
  const getEast = (rowIndex: number, columnIndex: number) => grid[rowIndex][columnIndex + 1];
  const souths = ['S', '|', 'L', 'J'];
  const getSouth = (rowIndex: number, columnIndex: number) => grid[rowIndex + 1]?.[columnIndex];
  const wests = ['S', '-', 'L', 'F'];
  const getWest = (rowIndex: number, columnIndex: number) => grid[rowIndex][columnIndex - 1];

  let foundNonLoop = true;
  while (foundNonLoop) {
    foundNonLoop = false;
    for (let rowIndex = 0; rowIndex <= grid.length - 1; rowIndex++) {
      const row = grid[rowIndex];
      for (let columnIndex = 0; columnIndex <= row.length - 1; columnIndex++) {
        const tile = row[columnIndex];
        const isNorthConnected = norths.includes(getNorth(rowIndex, columnIndex));
        const isEastConnected = easts.includes(getEast(rowIndex, columnIndex));
        const isSouthConnected = souths.includes(getSouth(rowIndex, columnIndex));
        const isWestConnected = wests.includes(getWest(rowIndex, columnIndex));

        if ((tile === '|' && (!isNorthConnected || !isSouthConnected)) ||
            (tile === '-' && (!isEastConnected || !isWestConnected)) ||
            (tile === 'L' && (!isNorthConnected || !isEastConnected)) ||
            (tile === 'J' && (!isNorthConnected || !isWestConnected)) ||
            (tile === '7' && (!isSouthConnected || !isWestConnected)) ||
            (tile === 'F' && (!isEastConnected || !isSouthConnected)) ||
            tile === '.') {
          row[columnIndex] = '#';
          foundNonLoop = true;
        }
      }
    }
  }

  let foundSConnectedLoops = true;
  while (foundSConnectedLoops) {
    foundSConnectedLoops = false;
    for (let rowIndex = 0; rowIndex <= grid.length - 1; rowIndex++) {
      const row = grid[rowIndex];
      for (let columnIndex = 0; columnIndex <= row.length - 1; columnIndex++) {
        const tile = row[columnIndex];
        const isNorthSConnected = getNorth(rowIndex, columnIndex) === 'S';
        const isEastSConnected = getEast(rowIndex, columnIndex) === 'S';
        const isSouthSConnected = getSouth(rowIndex, columnIndex) === 'S';
        const isWestSConnected = getWest(rowIndex, columnIndex) === 'S';

        if ((tile === '|' && (isNorthSConnected || isSouthSConnected)) ||
            (tile === '-' && (isEastSConnected || isWestSConnected)) ||
            (tile === 'L' && (isNorthSConnected || isEastSConnected)) ||
            (tile === 'J' && (isNorthSConnected || isWestSConnected)) ||
            (tile === '7' && (isSouthSConnected || isWestSConnected)) ||
            (tile === 'F' && (isEastSConnected || isSouthSConnected))) {
          row[columnIndex] = 'S';
          foundSConnectedLoops = true;
        }
      }
    }
  }

  return Math.floor(grid.flat().filter(s => s === 'S').length / 2);
};

if (Deno.args && Deno.args[0] == 'solve') {
  const text = await readInput('day10a.txt');
  console.log(farthestSteps(text)); // 6882
}
