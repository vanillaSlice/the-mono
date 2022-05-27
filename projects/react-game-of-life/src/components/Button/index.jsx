import React from 'react';
import PropTypes from 'prop-types';

import './index.css';

const Button = (props) => {
  const { id, onClick, text } = props;

  return (
    <button id={id} className="btn" type="button" onClick={onClick}>
      {text}
    </button>
  );
};

Button.propTypes = {
  id: PropTypes.string.isRequired,
  onClick: PropTypes.func.isRequired,
  text: PropTypes.string.isRequired,
};

export default Button;
