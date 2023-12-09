import { readInput } from './util.ts';

export const countSteps = (input: string): number => {
  const lines = input.split(/\r?\n/).filter(line => line);

  const instructions = lines[0];

  const nodes = lines.slice(1).reduce((acc, line) => {
    const groups = /(?<node>.*) = \((?<left>.*), (?<right>.*)\)/.exec(line)!.groups!;
    acc[groups.node] = [groups.left, groups.right];
    return acc;
  }, {} as Record<string, string[]>);

  let steps = 0;
  let currentLocation = 'AAA';

  while (true) {
    const currentStep = instructions[steps % instructions.length];
    const nextLocations = nodes[currentLocation];
    currentLocation = currentStep === 'L' ? nextLocations[0] : nextLocations[1];
    steps++;
    if (currentLocation === 'ZZZ') {
      return steps;
    }
  }
};

if (Deno.args && Deno.args[0] == 'solve') {
  const text = await readInput('day8a.txt');
  console.log(countSteps(text)); // 19241
}
