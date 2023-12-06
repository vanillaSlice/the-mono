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

  const locationMap = itemMaps['humidity-to-location'];
  const minPossibleLocation = 0;
  const maxPossibleLocation = Math.max(...locationMap.ranges.map(range => range[0] + range[2]));

  for (let i = minPossibleLocation; i <= maxPossibleLocation; i++) {
    console.log(i);
    const potentialLocationRange = locationMap.ranges.find(range => range[0] <= i && i < range[0] + range[2]);
    let previousLocation = potentialLocationRange ? i - potentialLocationRange[0] + potentialLocationRange[1] : i;
    let from = locationMap.from;

    while (true) {
      if (from === 'seed') {
        if (seedPairs.find(seedPair => seedPair[0] <= previousLocation && previousLocation < seedPair[0] + seedPair[1])) {
          return i;
        }
        break;
      }

      const itemMap = Object.values(itemMaps).find(value => value.to === from)!;
      const potentialDestination = itemMap.ranges.find((range) => range[0] <= previousLocation && previousLocation < range[0] + range[2]);
      if (potentialDestination) {
        previousLocation = previousLocation - potentialDestination[0] + potentialDestination[1];
      }
      from = itemMap.from;
    }
  }

  return 5;
};

if (Deno.args && Deno.args[0] == 'solve') {
  const text = await readInput('day5b.txt');
  console.log(lowestLocation(text));
}
