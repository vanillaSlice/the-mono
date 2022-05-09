import React from 'react';
import { shallow } from 'enzyme';

import IconButton from '.';

describe('IconButton', () => {
  const onClick = jest.fn();
  const iconButton = shallow((
    <IconButton
      buttonStyle="light"
      onClick={onClick}
      icon="close"
      description="test-description"
    />
  ));

  test('renders style class', () => {
    expect(iconButton.hasClass('icon-btn--light')).toBe(true);
  });

  test('function is triggered when clicked', () => {
    iconButton.simulate('click');
    expect(onClick).toHaveBeenCalledTimes(1);
  });

  test('renders font awesome icon', () => {
    expect(iconButton.find('FontAwesome').props().name).toBe('close');
  });

  test('renders description', () => {
    expect(iconButton.find('span').text()).toBe('test-description');
  });
});
