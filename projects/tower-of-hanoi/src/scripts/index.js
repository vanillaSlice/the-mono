import { version } from '../../package.json';

import '../styles/style.css';

const INITIAL_DISKS = 4;
const MIN_DISKS = 1;
const MAX_DISKS = 10;
const SOLVE_TIMEOUT = 200;
const ODD_SOLVE_SEQUENCE = [[0, 2], [0, 1], [1, 2]];
const EVEN_SOLVE_SEQUENCE = [[0, 1], [0, 2], [1, 2]];

class App {
  constructor() {
    this.bindFunctions();
    this.initState();
    this.initElements();
  }

  bindFunctions() {
    this.handleRestartBtnClick = this.handleRestartBtnClick.bind(this);
    this.handleSolveBtnClick = this.handleSolveBtnClick.bind(this);
    this.solve = this.solve.bind(this);
  }

  initState() {
    this.state = {
      disks: INITIAL_DISKS,
      currentMoves: 0,
      towers: [this.range(INITIAL_DISKS), [], []],
      isBeingSolved: false,
      solveTimeoutId: null,
      selectedDisk: null,
      selectedTower: null,
      minMoves: this.calculateMinMoves(INITIAL_DISKS),
    };
  }

  range(n) {
    return [...Array(n).keys()];
  }

  calculateMinMoves(disks) {
    return (2 ** disks) - 1;
  }

  initElements() {
    this.getElements();
    this.resetElements();
    this.addEventListeners();
    this.setVersion();
  }

  getElements() {
    this.gameContainer = document.querySelector('.js-game-container');
    this.disksLabel = document.querySelector('.js-disks-label');
    this.increaseDisksBtn = document.querySelector('.js-increase-disks-btn');
    this.decreaseDisksBtn = document.querySelector('.js-decrease-disks-btn');
    this.currentMovesLabel = document.querySelector('.js-current-moves-label');
    this.restartBtn = document.querySelector('.js-restart-btn');
    this.solveBtn = document.querySelector('.js-solve-btn');
    this.winMessageElement = document.querySelector('.js-win-message');
    this.diskElements = [...document.querySelectorAll('.js-disk')];
    this.towerElements = [...document.querySelectorAll('.js-tower')];
    this.minMovesLabel = document.querySelector('.js-min-moves-label');
    this.versionElement = document.querySelector('.js-version');
  }

  resetElements() {
    this.disksLabel.textContent = this.state.disks;
    this.currentMovesLabel.textContent = this.state.currentMoves;
    this.winMessageElement.classList.add('hidden');
    this.diskElements.forEach((diskElement, index) => {
      diskElement.classList.remove('selected');
      diskElement.classList.toggle('hidden', index >= this.state.disks);
      this.towerElements[0].appendChild(diskElement);
    });
    this.minMovesLabel.textContent = this.state.minMoves;
  }

  addEventListeners() {
    this.increaseDisksBtn.addEventListener('click', () => this.handleUpdateDisksBtnClick(this.state.disks + 1));
    this.decreaseDisksBtn.addEventListener('click', () => this.handleUpdateDisksBtnClick(this.state.disks - 1));
    this.restartBtn.addEventListener('click', this.handleRestartBtnClick);
    this.solveBtn.addEventListener('click', this.handleSolveBtnClick);
    this.towerElements.forEach((towerElement, index) => towerElement.addEventListener('click', () => this.handleTowerElementClick(index)));
  }

  handleUpdateDisksBtnClick(disks) {
    if (disks < MIN_DISKS || disks > MAX_DISKS) {
      return;
    }
    this.state.disks = disks;
    this.resetState();
    this.resetElements();
  }

  resetState() {
    this.state.currentMoves = 0;
    this.state.towers = [this.range(this.state.disks), [], []];
    this.state.isBeingSolved = false;
    clearTimeout(this.state.solveTimeoutId);
    this.state.solveTimeoutId = null;
    this.state.selectedDisk = null;
    this.state.selectedTower = null;
    this.state.minMoves = this.calculateMinMoves(this.state.disks);
  }

  handleRestartBtnClick() {
    this.resetState();
    this.resetElements();
  }

  handleSolveBtnClick() {
    this.resetState();
    this.resetElements();
    const sequence = this.state.disks % 2 === 0 ? EVEN_SOLVE_SEQUENCE : ODD_SOLVE_SEQUENCE;
    this.state.isBeingSolved = true;
    this.state.solveTimeoutId = setTimeout(this.solve, SOLVE_TIMEOUT, sequence);
  }

  solve(sequence, index = 0) {
    const [tower1Index, tower2Index] = sequence[index];
    const disk1Index = this.state.towers[tower1Index][0];
    const disk2Index = this.state.towers[tower2Index][0];

    if (disk2Index === undefined || disk1Index < disk2Index) {
      const diskElement = this.diskElements[disk1Index];
      this.state.towers[tower1Index].shift();
      this.state.towers[tower2Index].unshift(disk1Index);
      this.towerElements[tower2Index].prepend(diskElement);
    } else {
      const diskElement = this.diskElements[disk2Index];
      this.state.towers[tower2Index].shift();
      this.state.towers[tower1Index].unshift(disk2Index);
      this.towerElements[tower1Index].prepend(diskElement);
    }

    this.state.currentMoves += 1;
    this.currentMovesLabel.textContent = this.state.currentMoves;

    if (this.isSolved()) {
      this.state.isBeingSolved = false;
    } else {
      const nextIndex = (index + 1) % sequence.length;
      this.state.solveTimeoutId = setTimeout(this.solve, SOLVE_TIMEOUT, sequence, nextIndex);
    }
  }

  isSolved() {
    const lastTower = this.state.towers[2];
    for (let i = 0; i < this.state.disks; i += 1) {
      if (lastTower[i] !== i) {
        return false;
      }
    }
    return true;
  }

  handleTowerElementClick(towerIndex) {
    if (this.state.isBeingSolved) {
      return;
    }

    if (this.state.selectedDisk !== null) {
      if (this.state.towers[towerIndex][0] < this.state.selectedDisk) {
        return;
      }

      if (this.state.selectedTower !== towerIndex) {
        const diskElement = this.diskElements[this.state.selectedDisk];
        this.towerElements[towerIndex].prepend(diskElement);
        this.state.towers[this.state.selectedTower].shift();
        this.state.towers[towerIndex].unshift(this.state.selectedDisk);
        this.state.currentMoves += 1;
        this.currentMovesLabel.textContent = this.state.currentMoves;
      }

      this.diskElements[this.state.selectedDisk].classList.remove('selected');
      this.winMessageElement.classList.toggle('hidden', !this.isSolved());
      this.state.selectedDisk = null;
      this.state.selectedTower = null;
    } else if (this.state.towers[towerIndex][0] !== undefined) {
      this.state.selectedTower = towerIndex;
      [this.state.selectedDisk] = this.state.towers[towerIndex];
      this.diskElements[this.state.selectedDisk].classList.add('selected');
    }
  }

  setVersion() {
    this.versionElement.textContent = `v${version}`;
  }

  show() {
    this.gameContainer.classList.remove('hidden');
  }
}

new App().show();
