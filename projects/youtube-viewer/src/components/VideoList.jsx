import React from 'react';
import PropTypes from 'prop-types';

import VideoListItem from './VideoListItem';

import './VideoList.css';

const VideoList = ({ videos, onVideoSelect }) => (
  <ul className="VideoList">
    {videos.map(video => (
      <VideoListItem
        key={video.etag}
        video={video}
        onVideoSelect={onVideoSelect}
      />
    ))}
  </ul>
);

VideoList.propTypes = {
  videos: PropTypes.arrayOf(PropTypes.object).isRequired,
  onVideoSelect: PropTypes.func.isRequired,
};

export default VideoList;
