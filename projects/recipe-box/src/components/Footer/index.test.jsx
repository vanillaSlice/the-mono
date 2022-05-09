import React from 'react';
import { shallow } from 'enzyme';

import Footer from '.';

import { version } from '../../../package.json';

describe('Footer', () => {
  const footer = shallow(<Footer />);

  test('renders version', () => {
    expect(footer.find('.footer__version').text()).toBe(version);
  });
});
