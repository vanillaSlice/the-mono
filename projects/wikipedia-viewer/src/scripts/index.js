import { version } from '../../package.json';

/*
 * URLs
 */

const wikipediaUrl = 'https://en.wikipedia.org/wiki/';
const queryUrl = 'https://en.wikipedia.org/w/api.php?action=query&prop=extracts&exintro=true&exsentences=1&explaintext=true&generator=search&format=json&gsrsearch=';
const randomUrl = 'https://en.wikipedia.org/wiki/Special:Random';

/*
 * DOM Elements
 */

const searchBtnElement = $('.js-search-btn');
const randomBtnElement = $('.js-random-btn');
const searchFormContainerElement = $('.js-search-form-container');
const searchFormElement = $('.js-search-form');
const searchInputElement = $('.js-search-input');
const resultsContainerElement = $('.js-results-container');
const versionElement = $('.js-version');

/*
 * Functions
 */

function handleSearchBtnClick() {
  searchBtnElement.attr('disabled', true);
  searchFormContainerElement.addClass('animated fadeIn').removeClass('hidden');
}

function handleRandomBtnClick() {
  window.open(randomUrl);
}

function handleSearchSuccess(data) {
  resultsContainerElement.empty().removeClass('hidden');

  const results = $('<div class="wiki-viewer__search-results animated slideInUp">');

  results.appendTo(resultsContainerElement);

  if (!data.query || !data.query.pages) {
    results.append('No results returned');
    return;
  }

  const { pages } = data.query;
  Object.keys(pages).forEach((key) => {
    const page = pages[key];
    const href = wikipediaUrl + page.title.replace(' ', '_');

    results.append(
      `<a class="wiki-viewer__search-result" href="${href}" target="_blank">`
      + '<div>'
      + `<h2>${page.title}</h2>`
      + `<p class="wiki-viewer__search-result-extract">${page.extract}</p>`
      + '</div>'
      + '</a>',
    );
  });
}

function handleSearchError(error) {
  console.error(error);
}

function handleSearch() {
  $.ajax({
    dataType: 'jsonp',
    url: queryUrl + encodeURIComponent(searchInputElement.val()),
    success: handleSearchSuccess,
    error: handleSearchError,
  });
}

function handleSearchFormSubmit(e) {
  e.preventDefault();
  searchInputElement.blur();
  handleSearch();
}

/*
 * Initialise
 */

searchBtnElement.click(handleSearchBtnClick);
randomBtnElement.click(handleRandomBtnClick);
searchFormElement.submit(handleSearchFormSubmit);
versionElement.text(version);
