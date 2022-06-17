/*
 * Elements
 */

const scoreElement = document.querySelector('.js-score');
const startBtn = document.querySelector('.js-start-btn');
const holeElements = document.querySelectorAll('.js-hole');
const moleElements = document.querySelectorAll('.js-mole');

/*
 * Variables
 */

let lastHole;
let timeouts = [];
let timeUp = false;
let score = 0;

/*
 * Functions
 */

function randomTime(min, max) {
  return Math.round(Math.random() * (max - min) + min);
}

function randomHole(holes) {
  const index = Math.floor(Math.random() * holes.length);
  const hole = holes[index];
  if (hole === lastHole) {
    return randomHole(holes);
  }
  lastHole = hole;
  return hole;
}

function peep() {
  const time = randomTime(200, 1000);
  const hole = randomHole(holeElements);
  hole.classList.add('up');
  timeouts.push(setTimeout(() => {
    hole.classList.remove('up');
    if (!timeUp) {
      peep();
    }
  }, time));
}

function startGame() {
  timeouts.forEach(timeout => clearTimeout(timeout));
  timeouts = [];
  holeElements.forEach(hole => hole.classList.remove('up'));
  scoreElement.textContent = 0;
  timeUp = false;
  score = 0;
  peep();
  timeouts.push(setTimeout(() => {
    timeUp = true;
  }, 10000));
}

function bonk(e) {
  // fake click
  if (!e.isTrusted) {
    return;
  }
  score += 1;
  this.classList.remove('up');
  scoreElement.textContent = score;
}

/*
 * Initialise
 */

startBtn.addEventListener('click', startGame);
moleElements.forEach(mole => mole.addEventListener('click', bonk));
