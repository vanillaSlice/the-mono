import React from 'react';
import { mount } from 'enzyme';

import MainControls from './index';

describe('MainControls', () => {
  const onPlayButtonClick = jest.fn();
  const onPauseButtonClick = jest.fn();
  const onResetButtonClick = jest.fn();
  const onClearButtonClick = jest.fn();
  const mainControls = mount(<MainControls
    onPlayButtonClick={onPlayButtonClick}
    onPauseButtonClick={onPauseButtonClick}
    onResetButtonClick={onResetButtonClick}
    onClearButtonClick={onClearButtonClick}
  />);

  it('play button is passed function', () => {
    expect(mainControls.find('.btn').get(0).props.onClick).toBe(onPlayButtonClick);
  });

  it('pause button is passed function', () => {
    expect(mainControls.find('.btn').get(1).props.onClick).toBe(onPauseButtonClick);
  });

  it('reset button is passed function', () => {
    expect(mainControls.find('.btn').get(2).props.onClick).toBe(onResetButtonClick);
  });

  it('clear button is passed function', () => {
    expect(mainControls.find('.btn').get(3).props.onClick).toBe(onClearButtonClick);
  });
});
