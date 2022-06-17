/*
 * Elements
 */

const endpoint = 'https://gist.githubusercontent.com/Miserlou/c5cd8364bf9b2420bb29/raw/2bf258763cdddd704f8ffd3ea9a3e81d25e2c6f6/cities.json';
const cities = [];
const searchInputElement = document.querySelector('.js-search-input');
const suggestionsElement = document.querySelector('.js-suggestions');

/*
 * Functions
 */

function findMatches(wordToMatch) {
  return cities.filter((place) => {
    const regex = new RegExp(wordToMatch, 'gi');
    return place.city.match(regex) || place.state.match(regex);
  });
}

function numberWithCommas(x) {
  return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
}

function displayMatches() {
  const matches = findMatches(this.value);
  const html = matches.map((place) => {
    const regex = new RegExp(this.value, 'gi');
    const cityName = place.city.replace(regex, match => `<span class="highlight">${match}</span>`);
    const stateName = place.state.replace(regex, match => `<span class="highlight">${match}</span>`);
    return `<li class="suggestion-item">
        <span class="name">${cityName}, ${stateName}</span>
        <span class="population">${numberWithCommas(place.population)}</span>
      </li>`;
  }).join('');
  suggestionsElement.innerHTML = html;
}

/*
 * Initialise
 */

searchInputElement.addEventListener('change', displayMatches);
searchInputElement.addEventListener('keyup', displayMatches);

fetch(endpoint)
  .then(blob => blob.json())
  .then(data => cities.push(...data));
