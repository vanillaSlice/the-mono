import React from 'react';
import { shallow } from 'enzyme';

import Modal from '.';

describe('Modal', () => {
  const onClose = jest.fn();
  const child = (<div />);
  const modal = shallow((
    <Modal onClose={onClose} title="test-title">
      {child}
    </Modal>
  ));

  test('onClose function triggered when clicked outside content area', () => {
    modal.simulate('click');
    expect(onClose).toHaveBeenCalledTimes(1);
  });

  test('renders title', () => {
    expect(modal.find('.modal__title').text()).toBe('test-title');
  });

  describe('close button', () => {
    const closeButton = modal.find('IconButton');

    test('renders', () => {
      expect(closeButton.length).toBe(1);
    });

    test('is given onClose function', () => {
      expect(closeButton.props().onClick).toBe(onClose);
    });
  });

  test('renders children', () => {
    expect(modal.contains(child)).toBe(true);
  });
});
