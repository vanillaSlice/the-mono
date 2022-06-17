/* eslint-disable no-param-reassign */

/*
 * Elements
 */

const checkboxElements = document.querySelectorAll('.js-checkbox');

/*
 * Variables
 */

let lastChecked;

/*
 * Functions
 */

function handleCheck(event) {
  let inBetween = false;
  if (event.shiftKey && this.checked) {
    checkboxElements.forEach((checkbox) => {
      if (checkbox === this || checkbox === lastChecked) {
        inBetween = !inBetween;
      }
      if (inBetween) {
        checkbox.checked = true;
      }
    });
  }
  lastChecked = this;
}

/*
 * Initialise
 */

checkboxElements.forEach(checkbox => checkbox.addEventListener('click', handleCheck));
