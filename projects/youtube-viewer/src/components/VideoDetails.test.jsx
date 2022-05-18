import React from 'react';
import { shallow } from 'enzyme';

import VideoDetails from './VideoDetails';

describe('VideoDetails', () => {
  const video = {
    id: {
      videoId: 'EIyixC9NsLI',
    },
    snippet: {
      title: 'Badgers : animated music video : MrWeebl',
      channelId: 'UC9wk6mlzKKN3_sVMPd2p-3Q',
      channelTitle: 'Weebl\'s Stuff',
      description: 'badger badger badger badger',
      publishedAt: '2008-06-28T09:34:55.000Z',
    },
  };
  const videoDetails = shallow(<VideoDetails video={video} />);
  const iframe = videoDetails.find('iframe');
  const channel = videoDetails.find('.channel');

  it('iframe renders title', () => {
    expect(iframe.props().title).toBe(video.snippet.title);
  });

  it('iframe renders src', () => {
    const expectedSrc = `https://www.youtube.com/embed/${video.id.videoId}`;
    expect(iframe.props().src).toBe(expectedSrc);
  });

  it('renders title', () => {
    expect(videoDetails.find('.title').text()).toBe(video.snippet.title);
  });

  it('renders published date', () => {
    expect(videoDetails.find('.published-date').text()).toBe('Published on\u00a0Jun 28, 2008');
  });

  it('channel link renders title', () => {
    expect(channel.text()).toBe(video.snippet.channelTitle);
  });

  it('channel link renders href', () => {
    const expectedHref = `https://www.youtube.com/channel/${video.snippet.channelId}`;
    expect(channel.props().href).toBe(expectedHref);
  });

  it('renders description', () => {
    expect(videoDetails.find('.description').text()).toBe(video.snippet.description);
  });
});
