/*
  eslint-disable
    no-param-reassign,
    no-console,
*/

/*
 * Elements
 */

const filterElement = document.querySelector('.js-filter');
const takePhotoBtn = document.querySelector('.js-take-photo');
const photoElement = document.querySelector('.js-photo');
const playerElement = document.querySelector('.js-player');
const context = photoElement.getContext('2d');
const stripElement = document.querySelector('.js-strip');
const snapElement = document.querySelector('.js-snap');

/**
 * Variables
 */

let selectedFilter = 'none';

/*
 * Functions
 */

function rgbSplit(pixels) {
  for (let i = 0; i < pixels.data.length; i += 4) {
    pixels.data[i - 350] = pixels.data[i];
    pixels.data[i + 100] = pixels.data[i + 1];
    pixels.data[i - 250] = pixels.data[i + 2];
  }
}

function redEffect(pixels) {
  for (let i = 0; i < pixels.data.length; i += 4) {
    pixels.data[i] += 100;
    pixels.data[i + 1] -= 50;
    pixels.data[i + 2] *= 0.5;
  }
}

function loveless(pixels) {
  context.globalAlpha = 0.02;
  for (let i = 0; i < pixels.data.length; i += 4) {
    pixels.data[i] += 10;
    pixels.data[i + 1] += 2;
    pixels.data[i + 2] += 5;
  }
}

function applyFilter(pixels) {
  context.globalAlpha = 1;
  switch (selectedFilter) {
    case 'rgb-split':
      rgbSplit(pixels);
      break;
    case 'red-effect':
      redEffect(pixels);
      break;
    case 'loveless':
      loveless(pixels);
      break;
    default:
      break;
  }
}

function paintToCanvas() {
  const width = playerElement.videoWidth;
  const height = playerElement.videoHeight;
  photoElement.width = width;
  photoElement.height = height;
  return setInterval(() => {
    context.drawImage(playerElement, 0, 0, width, height);
    const pixels = context.getImageData(0, 0, width, height);
    applyFilter(pixels);
    context.putImageData(pixels, 0, 0);
  }, 16);
}

function getVideo() {
  navigator.mediaDevices.getUserMedia({
    video: true,
    audio: false,
  })
    .then((localMediaStream) => {
      playerElement.srcObject = localMediaStream;
      playerElement.onloadedmetadata = () => {
        playerElement.play();
      };
    })
    .catch((err) => {
      console.error('OH NOOOOO', err);
    });
}

function takePhoto() {
  // play the sound
  snapElement.currentTime = 0;
  snapElement.play();

  // create photo and add download link
  const data = photoElement.toDataURL('image/jpeg');
  const link = document.createElement('a');
  link.classList += 'strip__link';
  link.href = data;
  link.setAttribute('download', 'handsome');
  link.innerHTML = `<img class="strip__img" src=${data} alt="Handsome Man">`;
  stripElement.insertBefore(link, stripElement.firstChild);
}

/*
 * Initialise
 */

playerElement.addEventListener('canplay', paintToCanvas);
filterElement.addEventListener('change', (e) => {
  selectedFilter = e.target.value;
});
takePhotoBtn.addEventListener('click', takePhoto);
getVideo();
