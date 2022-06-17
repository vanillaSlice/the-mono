/*
 * Elements
 */

const panelElements = document.querySelectorAll('.js-panel');

/*
 * Functions
 */

function toggleOpen() {
  this.classList.toggle('panel--open');
}

function toggleActive(event) {
  if (event.propertyName === 'flex-grow' || event.propertyName === 'flex') {
    this.classList.toggle('panel--open-active', this.classList.contains('panel--open'));
  }
}

/*
 * Initialise
 */

panelElements.forEach(panel => panel.addEventListener('click', toggleOpen));
panelElements.forEach(panel => panel.addEventListener('transitionend', toggleActive));
