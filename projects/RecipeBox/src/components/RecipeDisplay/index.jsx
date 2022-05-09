import React from 'react';
import PropTypes from 'prop-types';

import Button from '../Button';

import './index.css';

const RecipeDisplay = (props) => {
  const {
    image,
    name,
    ingredients,
    method,
    onEdit,
    onDelete,
  } = props;

  return (
    <div className="recipe-display">
      <div className="recipe-display__row">
        <img
          className="recipe-display__img"
          src={image}
          alt={name}
          title={name}
        />
        <div className="recipe-display__ingredients">
          <h3 className="recipe-display__title">Ingredients</h3>
          <p className="recipe-display__text">{ingredients}</p>
        </div>
      </div>
      <div className="recipe-display__method">
        <h3 className="recipe-display__title">Method</h3>
        <p className="recipe-display__text">{method}</p>
      </div>
      <Button onClick={onEdit} text="Edit" />
      <Button buttonStyle="danger" onClick={onDelete} text="Delete" />
    </div>
  );
};

RecipeDisplay.propTypes = {
  image: PropTypes.string,
  name: PropTypes.string,
  ingredients: PropTypes.string,
  method: PropTypes.string,
  onEdit: PropTypes.func,
  onDelete: PropTypes.func,
};

RecipeDisplay.defaultProps = {
  image: '',
  name: '',
  ingredients: '',
  method: '',
  onEdit: null,
  onDelete: null,
};

export default RecipeDisplay;
