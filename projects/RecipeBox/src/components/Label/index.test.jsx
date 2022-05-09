import React from 'react';
import { shallow } from 'enzyme';

import Label from '.';

describe('Label', () => {
  const text = 'label-text';
  const child = <div />;
  const label = shallow(<Label text={text}>{child}</Label>);

  test('renders text', () => {
    expect(label.text()).toBe('label-text');
  });

  test('renders children', () => {
    expect(label.contains(child)).toBe(true);
  });
});
