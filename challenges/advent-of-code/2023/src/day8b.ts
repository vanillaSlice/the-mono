import { readInput } from './util.ts';

export const countSteps = (input: string): number => {
  const lines = input.split(/\r?\n/).filter(line => line);

  const instructions = lines[0];

  const nodes = lines.slice(1).reduce((acc, line) => {
    const groups = /(?<node>.*) = \((?<left>.*), (?<right>.*)\)/.exec(line)!.groups!;
    acc[groups.node] = [groups.left, groups.right];
    return acc;
  }, {} as Record<string, string[]>);

  const stepsToZs = Object.keys(nodes)
    .filter(node => node.endsWith('A'))
    .map(node => {
      let steps = 0;
      let currentLocation = node;
      while (true) {
        const currentStep = instructions[steps % instructions.length];
        const nextLocations = nodes[currentLocation];
        currentLocation = currentStep === 'L' ? nextLocations[0] : nextLocations[1];
        steps++;
        if (currentLocation.endsWith('Z')) {
          return steps;
        }
      }
    })
    .sort();

  const maxStepsToAZ = stepsToZs[stepsToZs.length - 1];
  let multiplier = 1;

  while (true) {
    if (stepsToZs.every(steps => maxStepsToAZ * multiplier % steps === 0)) {
      return maxStepsToAZ * multiplier;
    }
    multiplier++;
  }
};

if (Deno.args && Deno.args[0] == 'solve') {
  const text = await readInput('day8b.txt');
  console.log(countSteps(text));
}
