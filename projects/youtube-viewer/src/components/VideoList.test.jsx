import React from 'react';
import { shallow } from 'enzyme';

import VideoList from './VideoList';

describe('VideoList', () => {
  const videos = [
    { etag: 1 },
    { etag: 2 },
    { etag: 3 },
    { etag: 4 },
    { etag: 5 },
  ];
  const onVideoSelect = jest.fn();
  const videoList = shallow(<VideoList videos={videos} onVideoSelect={onVideoSelect} />);

  it('renders video list items', () => {
    expect(videoList.find('VideoListItem').length).toBe(videos.length);
  });
});
