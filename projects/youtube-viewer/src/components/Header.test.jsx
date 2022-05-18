import React from 'react';
import { shallow } from 'enzyme';

import Header from './Header';

describe('Header', () => {
  const onSearch = jest.fn();
  const header = shallow(<Header onSearch={onSearch} />);

  it('renders heading', () => {
    expect(header.find('h1').length).toBe(1);
  });

  it('renders search form', () => {
    expect(header.find('.search-form').length).toBe(1);
  });

  it('renders back button', () => {
    expect(header.find('.back-button').length).toBe(1);
  });

  it('back button click toggles search box display', () => {
    header.find('.back-button').simulate('click');
    expect(header.state().displaySearchSmall).toBe(true);
    header.find('.back-button').simulate('click');
    expect(header.state().displaySearchSmall).toBe(false);
  });

  it('renders search button', () => {
    expect(header.find('.search-button').length).toBe(1);
  });

  it('search button click toggles search box display', () => {
    header.find('.search-button').simulate('click');
    expect(header.state().displaySearchSmall).toBe(true);
    header.find('.search-button').simulate('click');
    expect(header.state().displaySearchSmall).toBe(false);
  });

  it('onSearch function is passed term on submit', () => {
    header.find('input').simulate('change', { target: { value: 'surfboards' } });
    header.find('form').simulate('submit', { preventDefault() {} });
    expect(onSearch).toHaveBeenCalledWith('surfboards');
  });
});
