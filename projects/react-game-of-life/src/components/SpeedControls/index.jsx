import React from 'react';
import PropTypes from 'prop-types';

import ControlLabel from '../ControlLabel';
import ToggleButton from '../ToggleButton';

const SpeedControls = (props) => {
  const { speed, onChange } = props;

  return (
    <div className="speed-ctrls">
      <ControlLabel text="Speed:" />
      <ToggleButton
        text="Slow"
        onChange={() => onChange(1000)}
        id="slow"
        checked={speed === 1000}
        name="speed"
      />
      <ToggleButton
        text="Medium"
        onChange={() => onChange(500)}
        id="medium"
        checked={speed === 500}
        name="speed"
      />
      <ToggleButton
        text="Fast"
        onChange={() => onChange(300)}
        id="fast"
        checked={speed === 300}
        name="speed"
      />
      <ToggleButton
        text="Fastest"
        onChange={() => onChange(100)}
        id="fastest"
        checked={speed === 100}
        name="speed"
      />
    </div>
  );
};

SpeedControls.propTypes = {
  onChange: PropTypes.func.isRequired,
  speed: PropTypes.number.isRequired,
};

export default SpeedControls;
