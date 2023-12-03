import { readInput } from './util.ts';

const nonSymbols = new Set(['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.']);

export const sumPartNumbers = (input: string): number => {
  const lines = input.split(/\r?\n/)
    .filter(line => line.length);

  const isNorthSymbol = (x: number, y: number) => y != 0 && !nonSymbols.has(lines[y - 1][x]);
  const isNorthEastSymbol = (x: number, y: number, line: string) => y != 0 && x != line.length - 1 && !nonSymbols.has(lines[y - 1][x + 1]);
  const isEastSymbol = (x: number, y: number, line: string) => x != line.length - 1 && !nonSymbols.has(lines[y][x + 1]);
  const isSouthEastSymbol = (x: number, y: number, line: string) => y != lines.length - 1 && x != line.length - 1 && !nonSymbols.has(lines[y + 1][x + 1]);
  const isSouthSymbol = (x: number, y: number) => y != lines.length - 1 && !nonSymbols.has(lines[y + 1][x]);
  const isSouthWestSymbol = (x: number, y: number) => y != lines.length - 1 && x != 0 && !nonSymbols.has(lines[y + 1][x - 1]);
  const isWestSymbol = (x: number, y: number) => x != 0 && !nonSymbols.has(lines[y][x - 1]);
  const isNorthWestSymbol = (x: number, y: number) => y != 0 && x != 0 && !nonSymbols.has(lines[y - 1][x - 1]);

  return lines.reduce((sum, line, rowIndex) => {
    const matches = line.matchAll(/(\d+)/g);
    for (const match of matches) {
      let isPart = false;
      for (let columnIndex = match.index!; columnIndex < match.index! + match[0].length; columnIndex++) {
        if (isNorthSymbol(columnIndex, rowIndex) ||
            isNorthEastSymbol(columnIndex, rowIndex, line) ||
            isEastSymbol(columnIndex, rowIndex, line) ||
            isSouthEastSymbol(columnIndex, rowIndex, line) ||
            isSouthSymbol(columnIndex, rowIndex) ||
            isSouthWestSymbol(columnIndex, rowIndex) ||
            isWestSymbol(columnIndex, rowIndex) ||
            isNorthWestSymbol(columnIndex, rowIndex)) {
          isPart = true;
        }
      }
      if (isPart) {
        sum += Number(match[0]);
      }
    }
    return sum;
  }, 0);
};

if (Deno.args && Deno.args[0] == 'solve') {
  const text = await readInput('day3a.txt');
  console.log(sumPartNumbers(text));
}
