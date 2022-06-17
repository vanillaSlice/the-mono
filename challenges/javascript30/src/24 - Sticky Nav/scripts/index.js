/*
 * Elements.
 */

const navElement = document.querySelector('.js-nav');

/*
 * Variables
 */

const topOfNav = navElement.offsetTop;

/*
 * Functions
 */

function fixNav() {
  if (window.scrollY >= topOfNav) {
    document.body.style.paddingTop = `${navElement.offsetHeight}px`;
    document.body.classList.add('fixed-nav');
  } else {
    document.body.style.paddingTop = 0;
    document.body.classList.remove('fixed-nav');
  }
}

/*
 * Initialise
 */

window.addEventListener('scroll', fixNav);
