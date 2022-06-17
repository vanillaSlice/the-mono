/*
 * Elements
 */

const linkElements = document.querySelectorAll('.menu__link, .text__link');
const highlightElement = document.createElement('span');
highlightElement.classList.add('highlight');
document.body.appendChild(highlightElement);

/*
 * Variables
 */

let currentLink;

/*
 * Functions
 */

function highlightLink(link) {
  if (!link) {
    return;
  }

  const linkCoords = link.getBoundingClientRect();
  const coords = {
    width: linkCoords.width,
    height: linkCoords.height,
    top: linkCoords.top + window.scrollY,
    left: linkCoords.left + window.scrollX,
  };
  highlightElement.style.width = `${coords.width}px`;
  highlightElement.style.height = `${coords.height}px`;
  highlightElement.style.transform = `translate(${coords.left}px, ${coords.top}px)`;
}

/*
 * Initialise
 */

linkElements.forEach(link => link.addEventListener('mouseenter', () => {
  currentLink = link;
  highlightLink(currentLink);
}));
// make sure we follow the link on resize
window.addEventListener('resize', () => highlightLink(currentLink));
