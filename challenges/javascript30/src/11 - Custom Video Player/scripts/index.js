/*
 * Elements
 */

const playerElement = document.querySelector('.js-player');
const videoElement = playerElement.querySelector('.js-video');
const progressElement = playerElement.querySelector('.js-progress');
const progressBarElement = playerElement.querySelector('.js-progress-bar');
const toggleElement = playerElement.querySelector('.js-toggle');
const sliderElements = playerElement.querySelectorAll('.js-slider');
const skipBtnElements = playerElement.querySelectorAll('.js-skip');

/*
 * Variables
 */

let mouseDown = false;

/*
 * Functions
 */

function togglePlay() {
  if (videoElement.paused) {
    videoElement.play();
  } else {
    videoElement.pause();
  }
}

function updateButton() {
  const icon = this.paused ? '►' : '❚ ❚';
  toggleElement.textContent = icon;
}

function skip() {
  videoElement.currentTime += parseFloat(this.dataset.skip);
}

function handleRangeUpdate() {
  videoElement[this.name] = this.value;
}

function handleProgress() {
  const percent = (videoElement.currentTime / videoElement.duration) * 100;
  progressBarElement.style.flexBasis = `${percent}%`;
}

function scrub(e) {
  const scrubTime = (e.offsetX / progressElement.offsetWidth) * videoElement.duration;
  videoElement.currentTime = scrubTime;
}

/*
 * Event listeners
 */

videoElement.addEventListener('click', togglePlay);
videoElement.addEventListener('play', updateButton);
videoElement.addEventListener('pause', updateButton);
videoElement.addEventListener('timeupdate', handleProgress);
toggleElement.addEventListener('click', togglePlay);
skipBtnElements.forEach(button => button.addEventListener('click', skip));
sliderElements.forEach(range => range.addEventListener('change', handleRangeUpdate));
sliderElements.forEach(range => range.addEventListener('mousemove', handleRangeUpdate));
progressElement.addEventListener('click', scrub);
progressElement.addEventListener('mousemove', e => mouseDown && scrub(e));
progressElement.addEventListener('mousedown', () => {
  mouseDown = true;
});
progressElement.addEventListener('mouseup', () => {
  mouseDown = false;
});
