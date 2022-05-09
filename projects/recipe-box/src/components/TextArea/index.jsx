import React from 'react';
import PropTypes from 'prop-types';

import './index.css';

const TextArea = (props) => {
  const {
    id,
    value,
    onChange,
    required,
  } = props;

  return (
    <textarea
      className="textarea"
      id={id}
      value={value}
      onChange={onChange}
      required={required}
      autoComplete="false"
    />
  );
};

TextArea.propTypes = {
  id: PropTypes.string.isRequired,
  value: PropTypes.string,
  onChange: PropTypes.func,
  required: PropTypes.bool,
};

TextArea.defaultProps = {
  value: null,
  onChange: null,
  required: false,
};

export default TextArea;
