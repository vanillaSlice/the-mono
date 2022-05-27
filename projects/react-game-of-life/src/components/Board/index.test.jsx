import React from 'react';
import { mount } from 'enzyme';

import Cell from '../Cell';
import Board from './index';

describe('Board', () => {
  it('renders rows', () => {
    const cells = [];

    for (let i = 0; i < 3; i += 1) {
      const row = [];
      for (let j = 0; j < 3; j += 1) {
        row.push(<Cell key={`cell-${i}-${j}`} onClick={jest.fn()} emoji="💩" alive />);
      }
      cells.push(row);
    }

    const wrapper = mount(<Board cells={cells} />);

    expect(wrapper.find('.board__row').length).toBe(3);
  });
});
