import React from 'react';
import { mount } from 'enzyme';

import LeaderboardRow from './LeaderboardRow';

describe('LeaderboardRow', () => {
  const activity = '16 minutes ago';
  const number = 1;
  const posters = [
    {
      avatar: 'https://www.freecodecamp.org/forum/user_avatar/www.freecodecamp.org/iamanudeep/32/110425_2.png',
      href: 'https://www.freecodecamp.org/forum/u/iamanudeep',
      id: 184107,
      username: 'iamanudeep',
    },
    {
      avatar: 'https://www.freecodecamp.org/forum/user_avatar/www.freecodecamp.org/ethanvernon/32/110429_2.png',
      href: 'https://www.freecodecamp.org/forum/u/ethanvernon',
      id: 184989,
      username: 'ethanvernon',
    },
  ];
  const replies = 4;
  const topic = {
    href: 'How to add mail?',
    title: 'https://www.freecodecamp.org/forum/t/how-to-add-mail/262472',
  };
  const views = 100;

  const wrapper = mount(
    <table>
      <tbody>
        <LeaderboardRow
          activity={activity}
          number={number}
          posters={posters}
          replies={replies}
          topic={topic}
          views={views}
        />
      </tbody>
    </table>,
  );

  it('renders number', () => {
    expect(wrapper.find('.number').text()).toBe(`${number}`);
  });

  it('renders topic', () => {
    const link = wrapper.find('.topic a');
    expect(link.props().href).toBe(topic.href);
    expect(link.text()).toBe(topic.title);
  });

  it('renders posters', () => {
    const postersElement = wrapper.find('.poster');

    expect(posters.length).toBe(postersElement.length);

    posters.forEach((poster, index) => {
      const posterLink = postersElement.at(index);
      const posterImg = posterLink.find('img');
      expect(posterLink.props().href).toBe(poster.href);
      expect(posterImg.props().src).toBe(poster.avatar);
      expect(posterImg.props().alt).toBe(poster.username);
      expect(posterImg.props().title).toBe(poster.username);
    });
  });

  it('renders replies', () => {
    expect(wrapper.find('.replies').text()).toBe(`${replies}`);
  });

  it('renders views', () => {
    expect(wrapper.find('.views').text()).toBe(`${views}`);
  });

  it('renders activity', () => {
    expect(wrapper.find('.activity').text()).toBe(activity);
  });
});
