import React from 'react';
import { shallow } from 'enzyme';

import Button from '.';

describe('Button', () => {
  const onClick = jest.fn();
  const button = shallow((
    <Button
      type="submit"
      buttonStyle="danger"
      onClick={onClick}
      text="test-text"
    />
  ));

  test('renders type', () => {
    expect(button.props().type).toBe('submit');
  });

  test('renders style class', () => {
    expect(button.hasClass('btn--danger')).toBe(true);
  });

  test('function is triggered when clicked', () => {
    button.simulate('click');
    expect(onClick).toHaveBeenCalledTimes(1);
  });

  test('renders text', () => {
    expect(button.text()).toBe('test-text');
  });
});
