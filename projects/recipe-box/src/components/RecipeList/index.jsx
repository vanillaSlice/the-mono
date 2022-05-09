import React from 'react';

import RecipeCard from '../RecipeCard';

import './index.css';

const renderRowsOfRecipeCards = (props) => {
  // map recipes to recipe cards
  const recipeCards = props.recipes.map((recipe, index) => (
    <RecipeCard
      key={`recipe-${index + 1}`}
      name={recipe.name}
      image={recipe.image}
      onClick={() => props.onClick(recipe)}
    />
  ));

  // organise into rows of 3
  const rows = recipeCards.reduce((_rows, recipeCard, index) => {
    if (index % 3 === 0) {
      _rows.push([recipeCard]);
    } else {
      _rows[Math.floor(index / 3)].push(recipeCard);
    }
    return _rows;
  }, []);

  // wrap inside row divs
  const rowDivs = rows.map((row, index) => (
    <div className="recipe-list__row" key={`row-${index + 1}`}>
      {row}
    </div>
  ));

  return rowDivs;
};

const RecipeList = props => (
  <div className="recipe-list">
    {renderRowsOfRecipeCards(props)}
  </div>
);

export default RecipeList;
