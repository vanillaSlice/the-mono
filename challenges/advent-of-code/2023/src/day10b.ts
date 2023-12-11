import { readInput } from './util.ts';

enum Direction {
  NORTH,
  EAST,
  SOUTH,
  WEST,
  NONE,
}

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

  let direction = Direction.NONE;
  let rowIndex = sRowIndex;
  let columnIndex = sColumnIndex;
  let currentTile = grid[rowIndex][columnIndex];
  let steps = 0;
  const stepCount: number[][] = [...grid.map(_ => [])];

  do {
    if (currentTile === 'S') {
      if (['|', '7', 'F'].includes(grid[rowIndex - 1]?.[columnIndex])) {
        rowIndex--;
        direction = Direction.NORTH;
      } else if (['-', 'J', '7'].includes(grid[rowIndex][columnIndex + 1])) {
        columnIndex++;
        direction = Direction.EAST;
      } else if (['|', 'L', 'J'].includes(grid[rowIndex + 1]?.[columnIndex])) {
        rowIndex++;
        direction = Direction.SOUTH;
      } else {
        columnIndex--;
        direction = Direction.WEST;
      }
    } else if (currentTile === '|') {
      if (direction === Direction.NORTH) {
        rowIndex--;
      } else {
        rowIndex++;
        direction = Direction.SOUTH;
      }
    } else if (currentTile === '-') {
      if (direction === Direction.EAST) {
        columnIndex++;
      } else {
        columnIndex--;
        direction = Direction.WEST;
      }
    } else if (currentTile === 'L') {
      if (direction === Direction.SOUTH) {
        columnIndex++;
        direction = Direction.EAST;
      } else {
        rowIndex--;
        direction = Direction.NORTH;
      }
    } else if (currentTile === 'J') {
      if (direction === Direction.SOUTH) {
        columnIndex--;
        direction = Direction.WEST;
      } else {
        rowIndex--;
        direction = Direction.NORTH;
      }
    } else if (currentTile === '7') {
      if (direction === Direction.NORTH) {
        columnIndex--;
        direction = Direction.WEST;
      } else {
        rowIndex++;
        direction = Direction.SOUTH;
      }
    } else if (currentTile === 'F') {
      if (direction === Direction.NORTH) {
        columnIndex++;
        direction = Direction.EAST;
      } else {
        rowIndex++;
        direction = Direction.SOUTH;
      }
    }
    currentTile = grid[rowIndex][columnIndex];
    steps++;
    stepCount[rowIndex][columnIndex] = steps;
  } while (currentTile !== 'S');

  let numTilesInLoop = 0;

  for (let rowIndex = 0; rowIndex < grid.length - 1; rowIndex++) {
    const row = stepCount[rowIndex];
    let crossingCounter = 0;
    for (let columnIndex = 0; columnIndex < row.length; columnIndex++) {
      const currentTile = row[columnIndex];
      const belowTile = stepCount[rowIndex + 1][columnIndex];
      const diff = currentTile - belowTile;
      if (diff === 1) {
        crossingCounter++;
      } else if (diff === -1) {
        crossingCounter--;
      } else if (crossingCounter !== 0 && !currentTile) {
        numTilesInLoop++;
      }
    }
  }

  return numTilesInLoop;
}

if (Deno.args && Deno.args[0] == 'solve') {
  const text = await readInput('day10b.txt');
  console.log(enclosedByLoop(text)); // 491
}
