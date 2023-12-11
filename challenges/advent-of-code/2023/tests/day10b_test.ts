import { assertEquals } from './deps.ts';
import { enclosedByLoop } from '../src/day10b.ts';

Deno.test('enclosedByLoop - small example', () => {
  const input = '...........\n.S-------7.\n.|F-----7|.\n.||.....||.\n.||.....||.\n.|L-7.F-J|.\n.|..|.|..|.\n.L--J.L--J.\n...........';
  const output = enclosedByLoop(input);
  assertEquals(output, 4);
});

Deno.test('enclosedByLoop - pipe gap example', () => {
  const input = '..........\n.S------7.\n.|F----7|.\n.||....||.\n.||....||.\n.|L-7F-J|.\n.|..||..|.\n.L--JL--J.\n..........';
  const output = enclosedByLoop(input);
  assertEquals(output, 4);
});

Deno.test('enclosedByLoop - larger example', () => {
  const input = '.F----7F7F7F7F-7....\n.|F--7||||||||FJ....\n.||.FJ||||||||L7....\nFJL7L7LJLJ||LJ.L-7..\nL--J.L7...LJS7F-7L7.\n....F-J..F7FJ|L7L7L7\n....L7.F7||L7|.L7L7|\n.....|FJLJ|FJ|F7|.LJ\n....FJL-7.||.||||...\n....L---J.LJ.LJLJ...';
  const output = enclosedByLoop(input);
  assertEquals(output, 8);
});

Deno.test('enclosedByLoop - complex example', () => {
  const input = 'FF7FSF7F7F7F7F7F---7\nL|LJ||||||||||||F--J\nFL-7LJLJ||||||LJL-77\nF--JF--7||LJLJ7F7FJ-\nL---JF-JLJ.||-FJLJJ7\n|F|F-JF---7F7-L7L|7|\n|FFJF7L7F-JF7|JL---7\n7-L-JL7||F7|L7F-7F7|\nL.L7LFJ|||||FJL7||LJ\nL7JLJL-JLJLJL--JLJ.L';
  const output = enclosedByLoop(input);
  assertEquals(output, 10);
});
