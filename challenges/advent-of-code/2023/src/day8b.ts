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
    });

  const gcd = (a: number, b: number): number => b === 0 ? a : gcd(b, a % b);
  const lcm = (a: number, b: number) => (a * b) / gcd(a, b);

  return stepsToZs.reduce((acc, steps) => lcm(acc, steps), 1);
};

if (Deno.args && Deno.args[0] == 'solve') {
  const text = await readInput('day8b.txt');
  console.log(countSteps(text)); // 9606140307013
}
