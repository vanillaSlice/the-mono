/*
 * Elements
 */

const sliderElement = document.querySelector('.js-slider');

/*
 * Variables
 */

let isDown = false;
let startX;
let scrollLeft;

/*
 * Functions and event listeners
 */

sliderElement.addEventListener('mousedown', (e) => {
  isDown = true;
  sliderElement.classList.add('active');
  startX = e.pageX - sliderElement.offsetLeft;
  ({ scrollLeft } = sliderElement);
});

sliderElement.addEventListener('mouseleave', () => {
  isDown = false;
  sliderElement.classList.remove('active');
});

sliderElement.addEventListener('mouseup', () => {
  isDown = false;
  sliderElement.classList.remove('active');
});

sliderElement.addEventListener('mousemove', (e) => {
  if (!isDown) {
    return;
  }

  e.preventDefault();
  const x = e.pageX - sliderElement.offsetLeft;
  const walk = (x - startX) * 3;
  sliderElement.scrollLeft = scrollLeft - walk;
});
