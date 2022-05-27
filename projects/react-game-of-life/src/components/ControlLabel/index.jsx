import React from 'react';
import PropTypes from 'prop-types';

import './index.css';

const ControlLabel = (props) => {
  const { text } = props;

  return (
    <span className="ctrl-label">{text}</span>
  );
};

ControlLabel.propTypes = {
  text: PropTypes.string.isRequired,
};

export default ControlLabel;
