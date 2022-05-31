import React, { Component } from 'react';

import './App.css';

import carlosImg from './carlos.png';

import { version } from '../package.json';

class App extends Component {
  constructor(props) {
    super(props);

    this.state = {
      paragraphs: 1,
      minQuotes: 1,
      maxQuotes: 5,
      hTagLevel: 0,
      includePTags: false,
      text: '',
    };

    this.handleFormSubmit = this.handleFormSubmit.bind(this);
    this.handleParagraphsChange = this.handleParagraphsChange.bind(this);
    this.handleMinQuotesChange = this.handleMinQuotesChange.bind(this);
    this.handleMaxQuotesChange = this.handleMaxQuotesChange.bind(this);
    this.handleHTagLevelChange = this.handleHTagLevelChange.bind(this);
    this.handleIncludePTagsChange = this.handleIncludePTagsChange.bind(this);
    this.handleTextChange = this.handleTextChange.bind(this);
  }

  handleFormSubmit(e) {
    e.preventDefault();

    const {
      hTagLevel,
      paragraphs,
      minQuotes,
      maxQuotes,
      includePTags,
    } = this.state;

    const includeHeadings = hTagLevel !== 0;

    let url = '/api/text'
      + `?paragraphs=${paragraphs}`
      + `&includeHeadings=${includeHeadings}`
      + `&minQuotes=${minQuotes}`
      + `&maxQuotes=${maxQuotes}`
      + `&includePTags=${includePTags}`;

    if (includeHeadings) {
      url += `&hTagLevel=${hTagLevel}`;
    }

    fetch(url)
      .then(res => res.json())
      .then((data) => {
        let text = '';
        if (!data.text) {
          text = 'Error when generating text';
        } else {
          data.text.forEach((textEntry) => {
            if (textEntry.heading) {
              text += `${textEntry.heading}\n`;
            }
            text += `${textEntry.paragraph}\n\n`;
          });
          text = text.trim();
        }
        this.setState({ text });
      });
  }

  handleParagraphsChange(e) {
    this.setState({ paragraphs: parseInt(e.target.value, 10) });
  }

  handleMinQuotesChange(e) {
    const minQuotes = parseInt(e.target.value, 10);
    this.setState((prevState) => {
      const newState = { minQuotes };
      newState.maxQuotes = Math.max(minQuotes, prevState.maxQuotes);
      return newState;
    });
  }

  handleMaxQuotesChange(e) {
    const maxQuotes = parseInt(e.target.value, 10);
    this.setState((prevState) => {
      const newState = { maxQuotes };
      newState.minQuotes = Math.min(maxQuotes, prevState.minQuotes);
      return newState;
    });
  }

  handleHTagLevelChange(e) {
    this.setState({ hTagLevel: parseInt(e.target.value, 10) });
  }

  handleIncludePTagsChange() {
    const { includePTags } = this.state;
    this.setState({ includePTags: !includePTags });
  }

  handleTextChange(e) {
    this.setState({ text: e.target.value });
  }

  render() {
    const {
      paragraphs,
      minQuotes,
      maxQuotes,
      hTagLevel,
      includePTags,
      text,
    } = this.state;

    return (
      <div className="App container text-center">
        <header className="row flex-center flex-middle">
          <img className="left no-border" src={carlosImg} alt="Carlos Matos" title="Carlos Matos" />
          <h1 className="col col-12 md-9">
            <a href=".">Bitconnect Carlos Ipsum</a>
          </h1>
          <img className="right no-border" src={carlosImg} alt="Carlos Matos" title="Carlos Matos" />
        </header>
        <main className="row margin-bottom-none">
          <div className="col col-12 md-4 text-left">
            <form onSubmit={this.handleFormSubmit}>
              <div className="form-group">
                <label className="input-block" htmlFor="number-of-paragraphs">
                  Number of paragraphs:
                  <input
                    className="input-block"
                    type="number"
                    min="1"
                    max="100"
                    value={paragraphs}
                    onChange={this.handleParagraphsChange}
                    id="number-of-paragraphs"
                  />
                </label>
              </div>
              <div className="form-group">
                <label className="input-block" htmlFor="min-quotes-per-paragraph">
                  Minimum quotes per paragraph:
                  <input
                    className="input-block"
                    type="number"
                    min="1"
                    max="20"
                    value={minQuotes}
                    onChange={this.handleMinQuotesChange}
                    id="min-quotes-per-paragraph"
                  />
                </label>
              </div>
              <div className="form-group">
                <label className="input-block" htmlFor="max-quotes-per-paragraph">
                  Maximum quotes per paragraph:
                  <input
                    className="input-block"
                    type="number"
                    min="1"
                    max="20"
                    value={maxQuotes}
                    onChange={this.handleMaxQuotesChange}
                    id="max-quotes-per-paragraph"
                  />
                </label>
              </div>
              <div className="form-group">
                <label className="input-block" htmlFor="headings">
                  Headings:
                  <select
                    className="input-block"
                    id="headings"
                    value={hTagLevel}
                    onChange={this.handleHTagLevelChange}
                  >
                    <option value="0">none</option>
                    <option value="1">h1</option>
                    <option value="2">h2</option>
                    <option value="3">h3</option>
                    <option value="4">h4</option>
                    <option value="5">h5</option>
                    <option value="6">h6</option>
                  </select>
                </label>
              </div>
              <div className="form-group">
                <label className="input-block paper-check" htmlFor="include-p-tags">
                  <input
                    className="input-block"
                    value={includePTags}
                    onChange={this.handleIncludePTagsChange}
                    type="checkbox"
                    name="p-tags"
                    id="include-p-tags"
                  />
                  <span>Include &lt;p&gt; tags</span>
                </label>
              </div>
              <button className="btn-block" type="submit">BITCONNEEEEEEEEEEEECT!</button>
            </form>
          </div>
          <div className="form-group col col-12 md-8 margin-bottom-none">
            <textarea
              className="input-block padding no-resize"
              rows="20"
              value={text}
              onChange={this.handleTextChange}
              id="text"
            />
          </div>
        </main>
        <footer className="col col-12">
          <div className="row flex-edges margin-none">
            <p>
              v
              {version}
            </p>
            <p>
              Made with&nbsp;
              <span role="img" aria-label="music">🎧</span>
              &nbsp;by&nbsp;
              <a href="https://mikelowe.xyz">Mike</a>
            </p>
          </div>
        </footer>
      </div>
    );
  }
}

export default App;
