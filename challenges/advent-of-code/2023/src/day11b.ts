import { readInput } from './util.ts';

export const sumLengths = (input: string, expansionMultiplier = 1000000): number => {
  const { expandedRows, expandedColumns, galaxyCoords } = input.split(/\r?\n/)
    .filter(line => line)
    .map(line => line.split(''))
    .reduce((details, row, rowIndex) => {
      const { expandedRows, expandedColumns, galaxyCoords } = details;
      expandedRows[rowIndex] = true;
      row.forEach((cell, columnIndex) => {
        if (cell === '#') {
          expandedRows[rowIndex] = false;
          expandedColumns[columnIndex] = false;
          galaxyCoords.push([rowIndex, columnIndex]);
        } else if (rowIndex === 0) {
          expandedColumns[columnIndex] = true;
        }
      });
      return details;
    }, {
      expandedRows: {} as Record<number, boolean>,
      expandedColumns: {} as Record<number, boolean>,
      galaxyCoords: [] as number[][],
    });

  let totalDistance = 0;

  for (let leftGalaxyIndex = 0; leftGalaxyIndex < galaxyCoords.length - 1; leftGalaxyIndex++) {
    const leftGalaxy = galaxyCoords[leftGalaxyIndex];
    const leftGalaxyX = leftGalaxy[0];
    const leftGalaxyY = leftGalaxy[1];

    for (let rightGalaxyIndex = leftGalaxyIndex + 1; rightGalaxyIndex < galaxyCoords.length; rightGalaxyIndex++) {
      let expandedGaps = 0;

      const rightGalaxy = galaxyCoords[rightGalaxyIndex];
      const rightGalaxyX = rightGalaxy[0];
      const rightGalaxyY = rightGalaxy[1];

      const [minGalaxyX, maxGalaxyX] = [Math.min(leftGalaxyX, rightGalaxyX), Math.max(leftGalaxyX, rightGalaxyX)];
      for (let x = minGalaxyX; x <= maxGalaxyX; x++) {
        if (expandedRows[x]) {
          expandedGaps += expansionMultiplier - 1;
        }
      }

      const [minGalaxyY, maxGalaxyY] = [Math.min(leftGalaxyY, rightGalaxyY), Math.max(leftGalaxyY, rightGalaxyY)];
      for (let y = minGalaxyY; y <= maxGalaxyY; y++) {
        if (expandedColumns[y]) {
          expandedGaps += expansionMultiplier - 1;
        }
      }

      totalDistance += Math.abs(leftGalaxyX - rightGalaxyX) + Math.abs(leftGalaxyY - rightGalaxyY) + expandedGaps;
    }
  }

  return totalDistance;
};

if (Deno.args && Deno.args[0] == 'solve') {
  const text = await readInput('day11b.txt');
  console.log(sumLengths(text)); // 458191688761
}
