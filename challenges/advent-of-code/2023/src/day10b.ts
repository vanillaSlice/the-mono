import { readInput } from './util.ts';

export const enclosedByLoop = (input: string): number => {
  const grid = input.split(/\r?\n/)
    .filter(line => line)
    .map(line => line.split(''));

  const norths = ['S', '|', '7', 'F'];
  const souths = ['S', '|', 'L', 'J'];
  const easts = ['S', '-', 'J', '7'];
  const wests = ['S', '-', 'L', 'F'];
  const isNorthConnected = (a: number, b: number) => {
    const north = grid[a - 1] ? grid[a - 1][b] : '';
    return norths.includes(north);
  }
  const isSouthConnected = (a: number, b: number) => {
    const south = grid[a + 1] ? grid[a + 1][b] : '';
    return souths.includes(south);
  }
  const isEastConnected = (a: number, b: number) => {
    const east = grid[a][b + 1] ?? '';
    return easts.includes(east);
  }
  const isWestConnected = (a: number, b: number) => {
    const west = grid[a][b - 1] ?? '';
    return wests.includes(west);
  }
  const isNorthSConnected = (a: number, b: number) => {
    const north = grid[a - 1] ? grid[a - 1][b] : '';
    return north.startsWith('S');
  }
  const isSouthSConnected = (a: number, b: number) => {
    const south = grid[a + 1] ? grid[a + 1][b] : '';
    return south.startsWith('S');
  }
  const isEastSConnected = (a: number, b: number) => {
    const east = grid[a][b + 1] ?? '';
    return east.startsWith('S');
  }
  const isWestSConnected = (a: number, b: number) => {
    const west = grid[a][b - 1] ?? '';
    return west.startsWith('S');
  }

  let foundNonLoop = true;
  while (foundNonLoop) {
    foundNonLoop = false;
    for (let i = 0; i <= grid.length - 1; i++) {
      const row = grid[i];
      for (let j = 0; j <= row.length - 1; j++) {
        const current = row[j];
        if (current === '|' && (!isNorthConnected(i, j) ||  !isSouthConnected(i, j))) {
          row[j] = '#';
          foundNonLoop = true;
        } else if (current === '-' && (!isEastConnected(i, j) ||  !isWestConnected(i, j))) {
          row[j] = '#';
          foundNonLoop = true;
        } else if (current === 'L' && (!isEastConnected(i, j) ||  !isNorthConnected(i, j))) {
          row[j] = '#';
          foundNonLoop = true;
        } else if (current === 'J' && (!isWestConnected(i, j) ||  !isNorthConnected(i, j))) {
          row[j] = '#';
          foundNonLoop = true;
        } else if (current === '7' && (!isWestConnected(i, j) ||  !isSouthConnected(i, j))) {
          row[j] = '#';
          foundNonLoop = true;
        } else if (current === 'F' && (!isEastConnected(i, j) ||  !isSouthConnected(i, j))) {
          row[j] = '#';
          foundNonLoop = true;
        } else if (current === '.') {
          row[j] = '#';
        }
      }
    }
  }

  let foundSConnectedLoops = true;
  while (foundSConnectedLoops) {
    foundSConnectedLoops = false;
    for (let i = 0; i <= grid.length - 1; i++) {
      const row = grid[i];
      for (let j = 0; j <= row.length - 1; j++) {
        const current = row[j];
        if (current === '|' && (isNorthSConnected(i, j) || isSouthSConnected(i , j))) {
          row[j] = `S${current}`;
          foundSConnectedLoops = true;
        } else if (current === '-' && (isEastSConnected(i, j) ||  isWestSConnected(i, j))) {
          row[j] = `S${current}`;
          foundSConnectedLoops = true;
        } else if (current === 'L' && (isEastSConnected(i, j) ||  isNorthSConnected(i, j))) {
          row[j] = `S${current}`;
          foundSConnectedLoops = true;
        } else if (current === 'J' && (isWestSConnected(i, j) ||  isNorthSConnected(i, j))) {
          row[j] = `S${current}`;
          foundSConnectedLoops = true;
        } else if (current === '7' && (isWestSConnected(i, j) ||  isSouthSConnected(i, j))) {
          row[j] = `S${current}`;
          foundSConnectedLoops = true;
        } else if (current === 'F' && (isEastSConnected(i, j) ||  isSouthSConnected(i, j))) {
          row[j] = `S${current}`;
          foundSConnectedLoops = true;
        }
      }
    }
  }

  for (let i = 0; i <= grid.length - 1; i++) {
    const row = grid[i];
    for (let j = 0; j <= row.length - 1; j++) {
      const current = row[j];
      if (current !== '#' && !current.startsWith('S')) {
        grid[i][j] = '#';
      }
    }
  }

  const gapsInPipes: string[][] = [];
  for (let i = 0; i <= grid.length - 1; i++) {
    const row = grid[i];
    const newRow = new Array(row.length) as string[];
    gapsInPipes[i] = newRow;
  }

  for (let i = 0; i < grid.length - 1; i++) {
    const row = grid[i];
    const gapRow = gapsInPipes[i];
    for (let j = 0; j < row.length - 1; j++) {
      const current = row[j].replace('S', '');
      const right = row[j + 1].replace('S', '');
      const below = grid[i + 1][j].replace('S', '');
      if (['|', 'J', '7'].includes(current) && ['|', 'L', 'F'].includes(right)) {
        gapRow[j] = 'V#';
        gapRow[j + 1] = 'V#';
      } else if (['-', 'J', 'L'].includes(current) && ['-', '7', 'F'].includes(below)) {
        gapRow[j] = 'H#';
        gapsInPipes[i + 1][j] = 'H#';
      }
    }
  }

  let changesMade = true;
  while (changesMade) {
    changesMade = false;
    for (let i = 0; i < grid.length; i++) {
      const row = grid[i];
      for (let j = 0; j < row.length; j++) {
        const current = grid[i][j];
        if (current === '#') {
          if (i === 0 || i === grid.length - 1 || j === 0 || j === row.length - 1) {
            grid[i][j] = 'O';
            changesMade = true;
          } else {
            const northRow = grid[i - 1];
            const southRow = grid[i + 1];
            const north = northRow ? northRow[j] : undefined;
            const northEast = northRow ? northRow[j + 1] : undefined;
            const east = grid[i][j + 1];
            const southEast = southRow ? southRow[j + 1] : undefined;
            const south = southRow ? southRow[j] : undefined;
            const southWest = southRow ? southRow[j - 1] : undefined;
            const west = grid[i][j - 1];
            const northWest = northRow ? northRow[j - 1] : undefined;
            if ([north, northEast, east, southEast, south, southWest, west, northWest].includes('O')) {
              grid[i][j] = 'O';
              changesMade = true;
            }
          }
        }
        const gapNorthRow = gapsInPipes[i - 1];
        const gapsSouthRow = gapsInPipes[i + 1];
        if (current === '#') {
          const northGaps = gapNorthRow ? [gapNorthRow[j - 1], gapNorthRow[j], gapNorthRow[j + 1]]
          .filter(gap => gap)
          .filter(gap => gap.startsWith('V')) : [];
          const southGaps = gapsSouthRow ? [gapsSouthRow[j - 1], gapsSouthRow[j], gapsSouthRow[j + 1]]
            .filter(gap => gap)
            .filter(gap => gap.startsWith('V')) : [];
          const eastGaps = [gapsInPipes[i][j + 1], gapNorthRow ? gapNorthRow[j + 1] : undefined, gapsSouthRow ? gapsSouthRow[j + 1] : undefined]
            .filter(gap => gap)
            .filter(gap => gap!.startsWith('H'));
          const westGaps = [gapsInPipes[i][j - 1], gapNorthRow ? gapNorthRow[j - 1] : undefined, gapsSouthRow ? gapsSouthRow[j - 1] : undefined]
            .filter(gap => gap)
            .filter(gap => gap!.startsWith('H'));
          if (northGaps.includes('VO') || southGaps.includes('VO') || eastGaps.includes('HO') || westGaps.includes('HO')) {
            grid[i][j] = '0';
            changesMade = true;
          }
        } else if (current === 'O') {
          // verticals
          if (gapNorthRow) {
            const northEast = gapsInPipes[i - 1][j + 1];
            const north = gapsInPipes[i - 1][j];
            const northWest = gapsInPipes[i - 1][j - 1];
            if (northEast === 'V#' && north === 'V#') {
              changesMade = true;
              gapsInPipes[i - 1][j + 1] = 'VO';
              gapsInPipes[i - 1][j] = 'VO';
            }
            if (north === 'V#' && northWest === 'V#') {
              changesMade = true;
              gapsInPipes[i - 1][j] = 'VO';
              gapsInPipes[i - 1][j - 1] = 'VO';
            }
          }
          if (gapsSouthRow) {
            const southEast = gapsInPipes[i + 1][j + 1];
            const south = gapsInPipes[i + 1][j];
            const southWest = gapsInPipes[i + 1][j - 1];
            if (southEast === 'V#' && south === 'V#') {
              changesMade = true;
              gapsInPipes[i + 1][j + 1] = 'VO';
              gapsInPipes[i + 1][j] = 'VO';
            }
            if (south === 'V#' && southWest === 'V#') {
              changesMade = true;
              gapsInPipes[i + 1][j] = 'VO';
              gapsInPipes[i + 1][j - 1] = 'VO';
            }
          }
          // horizontals
          const northEast = gapNorthRow ? gapsInPipes[i - 1][j + 1] : undefined;
          const east = gapsInPipes[i][j + 1];
          const southEast = gapsSouthRow ? gapsInPipes[i + 1][j + 1] : undefined;
          if (northEast === 'H#' && east === 'H#') {
            changesMade = true;
            gapsInPipes[i - 1][j + 1] = 'HO';
            gapsInPipes[i][j + 1] = 'HO';
          }
          if (southEast === 'H#' && east === 'H#') {
            changesMade = true;
            gapsInPipes[i + 1][j + 1] = 'HO';
            gapsInPipes[i][j + 1] = 'HO';
          }
          const northWest = gapNorthRow ? gapsInPipes[i - 1][j - 1] : undefined;
          const west = gapsInPipes[i][j - 1];
          const southWest = gapsSouthRow ? gapsInPipes[i + 1][j - 1] : undefined;
          if (northWest === 'H#' && west === 'H#') {
            changesMade = true;
            gapsInPipes[i - 1][j - 1] = 'HO';
            gapsInPipes[i][j - 1] = 'HO';
          }
          if (southWest === 'H#' && west === 'H#') {
            changesMade = true;
            gapsInPipes[i + 1][j - 1] = 'HO';
            gapsInPipes[i][j - 1] = 'HO';
          }
        }
      }
    }
  }

  return grid.flat().filter(s => s === '#').length;
};

if (Deno.args && Deno.args[0] == 'solve') {
  const text = await readInput('day10b.txt');
  console.log(enclosedByLoop(text)); // 766 is too big
}
