import React from 'react';
import PropTypes from 'prop-types';

import ControlLabel from '../ControlLabel';
import ToggleButton from '../ToggleButton';

const BoardSizeControls = (props) => {
  const { columns, rows, onChange } = props;

  return (
    <div className="board-size-ctrls">
      <ControlLabel text="Board Size:" />
      <ToggleButton
        text="10x10"
        onChange={() => onChange(10, 10)}
        id="_10x10"
        checked={columns === 10 && rows === 10}
        name="board-size"
      />
      <ToggleButton
        text="20x20"
        onChange={() => onChange(20, 20)}
        id="_20x20"
        checked={columns === 20 && rows === 20}
        name="board-size"
      />
      <ToggleButton
        text="40x20"
        onChange={() => onChange(20, 40)}
        id="_40x20"
        checked={columns === 40 && rows === 20}
        name="board-size"
      />
    </div>
  );
};

BoardSizeControls.propTypes = {
  onChange: PropTypes.func.isRequired,
  columns: PropTypes.number.isRequired,
  rows: PropTypes.number.isRequired,
};

export default BoardSizeControls;
