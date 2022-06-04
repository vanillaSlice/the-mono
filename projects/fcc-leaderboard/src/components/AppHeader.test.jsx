import React from 'react';
import { mount } from 'enzyme';

import AppHeader from './AppHeader';

describe('AppHeader', () => {
  it('renders', () => {
    mount(<AppHeader />);
  });
});
