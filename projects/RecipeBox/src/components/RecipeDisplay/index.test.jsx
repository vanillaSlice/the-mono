import React from 'react';
import { shallow } from 'enzyme';

import RecipeDisplay from '.';

describe('RecipeDisplay', () => {
  const onEdit = jest.fn();
  const onDelete = jest.fn();
  const recipeDisplay = shallow((
    <RecipeDisplay
      image="test-image.jpg"
      name="test-name"
      ingredients="test-ingredients"
      method="test-method"
      onEdit={onEdit}
      onDelete={onDelete}
    />
  ));

  describe('image', () => {
    const image = recipeDisplay.find('.recipe-display__img');

    test('renders', () => {
      expect(image.props().src).toBe('test-image.jpg');
    });

    test('renders alt attribute', () => {
      expect(image.props().alt).toBe('test-name');
    });

    test('renders title attribute', () => {
      expect(image.props().title).toBe('test-name');
    });
  });

  describe('recipe-info', () => {
    const recipeInfo = recipeDisplay.find('.recipe-display__text');

    test('renders ingredients', () => {
      expect(recipeInfo.at(0).text()).toBe('test-ingredients');
    });

    test('renders method', () => {
      expect(recipeInfo.at(1).text()).toBe('test-method');
    });
  });

  describe('buttons', () => {
    const buttons = recipeDisplay.find('Button');
    const editButton = buttons.at(0);
    const deleteButton = buttons.at(1);

    test('renders edit button', () => {
      expect(editButton.props().text).toBe('Edit');
    });

    test('edit button is given edit function', () => {
      expect(editButton.props().onClick).toBe(onEdit);
    });

    test('renders delete button', () => {
      expect(deleteButton.props().text).toBe('Delete');
    });

    test('delete button is given delete function', () => {
      expect(deleteButton.props().onClick).toBe(onDelete);
    });
  });
});
