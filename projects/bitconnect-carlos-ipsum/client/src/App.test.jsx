import React from 'react';
import { mount } from 'enzyme';
import fetchMock from 'fetch-mock';

import App from './App';

let wrapper;

beforeEach(() => {
  wrapper = mount(<App />);
});

afterEach(() => {
  fetchMock.restore();
});

it('updates number of paragraphs', () => {
  expect(wrapper.state('paragraphs')).toBe(1);

  const input = wrapper.find('#number-of-paragraphs');
  input.instance().value = 10;
  input.simulate('change');

  expect(wrapper.state('paragraphs')).toBe(10);
});

it('updates minimum quotes', () => {
  expect(wrapper.state('minQuotes')).toBe(1);

  const input = wrapper.find('#min-quotes-per-paragraph');
  input.instance().value = 5;
  input.simulate('change');

  expect(wrapper.state('minQuotes')).toBe(5);
});

it('updates maximum quotes', () => {
  expect(wrapper.state('maxQuotes')).toBe(5);

  const input = wrapper.find('#max-quotes-per-paragraph');
  input.instance().value = 10;
  input.simulate('change');

  expect(wrapper.state('maxQuotes')).toBe(10);
});

it('updates heading tag level', () => {
  expect(wrapper.state('hTagLevel')).toBe(0);

  const input = wrapper.find('#headings');
  input.instance().value = 1;
  input.simulate('change');

  expect(wrapper.state('hTagLevel')).toBe(1);
});

it('updates if paragraph tag should be included', () => {
  expect(wrapper.state('includePTags')).toBe(false);

  wrapper.find('#include-p-tags').simulate('change');

  expect(wrapper.state('includePTags')).toBe(true);
});

it('updates text', () => {
  expect(wrapper.state('text')).toBe('');

  const input = wrapper.find('#text');
  input.instance().value = 'some text';
  input.simulate('change');

  expect(wrapper.state('text')).toBe('some text');
});

it('sends request to expected URL', async () => {
  fetchMock.get('/api/text?paragraphs=1&includeHeadings=false&minQuotes=1&maxQuotes=5&includePTags=false', {});

  wrapper.find('form').simulate('submit');

  await fetchMock.flush();

  expect(fetchMock.called()).toBe(true);
});

it('sends request to expected URL with heading level', async () => {
  const input = wrapper.find('#headings');
  input.instance().value = 1;
  input.simulate('change');

  fetchMock.get('/api/text?paragraphs=1&includeHeadings=true&minQuotes=1&maxQuotes=5&includePTags=false&hTagLevel=1', {});

  wrapper.find('form').simulate('submit');

  await fetchMock.flush();

  expect(fetchMock.called()).toBe(true);
});

it('updates text on successful response', async () => {
  const res = {
    text: [
      {
        paragraph: 'BITCONNEEEEEEEEEEEECT!',
      },
    ],
  };

  fetchMock.get('*', res);

  wrapper.find('form').simulate('submit');

  await fetchMock.flush();

  expect(wrapper.state('text')).toBe('BITCONNEEEEEEEEEEEECT!');
});

it('updates text with headings when requested', async () => {
  const res = {
    text: [
      {
        heading: 'Hey hey heeeeeeeeeey!',
        paragraph: 'BITCONNEEEEEEEEEEEECT!',
      },
    ],
  };

  fetchMock.get('*', res);

  wrapper.find('form').simulate('submit');

  await fetchMock.flush();

  expect(wrapper.state('text')).toBe('Hey hey heeeeeeeeeey!\nBITCONNEEEEEEEEEEEECT!');
});

it('updates text on unsuccessful response with error message', async () => {
  const res = {
    errors: {
      paragraphs: {
        location: 'query',
        param: 'paragraphs',
        value: '1000',
        msg: 'must be between 1 and 100',
      },
    },
  };

  fetchMock.get('*', res);

  wrapper.find('form').simulate('submit');
  wrapper.find('form').simulate('change');

  await fetchMock.flush();

  expect(wrapper.state('text')).toBe('Error when generating text');
});
