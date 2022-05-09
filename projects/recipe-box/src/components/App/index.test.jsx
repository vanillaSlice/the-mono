import React from 'react';
import ReactTestUtils from 'react-dom/test-utils';
import { mount, shallow } from 'enzyme';

import App from '.';
import RecipeFormTestUtils from '../RecipeForm/RecipeFormTestUtils';

import data from '../../data/recipes.json';

const recipes = [...data.recipes];

beforeEach(() => {
  window.localStorage.clear();
});

describe('App', () => {
  test('renders without crashing', () => {
    mount(<App />);
  });

  test('renders header', () => {
    const app = shallow(<App />);
    expect(app.find('Header').length).toBe(1);
  });

  test('renders recipe list', () => {
    const app = shallow(<App />);
    expect(app.find('RecipeList').length).toBe(1);
  });

  test('recipe click renders add recipe form', () => {
    const app = mount(<App />);
    expect(app.find('RecipeForm').length).toBe(0);
    app.find('IconButton').simulate('click');
    expect(app.find('RecipeForm').length).toBe(1);
  });

  test('recipe card click renders recipe display', () => {
    const app = mount(<App />);
    expect(app.find('RecipeDisplay').length).toBe(0);
    app.find('RecipeCard').at(0).simulate('click');
    expect(app.find('RecipeDisplay').length).toBe(1);
  });

  test('recipe card renders selected recipe', () => {
    const app = mount(<App />);
    app.find('RecipeCard').at(0).simulate('click');
    const recipeDisplay = app.find('RecipeDisplay');
    const expectedRecipe = recipes[0];
    expect(recipeDisplay.props().name).toBe(expectedRecipe.name);
    expect(recipeDisplay.props().ingredients).toBe(expectedRecipe.ingredients);
    expect(recipeDisplay.props().method).toBe(expectedRecipe.method);
    expect(recipeDisplay.props().image).toBe(expectedRecipe.image);
  });

  describe('new recipe', () => {
    const component = ReactTestUtils.renderIntoDocument(<App />);

    // bring up the add recipe form
    const addRecipeButton = ReactTestUtils.findRenderedDOMComponentWithClass(component, 'icon-btn');
    ReactTestUtils.Simulate.click(addRecipeButton);

    RecipeFormTestUtils.updateName(component, 'test-name');
    RecipeFormTestUtils.updateIngredients(component, 'test-ingredients');
    RecipeFormTestUtils.updateMethod(component, 'test-method');
    RecipeFormTestUtils.updateImage(component, 'test-image');
    RecipeFormTestUtils.submit(component);

    const savedRecipes = JSON.parse(window.localStorage.getItem('recipes'));

    test('is saved to localStorage', () => {
      const expectedRecipe = {
        name: 'test-name',
        ingredients: 'test-ingredients',
        method: 'test-method',
        image: 'test-image',
      };
      const actualRecipe = savedRecipes[savedRecipes.length - 1];
      expect(actualRecipe).toEqual(expectedRecipe);
    });

    test('updated the recipe card list', () => {
      const recipeCards = ReactTestUtils.scryRenderedDOMComponentsWithClass(component, 'recipe-card');
      expect(recipeCards.length).toEqual(savedRecipes.length);
    });
  });

  test('edited recipe is saved', () => {
    const component = ReactTestUtils.renderIntoDocument(<App />);

    // bring up recipe display
    const recipeCard = ReactTestUtils.scryRenderedDOMComponentsWithClass(component, 'recipe-card')[0];
    ReactTestUtils.Simulate.click(recipeCard);

    // bring up edit recipe form
    const editButton = ReactTestUtils.scryRenderedDOMComponentsWithClass(component, 'btn')[0];
    ReactTestUtils.Simulate.click(editButton);

    RecipeFormTestUtils.updateName(component, 'test-name');
    RecipeFormTestUtils.submit(component);

    const savedRecipes = JSON.parse(window.localStorage.getItem('recipes'));
    const actualRecipe = savedRecipes[0];
    expect(actualRecipe.name).toEqual('test-name');
  });

  describe('delete recipe', () => {
    const component = ReactTestUtils.renderIntoDocument(<App />);

    // bring up recipe display
    const recipeCard = ReactTestUtils.scryRenderedDOMComponentsWithClass(component, 'recipe-card')[0];
    ReactTestUtils.Simulate.click(recipeCard);

    const originalNumberOfRecipes = JSON.parse(window.localStorage.getItem('recipes')).length;

    // delete recipe
    const deleteButton = ReactTestUtils.scryRenderedDOMComponentsWithClass(component, 'btn')[1];
    ReactTestUtils.Simulate.click(deleteButton);

    const updatedNumberOfRecipes = JSON.parse(window.localStorage.getItem('recipes')).length;

    test('is deleted from localStorage', () => {
      expect(updatedNumberOfRecipes).toBe(originalNumberOfRecipes - 1);
    });

    test('updated the recipe card list', () => {
      const recipeCards = ReactTestUtils.scryRenderedDOMComponentsWithClass(component, 'recipe-card');
      expect(recipeCards.length).toEqual(updatedNumberOfRecipes);
    });
  });

  test('renders footer', () => {
    const app = shallow(<App />);
    expect(app.find('Footer').length).toBe(1);
  });
});
