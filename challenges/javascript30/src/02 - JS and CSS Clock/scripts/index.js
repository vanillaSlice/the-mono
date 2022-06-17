/*
 * Elements
 */

const secondHandElement = document.querySelector('.js-second-hand');
const minuteHandElement = document.querySelector('.js-min-hand');
const hourHandElement = document.querySelector('.js-hour-hand');

/*
 * Functions
 */

function setDate(disableTransition = false) {
  const now = new Date();

  const seconds = now.getSeconds();
  const secondsDegrees = ((seconds / 60) * 360) + 90;
  secondHandElement.style.transform = `rotate(${secondsDegrees}deg)`;
  secondHandElement.style.transitionDuration = (disableTransition || seconds === 0) ? '0s' : '0.5s';

  const minutes = now.getMinutes();
  const minutesDegrees = ((minutes / 60) * 360) + ((seconds / 60) * 6) + 90;
  minuteHandElement.style.transform = `rotate(${minutesDegrees}deg)`;
  minuteHandElement.style.transitionDuration = (disableTransition || minutes === 0) ? '0s' : '0.5s';

  const hours = now.getHours();
  const hoursDegrees = ((hours / 12) * 360) + ((minutes / 60) * 30) + 90;
  hourHandElement.style.transform = `rotate(${hoursDegrees}deg)`;
  hourHandElement.style.transitionDuration = (disableTransition || hours === 0 || hours === 12) ? '0s' : '0.5s';
}

/*
 * Initialise
 */

setInterval(setDate, 1000);
setDate(true);
