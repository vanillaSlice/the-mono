/* eslint-disable
    jsx-a11y/label-has-for,
    jsx-a11y/label-has-associated-control
*/

import React from 'react';
import PropTypes from 'prop-types';

import './index.css';

const Label = (props) => {
  const { text, children } = props;
  return (
    <label className="label">
      {text}
      {children}
    </label>
  );
};

Label.propTypes = {
  text: PropTypes.string,
  children: PropTypes.node.isRequired,
};

Label.defaultProps = {
  text: '',
};

export default Label;
