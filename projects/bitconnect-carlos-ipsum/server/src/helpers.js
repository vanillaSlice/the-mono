const headings = require('./headings.json');
const quotes = require('./quotes.json');

function getHeadings(hTagLevel) {
  return hTagLevel ? addHTags(hTagLevel, headings) : [...headings];
}

function addHTags(hTagLevel, headings) {
  return headings.map((heading) => addHTag(hTagLevel, heading));
}

function addHTag(hTagLevel, heading) {
  return addTag(`h${hTagLevel}`, heading);
}

function addTag(tag, content) {
  return `<${tag}>${content}</${tag}>`;
}

function getRandomHeading(hTagLevel) {
  const heading = getRandomArrayElement(headings);
  return hTagLevel ? addHTag(hTagLevel, heading) : heading;
}

function getRandomArrayElement(array) {
  return array[getRandomInt(0, array.length - 1)];
}

function getRandomInt(min, max) {
  return Math.floor(Math.random() * (max - min + 1)) + min;
}

function getQuotes(includePTags) {
  return includePTags ? addPTags(quotes) : [...quotes];
}

function addPTags(paragraphs) {
  return paragraphs.map((paragraph) => addPTag(paragraph));
}

function addPTag(paragraph) {
  return addTag('p', paragraph);
}

function getRandomQuote(includePTags) {
  const quote = getRandomArrayElement(quotes);
  return includePTags ? addPTag(quote) : quote;
}

function getText(options) {
  const text = [];
  for (let i = 0; i < options.paragraphs; i += 1) {
    text.push(getTextEntry(options));
  }
  return text;
}

function getTextEntry(options) {
  const entry = {};

  if (options.includeHeadings) {
    entry.heading = getRandomHeading(options.hTagLevel);
  }

  entry.paragraph = getParagraph(options);

  return entry;
}

function getParagraph(options) {
  let paragraph = '';

  const quotesInParagraph = getRandomInt(options.minQuotes, options.maxQuotes);

  for (let i = 0; i < quotesInParagraph; i += 1) {
    paragraph += `${getRandomArrayElement(quotes)} `;
  }

  paragraph = paragraph.trim();

  if (options.includePTags) {
    paragraph = addPTag(paragraph);
  }

  return paragraph;
}

module.exports = {
  getHeadings,
  getRandomHeading,
  getQuotes,
  getRandomQuote,
  getText,
};
