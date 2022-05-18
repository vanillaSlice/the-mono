import React from 'react';
import PropTypes from 'prop-types';

import './VideoDetails.css';

const DATE_OPTIONS = {
  year: 'numeric',
  month: 'short',
  day: 'numeric',
};

const formatDate = str => new Date(str).toLocaleDateString('en-UK', DATE_OPTIONS);

const VideoDetails = ({ video }) => (
  <div className="VideoDetails">
    <div className="iframe-wrapper">
      <iframe
        type="text/html"
        width="640"
        height="360"
        title={video.snippet.title}
        allowFullScreen
        src={`https://www.youtube.com/embed/${video.id.videoId}`}
      />
    </div>
    <h2 className="title">{video.snippet.title}</h2>
    <a
      className="channel"
      href={`https://www.youtube.com/channel/${video.snippet.channelId}`}
    >
      {video.snippet.channelTitle}
    </a>
    <p className="published-date">
      Published on&nbsp;
      {formatDate(video.snippet.publishedAt)}
    </p>
    <p className="description">{video.snippet.description}</p>
  </div>
);

VideoDetails.propTypes = {
  video: PropTypes.objectOf(PropTypes.any).isRequired,
};

export default VideoDetails;
