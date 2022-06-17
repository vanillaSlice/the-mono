/*
 * Elements
 */

const instructionsElement = document.querySelector('.js-instructions');
const drawElement = document.querySelector('.js-draw');
const context = drawElement.getContext('2d');

/*
 * Variables
 */

let isDrawing = false;
let lastX = 0;
let lastY = 0;
let hue = 0;
let growing = false;

/*
 * Functions
 */

function resize() {
  const oldImageData = context.getImageData(0, 0, drawElement.width, drawElement.height);
  drawElement.width = window.innerWidth;
  drawElement.height = window.innerHeight;
  context.lineJoin = 'round';
  context.lineCap = 'round';
  context.lineWidth = 100;
  context.putImageData(oldImageData, 0, 0);
}

function handlePress(e) {
  e.preventDefault();
  e.stopPropagation();
  isDrawing = true;
  if (e.type === 'touchstart') {
    const target = e.targetTouches[0];
    [lastX, lastY] = [target.clientX, target.clientY];
  } else {
    [lastX, lastY] = [e.offsetX, e.offsetY];
  }
}

function draw(e) {
  // stop if not drawing
  if (!isDrawing) {
    return;
  }

  instructionsElement.remove(); // make sure p is no longer displaying

  // get x and y
  let x;
  let y;
  if (e.type === 'touchmove') {
    const target = e.targetTouches[0];
    [x, y] = [target.clientX, target.clientY];
  } else {
    [x, y] = [e.offsetX, e.offsetY];
  }

  // draw the line
  context.strokeStyle = `hsl(${hue}, 100%, 50%)`;
  context.beginPath();
  context.moveTo(lastX, lastY);
  context.lineTo(x, y);
  context.stroke();
  [lastX, lastY] = [x, y];

  // update colour
  hue += 1;
  if (hue >= 360) {
    hue = 0;
  }

  // update line width
  growing = (growing && context.lineWidth < 100) || (!growing && context.lineWidth <= 1);
  if (growing) {
    context.lineWidth += 1;
  } else {
    context.lineWidth -= 1;
  }
}

function stopDrawing() {
  isDrawing = false;
}

/*
 * Initialise
 */

window.addEventListener('resize', resize);
drawElement.addEventListener('mousedown', handlePress);
drawElement.addEventListener('touchstart', handlePress);
drawElement.addEventListener('mousemove', draw);
drawElement.addEventListener('touchmove', draw);
drawElement.addEventListener('mouseup', stopDrawing);
drawElement.addEventListener('mouseout', stopDrawing);
drawElement.addEventListener('touchend', stopDrawing);
drawElement.addEventListener('touchcancel', stopDrawing);
resize();
