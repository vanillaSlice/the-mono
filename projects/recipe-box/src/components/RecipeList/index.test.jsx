import React from 'react';
import { shallow } from 'enzyme';

import RecipeList from '.';

const recipes = [
  {
    name: 'test-recipe-1',
    ingredients: 'test-ingredients-1',
    method: 'test-method-1',
    image: 'test-image-1',
  },
  {
    name: 'test-recipe-2',
    ingredients: 'test-ingredients-2',
    method: 'test-method-2',
    image: 'test-image-2',
  },
  {
    name: 'test-recipe-3',
    ingredients: 'test-ingredients-3',
    method: 'test-method-3',
    image: 'test-image-3',
  },
  {
    name: 'test-recipe-4',
    ingredients: 'test-ingredients-4',
    method: 'test-method-4',
    image: 'test-image-4',
  },
  {
    name: 'test-recipe-5',
    ingredients: 'test-ingredients-5',
    method: 'test-method-5',
    image: 'test-image-5',
  },
];

describe('RecipeList', () => {
  const onClick = jest.fn();
  const recipeList = shallow((
    <RecipeList
      recipes={recipes}
      onClick={onClick}
    />
  ));

  describe('recipe cards', () => {
    const recipeCards = recipeList.find('RecipeCard');

    test('render', () => {
      expect(recipeList.find('RecipeCard').length).toBe(5);
    });

    test('passes selected recipe to function when clicked', () => {
      recipeCards.forEach((recipeCard, index) => {
        recipeCard.simulate('click');
        expect(onClick).toHaveBeenLastCalledWith(recipes[index]);
      });
    });
  });

  test('renders rows', () => {
    expect(recipeList.find('.recipe-list__row').length).toBe(2);
  });
});
