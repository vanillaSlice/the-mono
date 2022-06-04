import React from 'react';
import { mount } from 'enzyme';
import moment from 'moment';

import Leaderboard from './Leaderboard';

describe('Leaderboard', () => {
  const wrapper = mount(<Leaderboard />);

  it('renders rows', () => {
    wrapper.update();

    const rows = wrapper.find('.LeaderboardRow');
    expect(rows.length).toBe(2);

    const row1 = rows.at(0);
    expect(row1.find('.number').text()).toBe('1');

    const row1Link = row1.find('.topic a');
    expect(row1Link.props().href).toBe('https://www.freecodecamp.org/forum/t/feedback-portfolio-internationalization/262374');
    expect(row1Link.text()).toBe('Feedback Portfolio - Internationalization?');

    const row1Posters = row1.find('.poster');
    expect(row1Posters.length).toBe(3);

    const row1Poster1Link = row1Posters.at(0);
    const row1Poster1Img = row1Poster1Link.find('img');
    expect(row1Poster1Link.props().href).toBe('https://www.freecodecamp.org/forum/u/enjoymrban');
    expect(row1Poster1Img.props().src).toBe('https://www.freecodecamp.org/forum/user_avatar/www.freecodecamp.org/enjoymrban/128/105914_2.png');
    expect(row1Poster1Img.props().alt).toBe('enjoymrban');
    expect(row1Poster1Img.props().title).toBe('enjoymrban');

    const row1Poster2Link = row1Posters.at(1);
    const row1Poster2Img = row1Poster2Link.find('img');
    expect(row1Poster2Link.props().href).toBe('https://www.freecodecamp.org/forum/u/yoelvis');
    expect(row1Poster2Img.props().src).toBe('https://www.freecodecamp.org/forum/user_avatar/www.freecodecamp.org/yoelvis/128/99065_2.png');
    expect(row1Poster2Img.props().alt).toBe('yoelvis');
    expect(row1Poster2Img.props().title).toBe('yoelvis');

    const row1Poster3Link = row1Posters.at(2);
    const row1Poster3Img = row1Poster3Link.find('img');
    expect(row1Poster3Link.props().href).toBe('https://www.freecodecamp.org/forum/u/kevinSmith');
    expect(row1Poster3Img.props().src).toBe('https://www.freecodecamp.org/forum/user_avatar/www.freecodecamp.org/kevinsmith/128/72934_2.png');
    expect(row1Poster3Img.props().alt).toBe('kevinSmith');
    expect(row1Poster3Img.props().title).toBe('kevinSmith');

    expect(row1.find('.replies').text()).toBe('4');
    expect(row1.find('.views').text()).toBe('30');
    expect(row1.find('.activity').text()).toBe(moment('2019-03-03T12:54:56.070Z').fromNow());

    const row2 = rows.at(1);
    expect(row2.find('.number').text()).toBe('2');

    const row2Link = row2.find('.topic a');
    expect(row2Link.props().href).toBe('https://www.freecodecamp.org/forum/t/image-grid-within-a-section-please-help/262479');
    expect(row2Link.text()).toBe('Image Grid within a section-Please help');

    const row2Posters = row2.find('.poster');
    expect(row2Posters.length).toBe(1);

    const row2Poster1Link = row2Posters.at(0);
    const row2Poster1Img = row2Poster1Link.find('img');
    expect(row2Poster1Link.props().href).toBe('https://www.freecodecamp.org/forum/u/RobbieC');
    expect(row2Poster1Img.props().src).toBe('https://www.freecodecamp.org/forum/user_avatar/www.freecodecamp.org/robbiec/128/111208_2.png');
    expect(row2Poster1Img.props().alt).toBe('RobbieC');
    expect(row2Poster1Img.props().title).toBe('RobbieC');

    expect(row2.find('.replies').text()).toBe('0');
    expect(row2.find('.views').text()).toBe('2');
    expect(row2.find('.activity').text()).toBe(moment('2019-03-03T12:49:24.557Z').fromNow());
  });
});
