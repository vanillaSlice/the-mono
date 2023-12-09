import { readInput } from './util.ts';

export const marginOfError = (input: string): number => {
  const lines = input.split(/\r?\n/).filter(line => line);
  const raceTimes = lines[0].replace('Time:', '').match(/\d+/g)!.map(Number);
  const raceDistances = lines[1].replace('Distance:', '').match(/\d+/g)!.map(Number);

  return raceTimes.reduce((result, raceTime, raceIndex) => {
    let waysToWinRace = 0;
    for (let holdTime = 1; holdTime < raceTime; holdTime++) {
      const distancePossible = (raceTime - holdTime) * holdTime;
      if (distancePossible > raceDistances[raceIndex]) {
        waysToWinRace++;
      }
    }
    return result * waysToWinRace;
  }, 1);
};

if (Deno.args && Deno.args[0] == 'solve') {
  const text = await readInput('day6a.txt');
  console.log(marginOfError(text)); // 2449062
}
