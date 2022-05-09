import React from 'react';
import { shallow } from 'enzyme';

import Header from '.';

describe('Header', () => {
  const onAdd = jest.fn();
  const header = shallow(<Header onAdd={onAdd} />);

  test('renders heading', () => {
    expect(header.find('.header__heading').length).toBe(1);
  });

  describe('add recipe button', () => {
    const addRecipeButton = header.find('IconButton');

    test('renders', () => {
      expect(addRecipeButton.length).toBe(1);
    });

    test('is given onAdd function', () => {
      expect(addRecipeButton.props().onClick).toBe(onAdd);
    });
  });
});
