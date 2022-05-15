import { version } from '../../package.json';

/*
 * DOM Elements
 */

const bodyElement = document.querySelector('.js-body');
const breakLengthElement = document.querySelector('.js-break-length');
const decreaseBreakBtnElement = document.querySelector('.js-decrease-break-btn');
const increaseBreakBtnElement = document.querySelector('.js-increase-break-btn');
const sessionLengthElement = document.querySelector('.js-session-length');
const decreaseSessionBtnElement = document.querySelector('.js-decrease-session-btn');
const increaseSessionBtnElement = document.querySelector('.js-increase-session-btn');
const fillElement = document.querySelector('.js-fill');
const modeElement = document.querySelector('.js-mode');
const remainingTimeElement = document.querySelector('.js-remaining-time');
const startBtnElement = document.querySelector('.js-start-btn');
const stopBtnElement = document.querySelector('.js-stop-btn');
const clearBtnElement = document.querySelector('.js-clear-btn');
const versionElement = document.querySelector('.js-version');

/*
 * Variables
 */

let breakLengthInMinutes = 5;
let sessionLengthInMinutes = 25;
let startTime;
let mode = 'Session';
let timerLength = 1500000;
let originalTimerLength = 1500000;
let remainingTime = 1500000;
let isStopped = true;
let timerIntervalId;

/*
 * Functions
 */

function minutesToMilliseconds(minutes) {
  return minutes * 60000;
}

function formatTime(time) {
  const minutes = parseInt((time / 1000) / 60, 10);
  let seconds = parseInt((time / 1000) % 60, 10);
  if (seconds < 10) {
    seconds = `0${seconds}`;
  }
  return `${minutes}:${seconds}`;
}

function updateBreakLength(length) {
  if (length < 1) {
    return;
  }

  breakLengthInMinutes = length;
  breakLengthElement.innerText = breakLengthInMinutes;

  if (mode === 'Break') {
    startTime = Date.now();
    timerLength = minutesToMilliseconds(breakLengthInMinutes);
    originalTimerLength = timerLength;
    remainingTime = timerLength;
    remainingTimeElement.innerText = formatTime(remainingTime);
    fillElement.style.height = '0%';
  }
}

function updateSessionLength(length) {
  if (length < 1) {
    return;
  }

  sessionLengthInMinutes = length;
  sessionLengthElement.innerText = sessionLengthInMinutes;

  if (mode === 'Session') {
    startTime = Date.now();
    timerLength = minutesToMilliseconds(sessionLengthInMinutes);
    originalTimerLength = timerLength;
    remainingTime = timerLength;
    remainingTimeElement.innerText = formatTime(remainingTime);
    fillElement.style.height = '0%';
  }
}

function switchMode() {
  if (mode === 'Session') {
    mode = 'Break';
    timerLength = minutesToMilliseconds(breakLengthInMinutes);
  } else {
    mode = 'Session';
    timerLength = minutesToMilliseconds(sessionLengthInMinutes);
  }
  startTime = Date.now();
  originalTimerLength = timerLength;
  remainingTime = timerLength;
  bodyElement.setAttribute('data-mode', mode);
  modeElement.innerText = mode;
}

function updateRemainingTime() {
  const elapsedTime = Date.now() - startTime;
  remainingTime = timerLength - elapsedTime;
  remainingTimeElement.innerText = formatTime(remainingTime);
}

function updateFillElement() {
  const complete = parseInt((originalTimerLength - remainingTime) / originalTimerLength * 100, 10);
  fillElement.style.height = `${complete}%`;
}

function updateTimer() {
  if (remainingTime <= 0) {
    switchMode();
  }
  updateRemainingTime();
  updateFillElement();
}

function handleStartBtnClick() {
  if (!isStopped) {
    return;
  }

  isStopped = false;
  startTime = Date.now();
  bodyElement.setAttribute('data-mode', mode);
  modeElement.innerText = mode;
  timerIntervalId = setInterval(updateTimer, 200);
}

function handleStopBtnClick() {
  if (isStopped) {
    return;
  }

  clearInterval(timerIntervalId);
  isStopped = true;
  timerLength -= Date.now() - startTime;
}

function handleClearBtnClick() {
  clearInterval(timerIntervalId);
  isStopped = true;
  mode = 'Session';
  timerLength = minutesToMilliseconds(sessionLengthInMinutes);
  originalTimerLength = timerLength;
  remainingTime = timerLength;
  remainingTimeElement.innerText = formatTime(remainingTime);
  bodyElement.removeAttribute('data-mode');
  fillElement.style.height = '0%';
  modeElement.innerText = 'Pomodoro';
}

/*
 * Initialise
 */

breakLengthElement.innerText = breakLengthInMinutes;
decreaseBreakBtnElement.addEventListener('click', () => updateBreakLength(breakLengthInMinutes - 1));
increaseBreakBtnElement.addEventListener('click', () => updateBreakLength(breakLengthInMinutes + 1));
sessionLengthElement.innerText = sessionLengthInMinutes;
decreaseSessionBtnElement.addEventListener('click', () => updateSessionLength(sessionLengthInMinutes - 1));
increaseSessionBtnElement.addEventListener('click', () => updateSessionLength(sessionLengthInMinutes + 1));
modeElement.innerText = 'Pomodoro';
remainingTimeElement.innerText = formatTime(timerLength);
startBtnElement.addEventListener('click', handleStartBtnClick);
stopBtnElement.addEventListener('click', handleStopBtnClick);
clearBtnElement.addEventListener('click', handleClearBtnClick);
versionElement.innerText = version;
