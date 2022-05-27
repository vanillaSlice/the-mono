import React from 'react';
import PropTypes from 'prop-types';

import './index.css';

const ToggleButton = (props) => {
  const {
    id,
    name,
    onChange,
    checked,
    text,
  } = props;

  return (
    <label className="toggle-btn" htmlFor={id}>
      <input
        className="toggle-btn__input"
        type="radio"
        id={id}
        name={name}
        onChange={onChange}
        checked={checked}
      />
      <span className="toggle-btn__text">
        {text}
      </span>
    </label>
  );
};

ToggleButton.propTypes = {
  id: PropTypes.string.isRequired,
  name: PropTypes.string.isRequired,
  onChange: PropTypes.func.isRequired,
  checked: PropTypes.bool,
  text: PropTypes.string.isRequired,
};

ToggleButton.defaultProps = {
  checked: false,
};

export default ToggleButton;
