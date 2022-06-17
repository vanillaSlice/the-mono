/*
  eslint-disable
    no-console,
    no-unused-vars,
*/

const divs = document.querySelectorAll('div');

function logText(e) {
  // e.stopPropagation();
  console.log(this.classList.value);
}

divs.forEach(div => div.addEventListener('click', logText, {
  capture: false,
  once: true,
}));
