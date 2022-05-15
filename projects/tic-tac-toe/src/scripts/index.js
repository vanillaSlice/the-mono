import { version } from '../../package.json';

/*
 * DOM Elements
 */

const modeInputElements = document.querySelectorAll('.js-mode-input');
const playersElement = document.querySelector('.js-players');
let playerXElement = document.querySelector('.js-player-x');
let playerXScoreElement = document.querySelector('.js-player-x-score');
const playerOElement = document.querySelector('.js-player-o');
const playerOScoreElement = document.querySelector('.js-player-o-score');
const messageElement = document.querySelector('.js-message');
const gridElement = document.querySelector('.js-grid');
let gridMaskElement = document.querySelector('.js-grid-mask');
const cellElements = document.querySelectorAll('.js-cell');
const restartBtnElement = document.querySelector('.js-restart-btn');
const versionElement = document.querySelector('.js-version');

/*
 * SVG Templates
 */

const crossTemplate = '<svg width="94" height="94">'
  + '<path class="cross-one" stroke="#545454" stroke-width="8" fill="none" d="M17,17L77,77" />'
  + '<path class="cross-two" stroke="#545454" stroke-width="8" fill="none" d="M77,17L17,77" />'
  + '</svg>';

const circleTemplate = '<svg width="94" height="94">'
  + '<path class="circle" stroke="#f2ebd3" stroke-width="8" fill="none" d="M17,47c0,16.569,13.431,30,30,30s30,-13.431,30-30s-13.431,-30,-30,-30s-30,13.431,-30,30z" />'
  + '</svg>';

/*
 * Winning Combinations
 */

const winningLines = [
  [0, 1, 2],
  [3, 4, 5],
  [6, 7, 8],
  [0, 3, 6],
  [1, 4, 7],
  [2, 5, 8],
  [0, 4, 8],
  [2, 4, 6],
];

/*
 * Players
 */

class Player {
  constructor(symbol) {
    this.symbol = symbol;
    this.type = '';
    this.score = 0;
  }
}

const playerX = new Player('x');
const playerO = new Player('o');

/*
 * State
 */

let mode = 'medium';
let playerWithTurn;
const grid = Array(9);
let hasStarted;
let isGameOver;

/*
 * Functions
 */

function handleModeChange(e) {
  mode = e.target.value;
  resetGame();
}

function resetGame() {
  resetState();
  resetDomElements();

  if (mode === 'multiplayer') {
    startMultiplayerGame();
  } else {
    setupGameAgainstComputer();
  }
}

function resetState() {
  playerX.type = 'human';
  playerWithTurn = playerX;
  grid.fill('');
  hasStarted = false;
  isGameOver = false;
}

function resetDomElements() {
  highlightPlayerElementWithTurn();
  replacePlayerXElement();
  clearCellElements();
  replaceGridMaskElement();
}

function highlightPlayerElementWithTurn() {
  playerXElement.classList.toggle('has-turn', playerWithTurn === playerX);
  playerOElement.classList.toggle('has-turn', playerWithTurn === playerO);
}

// Do this so the border animation plays
function replacePlayerXElement() {
  const newPlayerXElement = playerXElement.cloneNode(true);
  playersElement.replaceChild(newPlayerXElement, playerXElement);
  playerXElement = newPlayerXElement;
  [, playerXScoreElement] = playerXElement.children;
}

function clearCellElements() {
  cellElements.forEach((e) => {
    e.innerHTML = '';
  });
}

// Do this so the grid animation plays
function replaceGridMaskElement() {
  const newGridMaskElement = gridMaskElement.cloneNode(true);
  gridElement.replaceChild(newGridMaskElement, gridMaskElement);
  gridMaskElement = newGridMaskElement;
}

function startMultiplayerGame() {
  playerO.type = 'human';
  startGame();
}

function startGame() {
  hasStarted = true;
  setMessage(`${playerWithTurn.symbol} turn`);
  if (playerWithTurn.type === 'computer') {
    computerMakeMove();
  }
}

function setMessage(message) {
  messageElement.innerHTML = `<p class="text">${message}</p>`;
}

function computerMakeMove() {
  let index = findBestMoveIndex();

  if (mode === 'easy' || mode === 'medium') {
    const emptyCells = grid.reduce((acc, cell, i) => {
      if (cell === '') {
        acc.push(i);
      }
      return acc;
    }, []);

    const threshold = mode === 'easy' ? 0.1 : 0.9;
    if (Math.random() > threshold && emptyCells.length > 1) {
      emptyCells.splice(emptyCells.indexOf(index), 1);
      index = emptyCells[Math.floor(Math.random() * emptyCells.length)];
    }
  }

  setTimeout(() => makeMove(index), 1000);
}

function findBestMoveIndex() {
  const bestMove = { points: -2 };
  const { symbol } = playerWithTurn;

  grid.forEach((_, index) => {
    if (grid[index] !== '') {
      return;
    }

    const points = minimax(index, symbol, symbol);

    if (bestMove.points < points) {
      bestMove.points = points;
      bestMove.index = index;
    }
  });

  return bestMove.index;
}

function minimax(index, symbol, computerSymbol) {
  grid[index] = symbol;

  if (isWinner(symbol)) {
    grid[index] = '';
    return symbol === computerSymbol ? 1 : -1;
  }

  if (isGridFilled()) {
    grid[index] = '';
    return 0;
  }

  let points;
  const nextSymbol = symbol === 'o' ? 'x' : 'o';

  if (nextSymbol === computerSymbol) {
    points = grid.reduce((max, cur, i) => (cur === '' ? Math.max(max, minimax(i, nextSymbol, computerSymbol)) : max), -2);
  } else {
    points = grid.reduce((min, cur, i) => (cur === '' ? Math.min(min, minimax(i, nextSymbol, computerSymbol)) : min), 2);
  }

  grid[index] = '';

  return points;
}

function isWinner(symbol) {
  return winningLines.some((line) => {
    const first = grid[line[0]];
    const second = grid[line[1]];
    const third = grid[line[2]];
    return symbol === first && symbol === second && symbol === third;
  });
}

function isGridFilled() {
  return grid.every(e => e !== '');
}

function makeMove(index) {
  if (!isMoveAllowed(index)) {
    return;
  }

  const { symbol } = playerWithTurn;

  grid[index] = symbol;
  cellElements[index].innerHTML = grid[index] === 'o' ? circleTemplate : crossTemplate;

  if (isWinner(playerWithTurn.symbol)) {
    playerWithTurn.score += 1;
    playerXScoreElement.innerText = playerX.score === 0 ? '-' : playerX.score;
    playerOScoreElement.innerText = playerO.score === 0 ? '-' : playerO.score;
    setMessage(`${symbol} wins`);
    isGameOver = true;
    return;
  }

  if (isGridFilled()) {
    setMessage('Draw');
    isGameOver = true;
    return;
  }

  playerWithTurn = playerWithTurn === playerX ? playerO : playerX;
  highlightPlayerElementWithTurn();
  setMessage(`${playerWithTurn.symbol} turn`);

  if (playerWithTurn.type === 'computer') {
    computerMakeMove();
  }
}

function isMoveAllowed(index) {
  return hasStarted && !isGameOver && grid[index] === '';
}

function setupGameAgainstComputer() {
  playerO.type = 'computer';
  setMessage('Start game or select player');
}

function handlePlayerOElementClick() {
  if (hasStarted) {
    return;
  }

  playerX.type = 'computer';
  playerO.type = 'human';
  startGame();
}

function handleCellElementClick(index) {
  return () => {
    if (isGameOver) {
      resetGame();
      return;
    }

    if (!hasStarted) {
      startGame();
    }

    if (playerWithTurn.type === 'human') {
      makeMove(index);
    }
  };
}

/*
 * Initialise
 */

versionElement.innerText = version;
modeInputElements.forEach(e => e.addEventListener('change', handleModeChange));
playerOElement.addEventListener('click', handlePlayerOElementClick);
cellElements.forEach((e, i) => e.addEventListener('click', handleCellElementClick(i)));
restartBtnElement.addEventListener('click', resetGame);
resetGame();
