import React, { Component } from 'react';

import Header from '../Header';
import RecipeList from '../RecipeList';
import Modal from '../Modal';
import RecipeForm from '../RecipeForm';
import RecipeDisplay from '../RecipeDisplay';
import Footer from '../Footer';

import data from '../../data/recipes.json';

import './index.css';

class App extends Component {
  constructor(props) {
    super(props);

    this.state = {
      recipes: JSON.parse(localStorage.getItem('recipes')) || data.recipes,
      selectedRecipe: {},
      displayModal: false,
      mode: '',
    };

    this.handleAddRecipeClick = this.handleAddRecipeClick.bind(this);
    this.handleRecipeCardClick = this.handleRecipeCardClick.bind(this);
    this.handleEditClick = this.handleEditClick.bind(this);
    this.handleDeleteClick = this.handleDeleteClick.bind(this);
    this.handleCloseModalClick = this.handleCloseModalClick.bind(this);
    this.renderModal = this.renderModal.bind(this);
    this.saveRecipe = this.saveRecipe.bind(this);
    this.updateRecipe = this.updateRecipe.bind(this);
  }

  handleAddRecipeClick() {
    this.setState({
      selectedRecipe: {},
      displayModal: true,
      mode: 'Add',
    });
  }

  handleRecipeCardClick(recipe) {
    this.setState({
      selectedRecipe: recipe,
      displayModal: true,
      mode: 'Display',
    });
  }

  handleEditClick() {
    this.setState({
      displayModal: true,
      mode: 'Edit',
    });
  }

  handleDeleteClick() {
    this.setState((prevState) => {
      const { selectedRecipe } = this.state;
      const recipes = [...prevState.recipes].filter(recipe => recipe !== selectedRecipe);
      localStorage.setItem('recipes', JSON.stringify(recipes));
      return { recipes, displayModal: false };
    });
  }

  handleCloseModalClick() {
    this.setState({
      selectedRecipe: {},
      displayModal: false,
      mode: '',
    });
  }

  updateRecipe(recipe) {
    this.setState((prevState) => {
      const recipes = [...prevState.recipes];
      const { selectedRecipe } = this.state;
      const index = recipes.findIndex(e => e === selectedRecipe);
      recipes[index] = recipe;
      localStorage.setItem('recipes', JSON.stringify(recipes));
      return { recipes, selectedRecipe: recipe, mode: 'Display' };
    });
  }

  saveRecipe(recipe) {
    this.setState((prevState) => {
      const recipes = [...prevState.recipes];
      recipes.push(recipe);
      localStorage.setItem('recipes', JSON.stringify(recipes));
      return { recipes };
    });
  }

  renderModal() {
    const {
      mode,
      selectedRecipe,
    } = this.state;

    if (mode === 'Display') {
      return (
        <Modal
          onClose={this.handleCloseModalClick}
          title={selectedRecipe.name}
        >
          <RecipeDisplay
            {...selectedRecipe}
            onEdit={this.handleEditClick}
            onDelete={this.handleDeleteClick}
          />
        </Modal>
      );
    }

    if (mode === 'Add') {
      return (
        <Modal onClose={this.handleCloseModalClick} title="Add recipe">
          <RecipeForm onSave={this.saveRecipe} />
        </Modal>
      );
    }

    if (mode === 'Edit') {
      return (
        <Modal onClose={this.handleCloseModalClick} title="Edit recipe">
          <RecipeForm
            {...selectedRecipe}
            onSave={this.updateRecipe}
          />
        </Modal>
      );
    }

    return <Modal />;
  }

  render() {
    const { recipes, displayModal } = this.state;

    return (
      <div className="app">
        <Header onAdd={this.handleAddRecipeClick} />
        <RecipeList
          recipes={recipes}
          onClick={this.handleRecipeCardClick}
        />
        {displayModal && this.renderModal()}
        <Footer />
      </div>
    );
  }
}

export default App;
