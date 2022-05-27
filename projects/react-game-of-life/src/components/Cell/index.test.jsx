import React from 'react';
import { mount } from 'enzyme';

import Cell from './index';

describe('Cell', () => {
  const onClick = jest.fn();
  const emoji = '💩';

  it('is passed onClick function', () => {
    const cell = mount(<Cell onClick={onClick} emoji={emoji} />);
    expect(cell.props().onClick).toBe(onClick);
  });

  it('renders emoji if alive', () => {
    const cell = mount(<Cell onClick={onClick} alive emoji={emoji} />);
    expect(cell.find('.cell__emoji').text()).toBe(emoji);
  });

  it('does not render emoji if dead', () => {
    const cell = mount(<Cell onClick={onClick} emoji={emoji} />);
    expect(cell.find('.cell__emoji').exists()).toBe(false);
  });
});
