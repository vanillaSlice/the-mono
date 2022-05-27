import React from 'react';
import PropTypes from 'prop-types';

import Button from '../Button';

const MainControls = (props) => {
  const {
    onPlayButtonClick,
    onPauseButtonClick,
    onResetButtonClick,
    onClearButtonClick,
  } = props;

  return (
    <div className="main-ctrls">
      <Button id="play" text="Play" onClick={onPlayButtonClick} />
      <Button id="pause" text="Pause" onClick={onPauseButtonClick} />
      <Button id="reset" text="Reset" onClick={onResetButtonClick} />
      <Button id="clear" text="Clear" onClick={onClearButtonClick} />
    </div>
  );
};

MainControls.propTypes = {
  onPlayButtonClick: PropTypes.func.isRequired,
  onPauseButtonClick: PropTypes.func.isRequired,
  onResetButtonClick: PropTypes.func.isRequired,
  onClearButtonClick: PropTypes.func.isRequired,
};

export default MainControls;
