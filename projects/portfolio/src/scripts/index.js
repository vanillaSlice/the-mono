import logo from '../images/logo.png';
import altLogo from '../images/colour-logo.jpg';

/*
 * Constants
 */

const quotes = [
  'like a night club in the morning...',
  'like a recently disinfected shithouse...',
  'like a dose of scabies...',
  'like a death at a birthday party...',
  'like a sucked and spat-out smartie...',
  'like a black widow spider in the shadows of disgrace...',
];
const quote = quotes[Math.floor(Math.random() * quotes.length)];

/*
 * Elements
 */

const htmlElement = $('html');
const bodyElement = $('body');
const scrollableElements = $('.js-scrollable');
const navbarToggleElement = $('.js-navbar-toggle');
const quoteElement = $('.js-quote');
const logoElement = $('.js-logo');

/*
 * Functions
 */

function handleScrollableElementClick(e) {
  e.preventDefault();

  const scrollTop = $(e.target.hash).offset().top - 50;
  const duration = 800;
  $(htmlElement).animate({ scrollTop }, duration);
  $(bodyElement).animate({ scrollTop }, duration);

  if (navbarToggleElement.is(':visible') && !navbarToggleElement.hasClass('collapsed')) {
    navbarToggleElement.trigger('click');
  }
}

function handleLogoElementHoverIn(e) {
  e.target.setAttribute('src', altLogo);
}

function handleLogoElementHoverOut(e) {
  e.target.setAttribute('src', logo);
}

/*
 * Initialise
 */

scrollableElements.click(handleScrollableElementClick);
logoElement.hover(handleLogoElementHoverIn, handleLogoElementHoverOut);
quoteElement.text(quote);
