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

  const seeds = almanac[0].split('seeds: ')[1].split(' ').map(Number);

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

  const locations = seeds.map(seed => {
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
        return destination;
      }
    }
  });

  return Math.min(...locations);
};

if (Deno.args && Deno.args[0] == 'solve') {
  const text = await readInput('day5a.txt');
  console.log(lowestLocation(text)); // 389056265
}
