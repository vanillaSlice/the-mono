/*
 * Elements
 */

const timerBtns = document.querySelectorAll('.js-timer-button');
const timerFormElement = document.querySelector('.js-timer-form');
const timeLeftElement = document.querySelector('.js-time-left');
const endTimeElement = document.querySelector('.js-end-time');

/*
 * Variables
 */

let countdown;

/*
 * Functions
 */

function displayTimeLeft(seconds) {
  const minutes = Math.floor(seconds / 60);
  const remainderSeconds = seconds % 60;
  const displayTime = `${minutes}:${(remainderSeconds < 10 ? '0' : '')}${remainderSeconds}`;
  document.title = displayTime;
  timeLeftElement.textContent = displayTime;
}

function displayEndTime(timestamp) {
  const end = new Date(timestamp);
  const hours = end.getHours();
  const minutes = end.getMinutes();
  endTimeElement.textContent = `Be back at ${(hours > 12 ? hours - 12 : hours)}:${(minutes < 10 ? '0' : '')}${minutes}`;
}

function timer(seconds) {
  clearInterval(countdown);
  const now = Date.now();
  const then = now + (seconds * 1000);
  displayTimeLeft(seconds);
  displayEndTime(then);
  countdown = setInterval(() => {
    const secondsLeft = Math.round((then - Date.now()) / 1000);
    if (secondsLeft < 0) {
      clearInterval(countdown);
      return;
    }
    displayTimeLeft(secondsLeft);
  }, 1000);
}

function startTimer() {
  const seconds = parseInt(this.dataset.time, 10);
  timer(seconds);
}

/*
 * Initialise
 */

timerBtns.forEach(button => button.addEventListener('click', startTimer));
timerFormElement.addEventListener('submit', (e) => {
  e.preventDefault();
  const minutes = e.target.minutes.value;
  timer(minutes * 60);
  e.target.reset();
});
