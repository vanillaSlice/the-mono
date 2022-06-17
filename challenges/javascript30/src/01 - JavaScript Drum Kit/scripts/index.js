/* eslint-disable no-param-reassign */

/*
 * Elements
 */

const modalElement = document.querySelector('.js-modal');
const modalOkBtn = document.querySelector('.js-modal-ok');
const soundElements = document.querySelectorAll('.js-audio');
const keyElements = document.querySelectorAll('.js-key');

/*
 * Variables
 */

let soundsEnabled = false;

/*
 * Functions
 */

function enableSounds() {
  if (soundsEnabled) {
    return;
  }

  soundElements.forEach((sound) => {
    sound.volume = 0;
    sound.play().then(() => {
      sound.pause();
      sound.currentTime = 0;
      sound.volume = 1;
      soundsEnabled = true;
    });
  });
}

function handleOkClick() {
  enableSounds();
  modalElement.remove();
}

function playSound(keyCode) {
  if (!soundsEnabled) {
    return;
  }

  const sound = document.querySelector(`.js-audio[data-key="${keyCode}"]`);
  const key = document.querySelector(`.js-key[data-key="${keyCode}"]`);

  if (!sound || !key) {
    return;
  }

  sound.currentTime = 0;
  sound.play();
  key.classList.add('key--playing');
}

function handleKeyDown(e) {
  playSound(e.keyCode);
}

function handleKeyPress(e) {
  e.preventDefault();
  e.stopPropagation();
  playSound(this.dataset.key);
}

function handleTransitionEnd(e) {
  if (e.propertyName !== 'background-color') {
    return;
  }

  this.classList.remove('key--playing');
}

function handleSoundEnd() {
  const key = document.querySelector(`.js-key[data-key="${this.dataset.key}"]`);
  key.classList.remove('key--playing');
}

/*
 * Initialise
 */

modalOkBtn.addEventListener('click', handleOkClick);
window.addEventListener('keydown', handleKeyDown);
keyElements.forEach(key => key.addEventListener('mousedown', handleKeyPress, true));
keyElements.forEach(key => key.addEventListener('touchstart', handleKeyPress, true));
keyElements.forEach(key => key.addEventListener('transitionend', handleTransitionEnd));
soundElements.forEach(sound => sound.addEventListener('ended', handleSoundEnd));
