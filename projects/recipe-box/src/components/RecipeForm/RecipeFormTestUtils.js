import ReactTestUtils from 'react-dom/test-utils';

const RecipeFormTestUtils = (() => {
  const getInputs = component => ReactTestUtils.scryRenderedDOMComponentsWithTag(component, 'input');

  const getTextAreas = component => ReactTestUtils.scryRenderedDOMComponentsWithTag(component, 'textarea');

  return {
    updateName: (component, value) => {
      const name = getInputs(component)[0];
      name.value = value;
      ReactTestUtils.Simulate.change(name);
    },
    updateIngredients: (component, value) => {
      const ingredients = getTextAreas(component)[0];
      ingredients.value = value;
      ReactTestUtils.Simulate.change(ingredients);
    },
    updateMethod: (component, value) => {
      const method = getTextAreas(component)[1];
      method.value = value;
      ReactTestUtils.Simulate.change(method);
    },
    updateImage: (component, value) => {
      const image = getInputs(component)[1];
      image.value = value;
      ReactTestUtils.Simulate.change(image);
    },
    submit: (component) => {
      const recipeForm = ReactTestUtils.findRenderedDOMComponentWithTag(component, 'form');
      ReactTestUtils.Simulate.submit(recipeForm);
    },
    getName: component => getInputs(component)[0].value,
    getIngredients: component => getTextAreas(component)[0].value,
    getMethod: component => getInputs(component)[1].value,
    getImage: component => getTextAreas(component)[1].value,
  };
})();

export default RecipeFormTestUtils;
