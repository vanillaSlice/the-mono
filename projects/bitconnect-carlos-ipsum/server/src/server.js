const express = require('express');
const path = require('path');
const swaggerUI = require('swagger-ui-express');
const {
  hTagLevelValidator,
  includePTagsValidator,
  paragraphsValidator,
  includeHeadingsValidator,
  minQuotesAndMaxQuotesValidator,
  minQuotesLessThanMaxQuotesValidator,
  maxQuotesGreaterThanMinQuotesValidator,
  errorHandler,
} = require('./validators');
const {
  getHeadings,
  getRandomHeading,
  getQuotes,
  getRandomQuote,
  getText,
} = require('./helpers');
const swaggerDocument = require('./swagger.json');

const app = express();
const port = process.env.PORT || 3001;

app.use('/api-docs', swaggerUI.serve, swaggerUI.setup(swaggerDocument));

app.get('/api/headings', [hTagLevelValidator, errorHandler], (req, res) => {
  res.send({ headings: getHeadings(req.query.hTagLevel) });
});

app.get('/api/headings/random', [hTagLevelValidator, errorHandler], (req, res) => {
  res.send({ heading: getRandomHeading(req.query.hTagLevel) });
});

app.get('/api/quotes', [includePTagsValidator, errorHandler], (req, res) => {
  res.send({ quotes: getQuotes(req.query.includePTags) });
});

app.get('/api/quotes/random', [includePTagsValidator, errorHandler], (req, res) => {
  res.send({ quote: getRandomQuote(req.query.includePTags) });
});

app.get(
  '/api/text',
  [
    hTagLevelValidator,
    includePTagsValidator,
    paragraphsValidator,
    includeHeadingsValidator,
    minQuotesAndMaxQuotesValidator,
    minQuotesLessThanMaxQuotesValidator,
    maxQuotesGreaterThanMinQuotesValidator,
    errorHandler,
  ], (req, res) => {
    res.send({ text: getText(req.query) });
  },
);

/*
 * Using static client files only in production because client runs
 * on its own server in development.
 */
if (process.env.NODE_ENV === 'production') {
  app.use(express.static(path.join(__dirname, '..', '..', 'client', 'build')));

  app.use((req, res) => {
    res.sendFile(path.join(__dirname, '..', '..', 'client', 'build', 'index.html'));
  });
}

module.exports = app.listen(port, () => console.log(`Listening on ${port}`));
