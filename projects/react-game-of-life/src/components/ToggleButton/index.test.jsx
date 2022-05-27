import React from 'react';
import { mount } from 'enzyme';

import ToggleButton from './index';

describe('ToggleButton', () => {
  const id = 'toggle-button-id';
  const name = 'toggle-button-name';
  const onChange = jest.fn();
  const checked = true;
  const text = 'toggle button text';
  const toggleButton = mount(<ToggleButton
    id={id}
    name={name}
    onChange={onChange}
    checked={checked}
    text={text}
  />);

  it('renders id', () => {
    expect(toggleButton.props().id).toBe(id);
  });

  it('renders name', () => {
    expect(toggleButton.props().name).toBe(name);
  });

  it('is passed onChange function', () => {
    expect(toggleButton.props().onChange).toBe(onChange);
  });

  it('renders checked', () => {
    expect(toggleButton.props().checked).toBe(checked);
  });

  it('renders text', () => {
    expect(toggleButton.text()).toBe(text);
  });
});
