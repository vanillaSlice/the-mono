/*
 * Text Constants
 */

const character = 'Mark "Rent-boy" Renton';
const film = 'Trainspotting';
const quotes = [
  'Choose Life.',
  'Choose a job.',
  'Choose a career.',
  'Choose a family.',
  'Choose a f**king big television.',
  'Choose washing machines.',
  'Choose cars.',
  'Choose compact disc players.',
  'Choose electrical tin openers.',
  'Choose good health.',
  'Choose low cholesterol.',
  'Choose dental insurance.',
  'Choose fixed interest mortgage repayments.',
  'Choose a starter home.',
  'Choose your friends.',
  'Choose leisurewear and matching luggage.',
  'Choose a three-piece suit on hire purchase in a range of f**king fabrics.',
  'Choose DIY and wondering who the f**k you are on Sunday morning.',
  'Choose sitting on that couch watching mind-numbing, spirit-crushing game shows, stuffing f**king junk food into your mouth.',
  'Choose rotting away at the end of it all, p**sing your last in a miserable home, nothing more than an embarrassment to the selfish, f**ked up brats you spawned to replace yourselves.',
  'Choose your future.',
];

/*
 * DOM Elements
 */

const characterElement = $('.js-character');
const filmElement = $('.js-film');
const quoteElement = $('.js-quote');
const nextBtnElement = $('.js-next-btn');
const tweetBtnElement = $('.js-tweet-btn');

/*
 * Helper Functions
 */

function getRandomElement(arr) {
  return arr[Math.floor(Math.random() * arr.length)];
}

function getRandomQuote() {
  return getRandomElement(quotes);
}

/*
 * Typer Class
 */

class Typer {
  constructor(element, text) {
    this.element = element;
    this.text = text;
    this.currentIndex = 0;
  }

  start() {
    if (this.currentIndex > this.text.length) {
      return;
    }

    this.element.html(
      `<span>${this.text.substring(0, this.currentIndex)}</span>`
      + `<span class="hidden">${this.text.substring(this.currentIndex)}</span>`,
    );

    this.currentIndex += 1;

    this.timeout = setTimeout(this.start.bind(this), 25);
  }

  stop() {
    clearTimeout(this.timeout);
  }

  reset() {
    this.currentIndex = 0;
  }
}

/*
 * Typers
 */

const characterTyper = new Typer(characterElement, character);
const filmTyper = new Typer(filmElement, film);
const quoteTyper = new Typer(quoteElement, getRandomQuote());

/*
 * Button Click Handlers
 */

function handleNextBtnClick() {
  quoteTyper.stop();
  quoteTyper.reset();
  quoteTyper.text = getRandomQuote();
  quoteTyper.start();
}

function handleTweetBtnClick() {
  window.open(encodeURI(`https://twitter.com/intent/tweet?text="${quoteTyper.text}"&hashtags=${film.toLowerCase()}`));
}

/*
 * Initialise
 */

nextBtnElement.click(handleNextBtnClick);
tweetBtnElement.click(handleTweetBtnClick);
characterTyper.start();
filmTyper.start();
quoteTyper.start();
