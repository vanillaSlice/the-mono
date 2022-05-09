import React from 'react';
import { shallow } from 'enzyme';
import ReactTestUtils from 'react-dom/test-utils';

import RecipeForm from '.';
import RecipeFormTestUtils from './RecipeFormTestUtils';

describe('RecipeForm', () => {
  test('renders name', () => {
    const recipeForm = shallow(<RecipeForm name="test-name" />);
    expect(recipeForm.find('#name').props().value).toBe('test-name');
  });

  test('renders ingredients', () => {
    const recipeForm = shallow(<RecipeForm ingredients="test-ingredients" />);
    expect(recipeForm.find('#ingredients').props().value).toBe('test-ingredients');
  });

  test('renders method', () => {
    const recipeForm = shallow(<RecipeForm method="test-method" />);
    expect(recipeForm.find('#method').props().value).toBe('test-method');
  });

  test('renders image', () => {
    const recipeForm = shallow(<RecipeForm image="test-image" />);
    expect(recipeForm.find('#image').props().value).toBe('test-image');
  });

  test('renders save button', () => {
    const recipeForm = shallow(<RecipeForm />);
    expect(recipeForm.find('#save').props().text).toBe('Save');
  });

  describe('on form submit', () => {
    const onSave = (recipe) => {
      expect(recipe).toEqual({
        name: 'test-name',
        ingredients: 'test-ingredients',
        method: 'test-method',
        image: 'test-image',
      });
    };
    const component = ReactTestUtils.renderIntoDocument(<RecipeForm onSave={onSave} />);

    RecipeFormTestUtils.updateName(component, 'test-name');
    RecipeFormTestUtils.updateIngredients(component, 'test-ingredients');
    RecipeFormTestUtils.updateMethod(component, 'test-method');
    RecipeFormTestUtils.updateImage(component, 'test-image');

    RecipeFormTestUtils.submit(component);

    test('clears form', () => {
      expect(RecipeFormTestUtils.getName(component)).toBe('');
      expect(RecipeFormTestUtils.getIngredients(component)).toBe('');
      expect(RecipeFormTestUtils.getMethod(component)).toBe('');
      expect(RecipeFormTestUtils.getImage(component)).toBe('');
    });

    test('renders save message', () => {
      const saveMessage = ReactTestUtils.findRenderedDOMComponentWithClass(component, 'recipe-form__save-message');
      expect(saveMessage.textContent).toBe('Saved recipe!');
    });
  });

  describe('clear button', () => {
    const recipeForm = shallow((
      <RecipeForm
        name="test-name"
        ingredients="test-ingredients"
        method="test-method"
        image="test-image"
      />
    ));
    const clearButton = recipeForm.find('#clear');

    test('renders', () => {
      expect(clearButton.length).toBe(1);
    });

    test('clears form on click', () => {
      clearButton.simulate('click');
      expect(recipeForm.find('#name').props().value).toBe('');
      expect(recipeForm.find('#ingredients').props().value).toBe('');
      expect(recipeForm.find('#method').props().value).toBe('');
      expect(recipeForm.find('#image').props().value).toBe('');
    });
  });
});
