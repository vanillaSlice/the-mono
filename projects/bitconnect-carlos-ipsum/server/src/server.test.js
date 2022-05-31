const request = require('supertest');
const server = require('./server');
const headings = require('./headings.json');
const quotes = require('./quotes.json');

describe('GET /api/headings', () => {
  const baseUrl = '/api/headings';

  it('returns array of all headings', (done) => {
    request(server)
      .get(baseUrl)
      .expect(200, { headings }, done);
  });

  for (let i = 1; i <= 6; i += 1) {
    it(`?hTagLevel=${i} returns array of all headings with surrounding <h${i}> tags`, (done) => {
      const headingsWithTags = headings.map((heading) => `<h${i}>${heading}</h${i}>`);
      request(server)
        .get(`${baseUrl}?hTagLevel=${i}`)
        .expect(200, { headings: headingsWithTags }, done);
    });
  }

  [0, 7, '', NaN, 'bitconneeeeect'].forEach((hTagLevel) => {
    it(`?hTagLevel=${hTagLevel} returns an error`, (done) => {
      request(server)
        .get(`${baseUrl}?hTagLevel=${hTagLevel}`)
        .expect(422, done);
    });
  });
});

describe('GET /api/headings/random', () => {
  const baseUrl = '/api/headings/random';

  it('returns a random heading', () => (request(server)
    .get(baseUrl)
    .expect(200)
    .then((res) => {
      expect(headings.includes(res.body.heading)).toBe(true);
    })
  ));

  for (let i = 1; i <= 6; i += 1) {
    it(`?hTagLevel=${i} returns a heading with surrounding <h${i}> tags`, () => (request(server)
      .get(`${baseUrl}?hTagLevel=${i}`)
      .expect(200)
      .then((res) => {
        const { heading } = res.body;
        expect(heading.startsWith(`<h${i}>`)).toBe(true);
        expect(heading.endsWith(`</h${i}>`)).toBe(true);
      })
    ));
  }

  [0, 7, '', NaN, 'bitconneeeeect'].forEach((hTagLevel) => {
    it(`?hTagLevel=${hTagLevel} returns an error`, (done) => {
      request(server)
        .get(`${baseUrl}?hTagLevel=${hTagLevel}`)
        .expect(422, done);
    });
  });
});

describe('GET /api/quotes', () => {
  const baseUrl = '/api/quotes';

  it('returns array of all quotes', (done) => {
    request(server)
      .get(baseUrl)
      .expect(200, { quotes }, done);
  });

  [false, 'TRUE', '', NaN, 'bitconneeeeect'].forEach((includePTags) => {
    it(
      `?includePTags=${includePTags} returns array of all quotes without surrounding <p> tags`,
      (done) => {
        request(server)
          .get(`${baseUrl}?includePTags=${includePTags}`)
          .expect(200, { quotes }, done);
      },
    );
  });

  it('?includePTags=true returns array of all quotes with surrounding <p> tags', (done) => {
    const quotesWithTags = quotes.map((quote) => `<p>${quote}</p>`);
    request(server)
      .get(`${baseUrl}?includePTags=true`)
      .expect(200, { quotes: quotesWithTags }, done);
  });
});

describe('GET /api/quotes/random', () => {
  const baseUrl = '/api/quotes/random';

  it('returns a random quote', () => (request(server)
    .get(baseUrl)
    .expect(200)
    .then((res) => {
      expect(quotes.includes(res.body.quote)).toBe(true);
    })
  ));

  [false, 'TRUE', '', NaN, 'bitconneeeeect'].forEach((includePTags) => {
    it(
      `?includePTags=${includePTags} returns a quote without surrounding <p> tags`,
      () => (request(server)
        .get(baseUrl)
        .expect(200)
        .then((res) => {
          expect(quotes.includes(res.body.quote)).toBe(true);
        })
      ),
    );
  });

  it('?includePTags=true returns a quote with surrounding <p> tags', () => (request(server)
    .get(`${baseUrl}?includePTags=true`)
    .expect(200)
    .then((res) => {
      const { quote } = res.body;
      expect(quote.startsWith('<p>')).toBe(true);
      expect(quote.endsWith('</p>')).toBe(true);
    })
  ));
});

describe('GET /api/text', () => {
  const baseUrl = '/api/text';

  it('?includeHeadings=true returns text entries with headings', () => (request(server)
    .get(`${baseUrl}?paragraphs=5&minQuotes=1&maxQuotes=5&includeHeadings=true`)
    .expect(200)
    .then((res) => {
      const { text } = res.body;
      text.forEach((textEntry) => {
        expect(headings.includes(textEntry.heading)).toBe(true);
      });
    })
  ));

  [false, 'TRUE', '', NaN, 'bitconneeeeect'].forEach((includeHeadings) => {
    it(
      `?includeHeadings=${includeHeadings} returns text entries without headings`,
      () => (
        request(server)
          .get(`${baseUrl}?paragraphs=5&minQuotes=1&maxQuotes=5&includeHeadings=${includeHeadings}`)
          .then((res) => {
            const { text } = res.body;
            text.forEach((textEntry) => {
              expect(textEntry.heading).toBe(undefined);
            });
          })
      ),
    );
  });

  for (let i = 1; i <= 6; i += 1) {
    it(`?hTagLevel=${i} returns text entries with headings with surrounding <h${i}> tags`, () => (request(server)
      .get(`${baseUrl}?paragraphs=5&minQuotes=1&maxQuotes=5&includeHeadings=true&hTagLevel=${i}`)
      .expect(200)
      .then((res) => {
        const { text } = res.body;
        text.forEach((textEntry) => {
          const { heading } = textEntry;
          expect(heading.startsWith(`<h${i}>`)).toBe(true);
          expect(heading.endsWith(`</h${i}>`)).toBe(true);
        });
      })
    ));
  }

  [0, 7, '', NaN, 'bitconneeeeect'].forEach((hTagLevel) => {
    it(`?hTagLevel=${hTagLevel} returns an error`, (done) => {
      request(server)
        .get(`${baseUrl}?paragraphs=5&minQuotes=1&maxQuotes=5&includeHeadings=true&hTagLevel=${hTagLevel}`)
        .expect(422, done);
    });
  });

  it('?includePTags=true returns text entries with paragraphs with surrounding <p> tags', () => (request(server)
    .get(`${baseUrl}?paragraphs=5&minQuotes=1&maxQuotes=5&includePTags=true`)
    .expect(200)
    .then((res) => {
      const { text } = res.body;
      text.forEach((textEntry) => {
        const { paragraph } = textEntry;
        expect(paragraph.startsWith('<p>')).toBe(true);
        expect(paragraph.endsWith('</p>')).toBe(true);
      });
    })
  ));

  [false, 'TRUE', '', NaN, 'bitconneeeeect'].forEach((includePTags) => {
    it(
      `?includePTags=${includePTags} returns text entries with paragraphs without surrounding <p> tags`,
      () => (
        request(server)
          .get(`${baseUrl}?paragraphs=5&minQuotes=1&maxQuotes=5&includePTags=${includePTags}`)
          .then((res) => {
            const { text } = res.body;
            text.forEach((textEntry) => {
              const { paragraph } = textEntry;
              expect(paragraph.startsWith('<p>')).toBe(false);
              expect(paragraph.endsWith('</p>')).toBe(false);
            });
          })
      ),
    );
  });

  [0, 101, '', NaN, 'bitconneeeeect'].forEach((paragraphs) => {
    it(`?paragraphs=${paragraphs} returns an error`, (done) => {
      request(server)
        .get(`${baseUrl}?minQuotes=1&maxQuotes=5&includeHeadings=true&paragraphs=${paragraphs}`)
        .expect(422, done);
    });
  });

  [1, 25, 50, 75, 100].forEach((paragraphs) => {
    it(
      `?paragraphs=${paragraphs} returns text entries with ${paragraphs} paragraphs`,
      () => (
        request(server)
          .get(`${baseUrl}?minQuotes=1&maxQuotes=5&paragraphs=${paragraphs}`)
          .then((res) => {
            expect(res.body.text.length).toBe(paragraphs);
          })
      ),
    );
  });

  [0, 21, '', NaN, 'bitconneeeeect'].forEach((minQuotes) => {
    it(`?minQuotes=${minQuotes} returns an error`, (done) => {
      request(server)
        .get(`${baseUrl}?paragraphs=1&maxQuotes=5&includeHeadings=true&minQuotes=${minQuotes}`)
        .expect(422, done);
    });
  });

  [0, 21, '', NaN, 'bitconneeeeect'].forEach((maxQuotes) => {
    it(`?maxQuotes=${maxQuotes} returns an error`, (done) => {
      request(server)
        .get(`${baseUrl}?paragraphs=1&minQuotes=1&includeHeadings=true&maxQuotes=${maxQuotes}`)
        .expect(422, done);
    });
  });

  it('?minQuotes=5&maxQuotes=1 returns error', (done) => {
    request(server)
      .get(`${baseUrl}?paragraphs=1&minQuotes=5&maxQuotes=1`)
      .expect(422, done);
  });

  it('?minQuotes=5&maxQuotes=5 does not return error', (done) => {
    request(server)
      .get(`${baseUrl}?paragraphs=1&minQuotes=5&maxQuotes=5`)
      .expect(200, done);
  });
});

afterAll(() => {
  server.close();
});
