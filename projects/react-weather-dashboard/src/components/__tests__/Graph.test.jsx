import React from 'react';
import { shallow } from 'enzyme';
import { Sparklines, SparklinesLine, SparklinesReferenceLine } from 'react-sparklines';

import Graph from '../Graph';

describe('graph', () => {
  const colour = 'green';
  const data = [1, 2, 3, 4, 5];
  const graph = shallow(<Graph colour={colour} data={data} />);

  it('renders sparklines with data', () => {
    expect(graph.find(Sparklines).props().data).toEqual(data);
  });

  it('renders sparkline with colour', () => {
    expect(graph.find(SparklinesLine).props().color).toEqual(colour);
  });

  it('renders reference line', () => {
    expect(graph.find(SparklinesReferenceLine).length).toBe(1);
  });
});
