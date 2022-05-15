/*
 * DOM Elements
 */

const btnElements = {
  green: document.querySelector('.js-green-btn'),
  red: document.querySelector('.js-red-btn'),
  yellow: document.querySelector('.js-yellow-btn'),
  blue: document.querySelector('.js-blue-btn'),
};
const screenElement = document.querySelector('.js-screen');
const screenTextElement = document.querySelector('.js-screen-text');
const startBtnElement = document.querySelector('.js-start-btn');
const strictBtnElement = document.querySelector('.js-strict-btn');
const strictLightElement = document.querySelector('.js-strict-light');
const powerBtnElement = document.querySelector('.js-power-btn');

/*
 * Constants
 */

const colours = ['green', 'red', 'yellow', 'blue'];
const speedIntervals = {
  1: 1000,
  5: 850,
  9: 700,
  13: 550,
};
const errorTimeouts = {
  1: 5000,
  5: 4000,
  9: 3000,
  13: 2000,
};
const screenFlashTimeout = 300;
const screenFlashToggles = {
  newGame: 4,
  error: 6,
  win: 8,
};
const winningScore = 20;

/*
 * State
 */

let isOn = false;
let strictMode = false;
let buttonsLocked = true;
let sequence;
let playerIndex;
let previousColour;
let speedInterval;
let errorTimeout;
let intervalIds = [];
let timeoutIds = [];

/*
 * Sounds
 */

let sounds;

function initialiseSounds() {
  if (sounds) {
    return;
  }

  const audioContext = new (window.AudioContext || window.webkitAudioContext)();

  sounds = {
    green: new Sound({
      audioContext,
      frequency: 164.81,
    }),
    red: new Sound({
      audioContext,
      frequency: 220.00,
    }),
    yellow: new Sound({
      audioContext,
      frequency: 277.18,
    }),
    blue: new Sound({
      audioContext,
      frequency: 329.63,
    }),
    error: new Sound({
      audioContext,
      frequency: 110.00,
      wave: 'triangle',
    }),
    win: new Sound({
      audioContext,
      frequency: 440.00,
    }),
  };
}

class Sound {
  constructor({ audioContext, frequency, wave = 'sine' }) {
    this.audioContext = audioContext;

    const oscillator = this.audioContext.createOscillator();
    oscillator.frequency.setValueAtTime(frequency, 0);
    oscillator.type = wave;
    oscillator.start();

    const gain = this.audioContext.createGain();
    gain.gain.setValueAtTime(0, 0);
    oscillator.connect(gain);
    gain.connect(this.audioContext.destination);

    this.gain = gain.gain;

    this.play = this.play.bind(this);
    this.stop = this.stop.bind(this);
  }

  play() {
    this.gain.setTargetAtTime(1, this.audioContext.currentTime, 0.01);
  }

  stop() {
    this.gain.setTargetAtTime(0, this.audioContext.currentTime, 0.01);
  }
}

/*
 * Functions.
 */

function togglePower() {
  if (powerBtnElement.checked) {
    turnPowerOn();
  } else {
    turnPowerOff();
  }
}

function turnPowerOn() {
  isOn = true;
  initialiseSounds();
  toggleScreenLight(true);
}

function toggleScreenLight(on) {
  screenElement.classList.toggle('lit', on);
}

function turnPowerOff() {
  isOn = false;
  clearTimedEvents();
  setScreenText('--');
  toggleScreenLight(false);
  turnOffStrictMode();
  lockButtons();
  stopSounds();
}

function clearTimedEvents() {
  clearIntervals();
  clearTimeouts();
}

function clearIntervals() {
  intervalIds.forEach(id => clearInterval(id));
  intervalIds = [];
}

function clearTimeouts() {
  timeoutIds.forEach(id => clearTimeout(id));
  timeoutIds = [];
}

function setScreenText(text) {
  screenTextElement.innerText = text;
}

function turnOffStrictMode() {
  strictMode = false;
  strictLightElement.classList.remove('lit');
}

function lockButtons() {
  buttonsLocked = true;
  Object.values(btnElements).forEach(btnElement => btnElement.classList.remove('clickable', 'lit'));
}

function stopSounds() {
  Object.values(sounds).forEach(sound => sound.stop());
}

function startNewGame() {
  if (!isOn) {
    return;
  }

  const toggles = screenFlashToggles.newGame;
  const timeout = (toggles + 1) * screenFlashTimeout;
  clearTimedEvents();
  setScreenText('--');
  toggleScreenLight(true);
  lockButtons();
  stopSounds();
  makeScreenFlash(toggles);
  initNewSequence();
  playSequence(timeout);
}

function makeScreenFlash(toggles) {
  timeoutIds.push(setTimeout(() => {
    if (toggles > 0) {
      toggleScreenLight();
      makeScreenFlash(toggles - 1);
    }
  }, screenFlashTimeout));
}

function initNewSequence() {
  sequence = [getRandomColour()];
}

function getRandomColour() {
  return colours[Math.floor(Math.random() * colours.length)];
}

function playSequence(timeout) {
  let index = 0;
  playerIndex = 0;
  speedInterval = speedIntervals[sequence.length] || speedInterval;
  errorTimeout = errorTimeouts[sequence.length] || errorTimeout;
  timeoutIds.push(setTimeout(() => {
    setScreenText(getCountString());
    intervalIds.push(setInterval(() => {
      if (index < sequence.length) {
        playAndStopSequenceElement(index);
        index += 1;
      } else {
        onPlaySequenceComplete();
      }
    }, speedInterval));
  }, timeout));
}

function getCountString() {
  return sequence.length < 10 ? `0${sequence.length}` : sequence.length;
}

function playAndStopSequenceElement(index) {
  const colour = sequence[index];
  const btnElement = btnElements[colour];
  const sound = sounds[colour];
  const timeout = speedInterval / 2;
  playSequenceElement(btnElement, sound);
  stopSequenceElement(btnElement, sound, timeout);
}

function playSequenceElement(btnElement, sound) {
  btnElement.classList.add('lit');
  sound.play();
}

function stopSequenceElement(btnElement, sound, timeout) {
  timeoutIds.push(setTimeout(() => {
    btnElement.classList.remove('lit');
    sound.stop();
  }, timeout));
}

function onPlaySequenceComplete() {
  clearTimedEvents();
  unlockButtons();
  startErrorTimeout();
}

function unlockButtons() {
  buttonsLocked = false;
  Object.values(btnElements).forEach(btnElement => btnElement.classList.add('clickable'));
}

function startErrorTimeout() {
  timeoutIds.push(setTimeout(flagError, errorTimeout));
}

function flagError() {
  const toggles = screenFlashToggles.error;
  const timeout = screenFlashTimeout * (toggles + 1);
  clearTimedEvents();
  lockButtons();
  stopSounds();
  playAndStopSound(sounds.error, timeout);
  setScreenText('!!');
  makeScreenFlash(toggles);
  continueAfterError(timeout);
}

function playAndStopSound(sound, timeout) {
  sound.play();
  timeoutIds.push(setTimeout(sound.stop, timeout));
}

function continueAfterError(timeout) {
  timeoutIds.push(setTimeout(strictMode ? startNewGame : playSequence, timeout));
}

function toggleStrictMode() {
  if (!isOn) {
    return;
  }

  strictMode = !strictMode;
  strictLightElement.classList.toggle('lit', strictMode);
}

function buttonPressed(e) {
  e.preventDefault();
  e.stopPropagation();

  if (buttonsLocked || !isOn) {
    previousColour = false;
    return;
  }

  const btnElement = e.target;
  const { colour } = btnElement.dataset;

  btnElement.classList.add('lit');
  sounds[colour].play();

  if (colour !== sequence[playerIndex]) {
    flagError();
    return;
  }

  previousColour = colour;
  clearTimedEvents();
}

function buttonReleased(e) {
  e.preventDefault();
  e.stopPropagation();

  if (buttonsLocked || !isOn) {
    return;
  }

  turnAllButtonsOff();
  stopSounds();

  if (!previousColour) {
    return;
  }

  playerIndex += 1;

  if (playerIndex === winningScore) {
    flagWin();
  } else if (playerIndex === sequence.length) {
    continueSequence();
  }

  previousColour = false;
}

function turnAllButtonsOff() {
  Object.values(btnElements).forEach(btnElement => btnElement.classList.remove('lit'));
}

function flagWin() {
  const timeout = screenFlashTimeout * (screenFlashToggles.win + 1);
  lockButtons();
  playAndStopSound(sounds.win, timeout);
  setScreenText('**');
  makeScreenFlash(screenFlashToggles.win);
  continueAfterWin(timeout);
}

function continueAfterWin(timeout) {
  timeoutIds.push(setTimeout(startNewGame, timeout));
}

function continueSequence() {
  playerIndex = 0;
  sequence.push(getRandomColour());
  lockButtons();
  playSequence();
}

/*
 * Initialse
 */

powerBtnElement.addEventListener('change', togglePower);
startBtnElement.addEventListener('click', startNewGame);
strictBtnElement.addEventListener('click', toggleStrictMode);

Object.values(btnElements).forEach((btnElement) => {
  btnElement.addEventListener('mousedown', buttonPressed);
  btnElement.addEventListener('mouseup', buttonReleased);
  btnElement.addEventListener('mouseleave', buttonReleased);
  btnElement.addEventListener('dragleave', buttonReleased);
  btnElement.addEventListener('touchstart', buttonPressed);
  btnElement.addEventListener('touchend', buttonReleased);
});
