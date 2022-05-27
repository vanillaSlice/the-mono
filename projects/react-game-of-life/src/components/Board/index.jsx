import React from 'react';
import PropTypes from 'prop-types';

import './index.css';

const Board = (props) => {
  const { cells } = props;
  return (
    <div className="board">
      {cells.map((row, i) => (
        <div className="board__row" key={`row-${i + 1}`}>{row}</div>
      ))}
    </div>
  );
};

Board.propTypes = {
  cells: PropTypes.arrayOf(PropTypes.arrayOf(PropTypes.element)).isRequired,
};

export default Board;
