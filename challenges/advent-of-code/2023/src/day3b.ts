import { readInput } from './util.ts';

interface Coordinate {
  x: number;
  y: number;
  partNumber: number;
}

export const sumGearRatios = (input: string): number => {
  const lines = input.split(/\r?\n/)
    .filter(line => line.length);

  const gears: Record<string, Record<string, number>> = {};

  const isNorthGear = (x: number, y: number) => y != 0 && lines[y - 1][x] === '*';
  const isNorthEastGear = (x: number, y: number, line: string) => y != 0 && x != line.length - 1 && lines[y - 1][x + 1] === '*';
  const isEastGear = (x: number, y: number, line: string) => x != line.length - 1 && lines[y][x + 1] === '*';
  const isSouthEastGear = (x: number, y: number, line: string) => y != lines.length - 1 && x != line.length - 1 && lines[y + 1][x + 1] === '*';
  const isSouthGear = (x: number, y: number) => y != lines.length - 1 && lines[y + 1][x] === '*';
  const isSouthWestGear = (x: number, y: number) => y != lines.length - 1 && x != 0 && lines[y + 1][x - 1] === '*';
  const isWestGear = (x: number, y: number) => x != 0 && lines[y][x - 1] === '*';
  const isNorthWestGear = (x: number, y: number) => y != 0 && x != 0 && lines[y - 1][x - 1] === '*';

  const addToGears = (x: number, y: number, partCoordinate: Coordinate) => {
    const key = `${y}-${x}`;
    if (!(key in gears)) {
      gears[key] = {};
    }
    gears[key][`${partCoordinate.y}-${partCoordinate.x}`] = partCoordinate.partNumber;
  }

  lines.forEach((line, rowIndex) => {
    const matches = line.matchAll(/(\d+)/g);
    for (const match of matches) {
      for (let columnIndex = match.index!; columnIndex < match.index! + match[0].length; columnIndex++) {
        const partCoordinate = { x: match.index!, y: rowIndex, partNumber: Number(match[0]) }

        if (isNorthGear(columnIndex, rowIndex)) {
          addToGears(columnIndex, rowIndex - 1, partCoordinate);
        }
        if (isNorthEastGear(columnIndex, rowIndex, line)) {
          addToGears(columnIndex + 1, rowIndex - 1, partCoordinate);
        }
        if (isEastGear(columnIndex, rowIndex, line)) {
          addToGears(columnIndex + 1, rowIndex, partCoordinate);
        }
        if (isSouthEastGear(columnIndex, rowIndex, line)) {
          addToGears(columnIndex + 1, rowIndex + 1, partCoordinate);
        }
        if (isSouthGear(columnIndex, rowIndex)) {
          addToGears(columnIndex, rowIndex + 1, partCoordinate);
        }
        if (isSouthWestGear(columnIndex, rowIndex)) {
          addToGears(columnIndex - 1, rowIndex + 1, partCoordinate);
        }
        if (isWestGear(columnIndex, rowIndex)) {
          addToGears(columnIndex - 1, rowIndex, partCoordinate);
        }
        if (isNorthWestGear(columnIndex, rowIndex)) {
          addToGears(columnIndex - 1, rowIndex - 1, partCoordinate);
        }
      }
    }
  });

  return Object.values(gears)
    .filter(gears => Object.entries(gears).length === 2)
    .reduce((sum, coords) => sum + Object.values(coords).reduce((s, c) => c * s), 0);
};

if (Deno.args && Deno.args[0] == 'solve') {
  const text = await readInput('day3b.txt');
  console.log(sumGearRatios(text));
}
