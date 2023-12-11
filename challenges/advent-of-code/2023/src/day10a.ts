import { readInput } from './util.ts';

enum Direction {
  NORTH,
  EAST,
  SOUTH,
  WEST,
  NONE,
};

export const farthestSteps = (input: string): number => {
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

  let previousMove = Direction.NONE;
  let currentRowIndex = sRowIndex;
  let currentColumnIndex = sColumnIndex;
  let currentTile = grid[currentRowIndex][currentColumnIndex];
  let steps = 0;
  do {
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

  return Math.floor(steps / 2);
};

if (Deno.args && Deno.args[0] == 'solve') {
  const text = await readInput('day10a.txt');
  console.log(farthestSteps(text)); // 6882
}
