/*
 * Elements
 */

const voicesDropdownElement = document.querySelector('.js-voices');
const rateElement = document.querySelector('.js-rate');
const pitchElement = document.querySelector('.js-pitch');
const textElement = document.querySelector('.js-text');
const stopBtn = document.querySelector('.js-stop');
const speakBtn = document.querySelector('.js-speak');

/*
 * Variables
 */

let utterance;
let voices = [];

/*
 * Functions
 */

function populateVoices() {
  voices = speechSynthesis.getVoices();
  const voiceOptions = voices
    .filter(voice => voice.lang.includes('en'))
    .map(voice => `<option value="${voice.name}">${voice.name} (${voice.lang})</option>`)
    .join('');
  voicesDropdownElement.innerHTML = voiceOptions;
}

function toggle(startOver = true) {
  speechSynthesis.cancel();
  if (startOver) {
    utterance = new SpeechSynthesisUtterance();
    utterance.voice = voices.find(voice => voice.name === voicesDropdownElement.value);
    utterance.rate = rateElement.value;
    utterance.pitch = pitchElement.value;
    utterance.text = textElement.value;
    speechSynthesis.speak(utterance);
  }
}

/*
 * Initialise
 */

populateVoices();
if (speechSynthesis.onvoiceschanged !== undefined) {
  speechSynthesis.addEventListener('voiceschanged', populateVoices);
}
voicesDropdownElement.addEventListener('change', toggle);
rateElement.addEventListener('change', toggle);
pitchElement.addEventListener('change', toggle);
textElement.addEventListener('change', toggle);
speakBtn.addEventListener('click', toggle);
stopBtn.addEventListener('click', () => toggle(false));
