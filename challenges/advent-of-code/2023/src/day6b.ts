import { readInput } from './util.ts';

export const waysToBeatRecord = (input: string): number => {
  const lines = input.split(/\r?\n/).filter(line => line);
  const raceTime = Number(lines[0].replace('Time:', '').replace(/\s/g, ''));
  const raceDistance = Number(lines[1].replace('Distance:', '').replace(/\s/g, ''));

  for (let holdTime = 1; holdTime < raceTime; holdTime++) {
    if ((raceTime - holdTime) * holdTime > raceDistance) {
      return raceTime - (2 * holdTime) + 1;
    }
  }

  return 0;
};

if (Deno.args && Deno.args[0] == 'solve') {
  const text = await readInput('day6b.txt');
  console.log(waysToBeatRecord(text));
}
