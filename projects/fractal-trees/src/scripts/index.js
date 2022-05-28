import _ from 'lodash';

/*
 * Elements
 */

const controlsElement = document.querySelector('.js-controls');
const depthElement = controlsElement.querySelector('.js-depth');
const angleElement = controlsElement.querySelector('.js-angle');
const rootLengthElement = controlsElement.querySelector('.js-root-length');
const shorterChildElement = controlsElement.querySelector('.js-shorter-child');
const rootWeightElement = controlsElement.querySelector('.js-root-weight');
const thinnerChildElement = controlsElement.querySelector('.js-thinner-child');
const rootColourElement = controlsElement.querySelector('.js-root-colour');
const multiColouredElement = controlsElement.querySelector('.js-multi-coloured');
const scaleElement = controlsElement.querySelector('.js-scale');
const resetElement = controlsElement.querySelector('.js-reset');
const closeContainerElement = controlsElement.querySelector('.js-close-container');
const closeElement = controlsElement.querySelector('.js-close');

/*
 * Constants
 */

const BACKGROUND_COLOUR = [0, 0, 11.8];
const MIN_DEPTH = 1;
const MAX_DEPTH = 15;
const DEFAULT_DEPTH = 11;
const MIN_ANGLE = 0;
const MAX_ANGLE = 360;
const DEFAULT_ANGLE = 30;
const MIN_ROOT_LENGTH = 1;
const MAX_ROOT_LENGTH = 200;
const DEFAULT_ROOT_LENGTH = 170;
const LENGTH_MULTIPLIER = 0.75; // used to determine child branch length
const DEFAULT_SHORTER_CHILD = true;
const MIN_ROOT_WEIGHT = 1;
const MAX_ROOT_WEIGHT = 30;
const DEFAULT_ROOT_WEIGHT = 1;
const WEIGHT_MULTIPLIER = 0.9; // used to determine child branch weight
const DEFAULT_THINNER_CHILD = true;
const DEFAULT_ROOT_COLOUR = '#ff81e6';
const DEFAULT_MULTI_COLOURED = true;
const HUE_MULTIPLIER = 0.75; // used to determine child branch colour
const MIN_SCALE = 0.1;
const MAX_SCALE = 2;
const DEFAULT_SCALE = 1;

/*
 * Variables.
 */

let controlsOpen = true;

/*
 * Functions
 */

function hexToHsl(h) {
  const hex = h.replace('#', '');

  const red = parseInt(hex.substring(0, 2), 16) / 255;
  const green = parseInt(hex.substring(2, 4), 16) / 255;
  const blue = parseInt(hex.substring(4, 6), 16) / 255;

  const max = Math.max(red, green, blue);
  const min = Math.min(red, green, blue);

  let hue = 0;
  let saturation = 0;
  const lightness = (max + min) / 2;

  if (max !== min) {
    const delta = max - min;

    saturation = (lightness > 0.5) ? delta / (2 - max - min) : delta / (max + min);

    if (max === red) {
      hue = (green - blue) / delta + (green < blue ? 6 : 0);
    } else if (max === green) {
      hue = (blue - red) / delta + 2;
    } else if (max === blue) {
      hue = (red - green) / delta + 4;
    }

    hue /= 6;
  }

  return [hue * 360, saturation * 100, lightness * 100];
}

function branch(options) {
  const {
    maxDepth,
    angle,
    shorterChild,
    thinnerChild,
    saturation,
    lightness,
    multiColoured,
  } = options;
  let {
    currentDepth,
    length,
    weight,
    hue,
  } = options;

  strokeWeight(weight);
  stroke(hue, saturation, lightness);
  line(0, 0, 0, -length);
  translate(0, -length);

  if (currentDepth < maxDepth - 1) {
    currentDepth += 1;

    if (shorterChild) {
      length *= LENGTH_MULTIPLIER;
    }

    if (thinnerChild) {
      weight *= WEIGHT_MULTIPLIER;
    }

    if (multiColoured) {
      hue *= HUE_MULTIPLIER;
    }

    const childBranchOptions = {
      currentDepth,
      maxDepth,
      angle,
      length,
      shorterChild,
      weight,
      thinnerChild,
      hue,
      saturation,
      lightness,
      multiColoured,
    };

    push();
    rotate(angle);
    branch(childBranchOptions);
    pop();

    push();
    rotate(-angle);
    branch(childBranchOptions);
    pop();
  }
}

const drawTree = _.debounce(() => {
  resizeCanvas(window.innerWidth, window.innerHeight);
  background(BACKGROUND_COLOUR);
  translate(width / 2, height);
  scale(parseFloat(scaleElement.value));

  const [hue, saturation, lightness] = hexToHsl(rootColourElement.value);

  branch({
    currentDepth: 0,
    maxDepth: parseInt(depthElement.value, 10),
    angle: parseInt(angleElement.value, 10),
    length: parseInt(rootLengthElement.value, 10),
    shorterChild: shorterChildElement.checked,
    weight: parseInt(rootWeightElement.value, 10),
    thinnerChild: thinnerChildElement.checked,
    hue,
    saturation,
    lightness,
    multiColoured: multiColouredElement.checked,
  });
}, 100);

function updateDepth() {
  if (depthElement.value < MIN_DEPTH) {
    depthElement.value = MIN_DEPTH;
  } else if (depthElement.value > MAX_DEPTH) {
    depthElement.value = MAX_DEPTH;
  } else {
    depthElement.value = Math.round(depthElement.value);
  }
  drawTree();
}

function updateAngle() {
  if (angleElement.value < MIN_ANGLE) {
    angleElement.value = MIN_ANGLE;
  } else if (angleElement.value > MAX_ANGLE) {
    angleElement.value = MAX_ANGLE;
  } else {
    angleElement.value = Math.round(angleElement.value);
  }
  drawTree();
}

function updateRootLength() {
  if (rootLengthElement.value < MIN_ROOT_LENGTH) {
    rootLengthElement.value = MIN_ROOT_LENGTH;
  } else if (rootLengthElement.value > MAX_ROOT_LENGTH) {
    rootLengthElement.value = MAX_ROOT_LENGTH;
  } else {
    rootLengthElement.value = Math.round(rootLengthElement.value);
  }
  drawTree();
}

function updateRootWeight() {
  if (rootWeightElement.value < MIN_ROOT_WEIGHT) {
    rootWeightElement.value = MIN_ROOT_WEIGHT;
  } else if (rootWeightElement.value > MAX_ROOT_WEIGHT) {
    rootWeightElement.value = MAX_ROOT_WEIGHT;
  } else {
    rootWeightElement.value = Math.round(rootWeightElement.value);
  }
  drawTree();
}

function updateRootColour() {
  const validColour = /^#[0-9A-F]{6}$/i.test(rootColourElement.value);
  if (!validColour) {
    rootColourElement.value = DEFAULT_ROOT_COLOUR;
  }
  drawTree();
}

function updateScale() {
  if (scaleElement.value < MIN_SCALE) {
    scaleElement.value = MIN_SCALE;
  } else if (scaleElement.value > MAX_SCALE) {
    scaleElement.value = MAX_SCALE;
  }
  drawTree();
}

function reset() {
  depthElement.value = DEFAULT_DEPTH;
  angleElement.value = DEFAULT_ANGLE;
  rootLengthElement.value = DEFAULT_ROOT_LENGTH;
  shorterChildElement.checked = DEFAULT_SHORTER_CHILD;
  rootWeightElement.value = DEFAULT_ROOT_WEIGHT;
  thinnerChildElement.checked = DEFAULT_THINNER_CHILD;
  rootColourElement.value = DEFAULT_ROOT_COLOUR;
  multiColouredElement.checked = DEFAULT_MULTI_COLOURED;
  scaleElement.value = DEFAULT_SCALE;
  drawTree();
}

function close() {
  if (controlsOpen) {
    controlsElement.style.transform = `translateY(-${closeContainerElement.offsetTop}px)`;
    closeElement.textContent = 'Open Controls';
  } else {
    controlsElement.style.transform = 'translateY(0px)';
    closeElement.textContent = 'Close Controls';
  }
  controlsOpen = !controlsOpen;
}

function setup() {
  createCanvas(window.innerWidth, window.innerHeight);
  colorMode(HSL);
  angleMode(DEGREES);
  drawTree();
}

/*
 * Initialise
 */

window.setup = setup;
window.windowResized = drawTree;
depthElement.addEventListener('change', updateDepth);
angleElement.addEventListener('change', updateAngle);
rootLengthElement.addEventListener('change', updateRootLength);
shorterChildElement.addEventListener('change', drawTree);
rootWeightElement.addEventListener('change', updateRootWeight);
thinnerChildElement.addEventListener('change', drawTree);
rootColourElement.addEventListener('change', updateRootColour);
multiColouredElement.addEventListener('change', drawTree);
scaleElement.addEventListener('change', updateScale);
resetElement.addEventListener('click', reset);
closeElement.addEventListener('click', close);
