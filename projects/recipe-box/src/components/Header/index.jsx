import React from 'react';
import PropTypes from 'prop-types';

import IconButton from '../IconButton';

import './index.css';

const Header = (props) => {
  const { onAdd } = props;

  return (
    <header className="header">
      <div className="header__content">
        <h1 className="header__heading"><a href=".">Recipe Box</a></h1>
        <IconButton
          buttonStyle="light"
          onClick={onAdd}
          icon="plus"
          description="Add new recipe"
        />
      </div>
    </header>
  );
};

Header.propTypes = {
  onAdd: PropTypes.func.isRequired,
};

export default Header;
