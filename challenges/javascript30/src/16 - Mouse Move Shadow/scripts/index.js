/*
 * Elements
 */

const heroElement = document.querySelector('.js-hero');
const titleElement = heroElement.querySelector('.js-title');

/*
 * Variables
 */

const walk = 100; // 100px

/*
 * Functions
 */

function shadow(e) {
  // get x and y
  let x = e.offsetX;
  let y = e.offsetY;

  if (e.type === 'touchmove') {
    const touches = e.targetTouches[0];
    x = touches.clientX;
    y = touches.clientY;
  } else {
    x = e.offsetX;
    y = e.offsetY;
    if (this !== e.target) {
      x += e.target.offsetLeft;
      y += e.target.offsetTop;
    }
  }

  const width = heroElement.offsetWidth;
  const height = heroElement.offsetHeight;
  const xWalk = Math.round((x / width * walk) - (walk / 2));
  const yWalk = Math.round((y / height * walk) - (walk / 2));

  titleElement.style.textShadow = `${xWalk}px ${yWalk}px 0 rgba(255, 0, 255, 0.7),
    ${xWalk * -1}px ${yWalk}px 0 rgba(0, 255, 255, 0.7),
    ${yWalk}px ${xWalk * -1}px 0 rgba(0, 255, 0, 0.7),
    ${yWalk * -1}px ${xWalk}px 0 rgba(0, 0, 255, 0.7)`;
}

/*
 * Initialise
 */

heroElement.addEventListener('mousemove', shadow);
heroElement.addEventListener('touchmove', shadow);
