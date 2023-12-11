import { readInput } from './util.ts';

enum Direction {
  NORTH,
  EAST,
  SOUTH,
  WEST,
  NONE,
};

export const enclosedByLoop = (input: string): number => {
  const grid = input.split(/\r?\n/)
    .filter(line => line)
    .map(line => line.split(''));

  const getSLocation = () => {
    for (let rowIndex = 0; rowIndex < grid.length; rowIndex++) {
      const row = grid[rowIndex];
      for (let columnIndex = 0; columnIndex < row.length; columnIndex++) {
        if (row[columnIndex] === 'S') {
          return { sRowIndex: rowIndex, sColumnIndex: columnIndex };
        }
      }
    }
    throw Error('No S found');
  }
  const { sRowIndex, sColumnIndex } = getSLocation();

  const norths = ['S', '|', '7', 'F'];
  const getNorth = (rowIndex: number, columnIndex: number) => grid[rowIndex - 1]?.[columnIndex];
  const easts = ['S', '-', 'J', '7'];
  const getEast = (rowIndex: number, columnIndex: number) => grid[rowIndex][columnIndex + 1];
  const souths = ['S', '|', 'L', 'J'];
  const getSouth = (rowIndex: number, columnIndex: number) => grid[rowIndex + 1]?.[columnIndex];

  const stepCount: number[][] = [];
  for (let i = 0; i < grid.length; i++) {
    stepCount[i] = Array(grid[i].length);
  };

  let previousMove = Direction.NONE;
  let currentRowIndex = sRowIndex;
  let currentColumnIndex = sColumnIndex;
  let currentTile = grid[currentRowIndex][currentColumnIndex];
  let steps = 0;
  do {
    stepCount[currentRowIndex][currentColumnIndex] = steps;
    if (currentTile === 'S') {
      if (norths.includes(getNorth(currentRowIndex, currentColumnIndex))) {
        currentRowIndex--;
        previousMove = Direction.NORTH;
      } else if (easts.includes(getEast(currentRowIndex, currentColumnIndex))) {
        currentColumnIndex++;
        previousMove = Direction.EAST;
      } else if (souths.includes(getSouth(currentRowIndex, currentColumnIndex))) {
        currentRowIndex++;
        previousMove = Direction.SOUTH;
      } else {
        currentColumnIndex--;
        previousMove = Direction.WEST;
      }
    } else if (currentTile === '|') {
      if (previousMove === Direction.NORTH) {
        currentRowIndex--;
        previousMove = Direction.NORTH;
      } else {
        currentRowIndex++;
        previousMove = Direction.SOUTH;
      }
    } else if (currentTile === '-') {
      if (previousMove === Direction.EAST) {
        currentColumnIndex++;
        previousMove = Direction.EAST;
      } else {
        currentColumnIndex--;
        previousMove = Direction.WEST;
      }
    } else if (currentTile === 'L') {
      if (previousMove === Direction.SOUTH) {
        currentColumnIndex++;
        previousMove = Direction.EAST;
      } else {
        currentRowIndex--;
        previousMove = Direction.NORTH;
      }
    } else if (currentTile === 'J') {
      if (previousMove === Direction.SOUTH) {
        currentColumnIndex--;
        previousMove = Direction.WEST;
      } else {
        currentRowIndex--;
        previousMove = Direction.NORTH;
      }
    } else if (currentTile === '7') {
      if (previousMove === Direction.NORTH) {
        currentColumnIndex--;
        previousMove = Direction.WEST;
      } else {
        currentRowIndex++;
        previousMove = Direction.SOUTH;
      }
    } else if (currentTile === 'F') {
      if (previousMove === Direction.NORTH) {
        currentColumnIndex++;
        previousMove = Direction.EAST;
      } else {
        currentRowIndex++;
        previousMove = Direction.SOUTH;
      }
    }
    currentTile = grid[currentRowIndex][currentColumnIndex];
    steps++;
  } while (currentTile !== 'S');

  let numTilesInLoop = 0;

  console.log(steps);
  for (let i = 0; i < grid.length - 1; i++) {
    const row = stepCount[i];
    let diffCounter = 0;
    for (let j = 0; j < row.length; j++) {
      const current = row[j] === 0 ? row[j] + steps : row[j];
      const below = (stepCount[i + 1][j]);

      if (current - below === 1) {
        diffCounter++;
        console.log('Up:', i, j, grid[i][j], diffCounter);
      } else if (current - below === -1) {
        diffCounter--;
        console.log('Down:', i, j, grid[i][j], diffCounter);
      } else if (diffCounter !== 0 && current === undefined) {
        numTilesInLoop++;
        console.log('In Loop:', i, j, grid[i][j], diffCounter);
      }
    }
  };

  return numTilesInLoop;
}

if (Deno.args && Deno.args[0] == 'solve') {
  const text = await readInput('day10b.txt');
  console.log(enclosedByLoop(text)); // 491
}
