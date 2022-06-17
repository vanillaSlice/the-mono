/* eslint-disable no-console */

const timeNodes = Array.from(document.querySelectorAll('[data-time]'));

const seconds = timeNodes
  .map(node => node.dataset.time)
  .map(time => time.split(':'))
  .map(split => parseFloat(split[0]) * 60 + parseFloat(split[1]))
  .reduce((total, secs) => total + secs, 0);

let secondsLeft = seconds;

const hours = Math.floor(secondsLeft / 3600);
secondsLeft %= 3600;

const minutes = Math.floor(secondsLeft / 60);
secondsLeft %= 60;

console.log(`Total = ${hours}:${minutes}:${secondsLeft}`);
