/*
 * Elements
 */

const wordsElement = document.querySelector('.words');
let paragraphElement = document.createElement('p');
wordsElement.appendChild(paragraphElement);

/*
 * Variables
 */

const SpeechRecognition = window.SpeechRecognition || window.webkitSpeechRecognition;
const recognition = new SpeechRecognition();
recognition.interimResults = true;

/*
 * Functions
 */

function handleResult(e) {
  const transcript = Array.from(e.results)
    .map(result => result[0])
    .map(result => result.transcript)
    .join('');
  paragraphElement.textContent = transcript;
  if (e.results[0].isFinal) {
    paragraphElement = document.createElement('p');
    wordsElement.appendChild(paragraphElement);
  }
}

/*
 * Initialise
 */

recognition.addEventListener('result', handleResult);
recognition.addEventListener('end', recognition.start);
recognition.start();
