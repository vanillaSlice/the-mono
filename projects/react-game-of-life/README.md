# React Game of Life

[![Build Status](https://img.shields.io/github/workflow/status/vanillaSlice/the-mono/React%20Game%20of%20Life/main)](https://github.com/vanillaSlice/the-mono/actions?query=workflow%3AReact-Game-of-Life+branch%3Amain)
[![Coverage Status](https://img.shields.io/codecov/c/gh/vanillaSlice/the-mono/main?flag=ReactGameOfLife)](https://codecov.io/gh/vanillaSlice/the-mono/tree/main/projects/react-game-of-life)
[![License](https://img.shields.io/badge/license-MIT-green)](LICENSE)

*The Game of Life* is a cellular automaton devised by mathematician John Conway.

In the game there exists a grid of cells, each of which can be in one of two states, *alive* or *dead*.
Every cell has eight neighbours (adjacent cells) which it interacts with. After each step in the game, the following
rules apply:

1. Any live cell with fewer than two live neighbours dies, i.e. underpopulation.
2. Any live cell with two or three live neighbours lives on to the next generation.
3. Any live cell with more than three live neighbours dies, i.e. overpopulation.
4. Any dead cell with exactly three live neighbours becomes a live cell, i.e. reproduction.

An example of cell interaction:

![Cell Interaction](./images/pattern-1.gif)

See [Wikipedia](https://en.wikipedia.org/wiki/Conway's_Game_of_Life) for more information on *The Game of Life*.

A deployed version can be viewed [here](https://reactgameoflife.mikelowe.xyz/).

## Screenshot

![Screenshot](./images/screenshot-1.png)

## Getting Started

### Prerequisites

* [Node.js](https://nodejs.org/en/)

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

This will run the development server on [localhost:3000](http://localhost:3000).

## Technology Used

For those of you that are interested, the technology used in this project includes:

* [React](https://reactjs.org/)

## Useful Links

Resources useful for the completion of this project:

* [Create React App](https://github.com/facebook/create-react-app) (React starter kit)

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
