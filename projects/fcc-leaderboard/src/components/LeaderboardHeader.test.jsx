import React from 'react';
import { mount } from 'enzyme';

import LeaderboardHeader from './LeaderboardHeader';

describe('LeaderboardHeader', () => {
  it('renders', () => {
    mount(
      <table>
        <LeaderboardHeader />
      </table>,
    );
  });
});
