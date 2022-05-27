import React from 'react';
import { shallow } from 'enzyme';

import ControlLabel from './index';

describe('ControlLabel', () => {
  const text = 'control label text';
  const controlLabel = shallow(<ControlLabel text={text} />);

  it('renders text', () => {
    expect(controlLabel.text()).toBe(text);
  });
});
