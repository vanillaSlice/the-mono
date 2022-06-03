import React from 'react';
import { mount } from 'enzyme';

import { SearchBar } from '../SearchBar';

describe('SearchBar', () => {
  const fetchWeather = jest.fn();
  const searchBar = mount(<SearchBar fetchWeather={fetchWeather} />);

  it('renders an input box', () => {
    expect(searchBar.find('input').length).toBe(1);
  });

  it('renders a button', () => {
    expect(searchBar.find('button').length).toBe(1);
  });

  it('fetches weather on submit', () => {
    searchBar.find('input').first().simulate('change', { target: { value: 'liverpool' } });
    searchBar.find('form').simulate('submit');
    expect(fetchWeather).toHaveBeenCalledWith('liverpool');
  });
});
