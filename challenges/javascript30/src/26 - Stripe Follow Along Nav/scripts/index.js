/*
 * Elements
 */

const navElement = document.querySelector('.js-nav');
const dropdownBackgroundElement = document.querySelector('.js-dropdown-background');
const triggerElements = document.querySelectorAll('.js-trigger');

/*
 * Functions
 */

function handleEnter() {
  this.classList.add('trigger-enter');
  setTimeout(() => {
    if (this.classList.contains('trigger-enter')) {
      this.classList.add('trigger-enter-active');
    }
  }, 150);
  dropdownBackgroundElement.classList.add('open');
  const dropdown = this.querySelector('.js-dropdown');
  const dropdownCoords = dropdown.getBoundingClientRect();
  const navCoords = navElement.getBoundingClientRect();
  const coords = {
    height: dropdownCoords.height,
    width: dropdownCoords.width,
    top: dropdownCoords.top - navCoords.top,
    left: dropdownCoords.left - navCoords.left,
  };
  dropdownBackgroundElement.style.setProperty('width', `${coords.width}px`);
  dropdownBackgroundElement.style.setProperty('height', `${coords.height}px`);
  dropdownBackgroundElement.style.setProperty('transform', `translate(${coords.left}px, ${coords.top}px)`);
}

function handleLeave() {
  this.classList.remove('trigger-enter', 'trigger-enter-active');
  dropdownBackgroundElement.classList.remove('open');
}

/*
 * Initialise
 */

triggerElements.forEach(trigger => trigger.addEventListener('mouseenter', handleEnter));
triggerElements.forEach(trigger => trigger.addEventListener('mouseleave', handleLeave));
