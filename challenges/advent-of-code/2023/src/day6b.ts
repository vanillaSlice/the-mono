import { readInput } from './util.ts';

export const waysToBeatRecord = (input: string): number => {
  const lines = input.split(/\r?\n/).filter(line => line);
  const raceTime = Number(lines[0].replace('Time:', '').replace(/\s/g, ''));
  const raceDistance = Number(lines[1].replace('Distance:', '').replace(/\s/g, ''));

  for (let offsetIndex = 1; offsetIndex <= raceTime / 2; offsetIndex++) {
    if ((raceTime - offsetIndex) * offsetIndex > raceDistance) {
      return raceTime - (2 * offsetIndex) + 1;
    }
  }

  return 0;
};

if (Deno.args && Deno.args[0] == 'solve') {
  const text = await readInput('day6b.txt');
  console.log(waysToBeatRecord(text));
}
