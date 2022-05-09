import React from 'react';
import { shallow } from 'enzyme';

import Input from '.';

describe('Input', () => {
  const onChange = jest.fn();
  const input = shallow((
    <Input
      type="url"
      id="test-id"
      value="test-value"
      onChange={onChange}
      required
      autoFocus
    />
  ));

  test('renders type', () => {
    expect(input.props().type).toBe('url');
  });

  test('renders id', () => {
    expect(input.props().id).toBe('test-id');
  });

  test('renders value', () => {
    expect(input.props().value).toBe('test-value');
  });

  test('function is triggered when changed', () => {
    input.simulate('change');
    expect(onChange).toHaveBeenCalledTimes(1);
  });

  test('renders required attribute', () => {
    expect(input.props().required).toBe(true);
  });
});
