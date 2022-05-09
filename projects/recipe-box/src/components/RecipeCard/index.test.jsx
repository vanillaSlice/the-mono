import React from 'react';
import { shallow } from 'enzyme';

import RecipeCard from '.';

describe('RecipeCard', () => {
  const onClick = jest.fn();
  const recipeCard = shallow((
    <RecipeCard
      onClick={onClick}
      image="test-image.jpg"
      name="test-name"
    />
  ));

  test('function triggered when clicked', () => {
    recipeCard.simulate('click');
    expect(onClick).toHaveBeenCalledTimes(1);
  });

  test('renders image', () => {
    const image = recipeCard.find('.recipe-card__img');
    expect(image.props().style.backgroundImage).toBe('url(\'test-image.jpg\')');
  });

  test('renders recipe name', () => {
    expect(recipeCard.find('.recipe-card__title').text()).toBe('test-name');
  });
});
