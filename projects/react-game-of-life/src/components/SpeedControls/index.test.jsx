import React from 'react';
import { mount } from 'enzyme';

import SpeedControls from './index';

describe('SpeedControls', () => {
  let onChange;

  beforeEach(() => {
    onChange = jest.fn();
  });

  it('slow is checked when speed is 1000', () => {
    const speedControls = mount(<SpeedControls speed={1000} onChange={onChange} />);
    expect(speedControls.find('#slow').first().props().checked).toBe(true);
  });

  it('onChange is called when slow is clicked', () => {
    const speedControls = mount(<SpeedControls speed={100} onChange={onChange} />);
    speedControls.find('#slow').at(1).simulate('change');
    expect(onChange).toHaveBeenCalledWith(1000);
  });

  it('medium is checked when speed is 500', () => {
    const speedControls = mount(<SpeedControls speed={500} onChange={onChange} />);
    expect(speedControls.find('#medium').first().props().checked).toBe(true);
  });

  it('onChange is called when medium is clicked', () => {
    const speedControls = mount(<SpeedControls speed={1000} onChange={onChange} />);
    speedControls.find('#medium').at(1).simulate('change');
    expect(onChange).toHaveBeenCalledWith(500);
  });

  it('fast is checked when speed is 300', () => {
    const speedControls = mount(<SpeedControls speed={300} onChange={onChange} />);
    expect(speedControls.find('#fast').first().props().checked).toBe(true);
  });

  it('onChange is called when fast is clicked', () => {
    const speedControls = mount(<SpeedControls speed={1000} onChange={onChange} />);
    speedControls.find('#fast').at(1).simulate('change');
    expect(onChange).toHaveBeenCalledWith(300);
  });

  it('fastest is checked when speed is 100', () => {
    const speedControls = mount(<SpeedControls speed={100} onChange={onChange} />);
    expect(speedControls.find('#fastest').first().props().checked).toBe(true);
  });

  it('onChange is called when fastest is clicked', () => {
    const speedControls = mount(<SpeedControls speed={1000} onChange={onChange} />);
    speedControls.find('#fastest').at(1).simulate('change');
    expect(onChange).toHaveBeenCalledWith(100);
  });
});
