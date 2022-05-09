/* eslint-disable
    jsx-a11y/click-events-have-key-events,
    jsx-a11y/no-static-element-interactions
*/

import React from 'react';
import PropTypes from 'prop-types';

import IconButton from '../IconButton';

import './index.css';

const Modal = (props) => {
  const {
    onClose,
    title,
    children,
  } = props;

  return (
    <div className="modal" onClick={onClose}>
      <div className="modal__container">
        <div className="modal__content" onClick={e => e.stopPropagation()}>
          <div className="modal__header">
            <h2 className="modal__title">{title}</h2>
            <IconButton
              onClick={onClose}
              icon="close"
              description="Close modal"
            />
          </div>
          <div className="modal__body">
            {children}
          </div>
        </div>
      </div>
    </div>
  );
};

Modal.propTypes = {
  onClose: PropTypes.func,
  title: PropTypes.string,
  children: PropTypes.node.isRequired,
};

Modal.defaultProps = {
  onClose: null,
  title: '',
};

export default Modal;
