import React, { Component } from 'react';
import PropTypes from 'prop-types';

import Label from '../Label';
import Input from '../Input';
import TextArea from '../TextArea';
import Button from '../Button';

import './index.css';

class RecipeForm extends Component {
  constructor(props) {
    super(props);

    const {
      name,
      ingredients,
      method,
      image,
    } = this.props;

    this.state = {
      name,
      ingredients,
      method,
      image,
      saved: false,
    };

    this.onSubmit = this.onSubmit.bind(this);
    this.onChange = this.onChange.bind(this);
    this.onClear = this.onClear.bind(this);
  }

  onSubmit(e) {
    e.preventDefault();

    const {
      name,
      ingredients,
      method,
      image,
    } = this.state;
    const { onSave } = this.props;

    // save this recipe
    onSave({
      name,
      ingredients,
      method,
      image,
    });

    // reset form and note that we've saved recipe
    this.setState({
      name: '',
      ingredients: '',
      method: '',
      image: '',
      saved: true,
    });
  }

  onChange(e) {
    const { id, value } = e.target;
    this.setState({
      [id]: value,
      saved: false,
    });
  }

  onClear() {
    this.setState({
      name: '',
      ingredients: '',
      method: '',
      image: '',
      saved: false,
    });
  }

  render() {
    const {
      name,
      ingredients,
      method,
      image,
      saved,
    } = this.state;

    return (
      <form className="recipe-form" onSubmit={this.onSubmit}>
        <Label text="Recipe name *">
          <Input
            id="name"
            value={name}
            onChange={this.onChange}
            required
          />
        </Label>
        <Label text="Ingredients *">
          <TextArea
            id="ingredients"
            value={ingredients}
            onChange={this.onChange}
            required
          />
        </Label>
        <Label text="Method *">
          <TextArea
            id="method"
            value={method}
            onChange={this.onChange}
            required
          />
        </Label>
        <Label text="Image URL (optional)">
          <Input
            type="url"
            id="image"
            value={image}
            onChange={this.onChange}
          />
        </Label>
        <Button id="save" type="submit" text="Save" />
        <Button id="clear" text="Clear" onClick={this.onClear} />
        {saved && <span className="recipe-form__save-message">Saved recipe!</span>}
      </form>
    );
  }
}

RecipeForm.propTypes = {
  name: PropTypes.string,
  ingredients: PropTypes.string,
  method: PropTypes.string,
  image: PropTypes.string,
  onSave: PropTypes.func,
};

RecipeForm.defaultProps = {
  name: '',
  ingredients: '',
  method: '',
  image: '',
  onSave: null,
};

export default RecipeForm;
