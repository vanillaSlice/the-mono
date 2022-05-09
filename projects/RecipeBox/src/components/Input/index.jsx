import React from 'react';
import PropTypes from 'prop-types';

import './index.css';

const Input = (props) => {
  const {
    type,
    id,
    value,
    onChange,
    required,
  } = props;

  return (
    <input
      className="input"
      type={type}
      id={id}
      value={value}
      onChange={onChange}
      required={required}
      autoComplete="false"
    />
  );
};

Input.propTypes = {
  type: PropTypes.oneOf(['text', 'url']),
  id: PropTypes.string.isRequired,
  value: PropTypes.string,
  onChange: PropTypes.func,
  required: PropTypes.bool,
};

Input.defaultProps = {
  type: 'text',
  value: null,
  onChange: null,
  required: false,
};

export default Input;
