# Tower of Hanoi

[![Latest Release](https://img.shields.io/github/release/vanillaSlice/tower-of-hanoi.svg)](https://github.com/vanillaSlice/tower-of-hanoi/releases/latest)
[![Build Status](https://img.shields.io/travis/com/vanillaSlice/tower-of-hanoi/master.svg)](https://travis-ci.com/vanillaSlice/tower-of-hanoi)
[![License](https://img.shields.io/github/license/vanillaSlice/tower-of-hanoi.svg)](LICENSE)

Tower of Hanoi puzzle written to practice using [Babel](http://babeljs.io/) and [Webpack](https://webpack.js.org/).
A deployed version can be viewed [here](https://towerofhanoi.mikelowe.xyz/).

## Screenshot

![Screenshot](./images/screenshot-1.png)

## Background

Tower of Hanoi is a mathematical puzzle. It consists of three towers and a number of disks of different sizes, which
can slide onto any tower. The puzzle starts with the disks in ascending order of size on the first tower.

The objective of the puzzle is to move the entire stack of disks to the third tower, obeying the following simple
rules:

1. Only one disk can be moved at a time.
2. Each move consists of taking the upper disk from one of the stacks and placing it on top of another stack.
3. No disk may be placed on top of a smaller disk.

With 3 disks, the puzzle can be solved in 7 moves. The minimal number of moves required to solve a Tower of Hanoi
puzzle is 2<sup>n</sup> − 1, where n is the number of disks.

See [Wikipedia](https://en.wikipedia.org/wiki/Tower_of_Hanoi) for more information on Tower of Hanoi.

## Getting Started

### Prerequisites

* [npm](https://www.npmjs.com/)

### Installing Dependencies

From your terminal/command prompt run:

```
npm install
```

### Running

From your terminal/command prompt run:

```
npm start
```

Point your browser to [localhost:8080](http://localhost:8080).

## Technology Used

For those of you that are interested, the technology used in this project includes:

* [npm](https://www.npmjs.com/) (package management)
* [Webpack](https://webpack.js.org/) (application bundler)
* [PaperCSS](https://www.getpapercss.com/) (CSS framework)

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
