/**
 * Horrible brute-force; takes ages to run.
 * TODO: improve performance
 */
import { readInput } from './util.ts';

interface ItemMaps  {
  [key: string]: ItemMap;
}

interface ItemMap {
  from: string;
  to: string;
  ranges: number[][];
}

export const lowestLocation = (input: string): number => {
  const almanac = input.split(/\r?\n/).filter(line => line);

  const seedPairs = almanac[0].split('seeds: ')[1]
    .match(/\d+ \d+/g)!
    .map(pair => pair.split(' ').map(Number));

  const itemMaps: ItemMaps = almanac.slice(1).reduce((acc, line) => {
    const { currentMapName } = acc;
    if (line.endsWith('map:')) {
      const newCurrentMapName = line.replace(' map:', '');
      const fromAndTo = newCurrentMapName.split('-to-');
      acc.currentMapName = newCurrentMapName;
      acc.itemMaps[newCurrentMapName] = {
        from: fromAndTo[0],
        to: fromAndTo[1],
        ranges: [],
      };
    } else {
      acc.itemMaps[currentMapName].ranges.push(line.split(' ').map(Number));
    }
    return acc;
  }, {
    currentMapName: '',
    itemMaps: {} as ItemMaps,
  }).itemMaps;

  let minLocation = Number.MAX_SAFE_INTEGER;

  for (const seedPair of seedPairs) {
    for (let seed = seedPair[0]; seed < seedPair[0] + seedPair[1]; seed++) {
      let from = 'seed';
      let destination = seed;
      while (true) {
        const itemMap = Object.values(itemMaps).find(value => value.from === from)!;
        const potentialDestination = itemMap.ranges.find((range) => range[1] <= destination && destination < range[1] + range[2]);
        if (potentialDestination) {
          destination = destination - potentialDestination[1] + potentialDestination[0];
        }
        from = itemMap.to;
        if (from === 'location') {
          minLocation = Math.min(minLocation, destination);
          break;
        }
      }
    }
  }

  return minLocation;
};

if (Deno.args && Deno.args[0] == 'solve') {
  const text = await readInput('day5b.txt');
  console.log(lowestLocation(text)); // 137516820
}
