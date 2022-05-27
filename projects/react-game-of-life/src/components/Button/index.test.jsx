import React from 'react';
import { shallow } from 'enzyme';

import Button from './index';

describe('Button', () => {
  const onClick = jest.fn();
  const id = 'btn-id';
  const text = 'button text';
  const button = shallow(<Button id={id} onClick={onClick} text={text} />);

  it('renders id', () => {
    expect(button.prop('id')).toBe(id);
  });

  it('is passed onClick function', () => {
    expect(button.props().onClick).toBe(onClick);
  });

  it('renders text', () => {
    expect(button.text()).toBe(text);
  });
});
