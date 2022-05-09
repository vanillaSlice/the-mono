import React from 'react';
import { shallow } from 'enzyme';

import TextArea from '.';

describe('TextArea', () => {
  const onChange = jest.fn();
  const textArea = shallow((
    <TextArea
      id="test-id"
      value="test-value"
      onChange={onChange}
      required
    />
  ));

  test('renders id', () => {
    expect(textArea.props().id).toBe('test-id');
  });

  test('renders value', () => {
    expect(textArea.props().value).toBe('test-value');
  });

  test('triggers function on change', () => {
    textArea.simulate('change');
    expect(onChange).toHaveBeenCalledTimes(1);
  });

  test('renders required attribute', () => {
    expect(textArea.props().required).toBe(true);
  });
});
