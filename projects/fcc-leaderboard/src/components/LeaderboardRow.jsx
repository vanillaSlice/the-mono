import React from 'react';
import PropTypes from 'prop-types';

function toPosterImage(poster) {
  return (
    <a className="poster" key={poster.id} href={poster.href}>
      <img
        className="rounded-circle my-1 mr-1"
        src={poster.avatar}
        alt={poster.username}
        title={poster.username}
        height="32"
        width="32"
      />
    </a>
  );
}

const LeaderboardRow = (props) => {
  const {
    activity,
    number,
    posters,
    replies,
    topic,
    views,
  } = props;

  return (
    <tr className="LeaderboardRow">
      <td className="number align-middle">{number}</td>
      <td className="topic align-middle"><a href={topic.href}>{topic.title}</a></td>
      <td className="posters align-middle">{posters.map(poster => toPosterImage(poster))}</td>
      <td className="replies align-middle">{replies}</td>
      <td className="views align-middle">{views}</td>
      <td className="activity align-middle">{activity}</td>
    </tr>
  );
};

LeaderboardRow.propTypes = {
  activity: PropTypes.string.isRequired,
  number: PropTypes.number.isRequired,
  posters: PropTypes.arrayOf(PropTypes.shape({
    avatar: PropTypes.string.isRequired,
    href: PropTypes.string.isRequired,
    id: PropTypes.number.isRequired,
    username: PropTypes.string.isRequired,
  })).isRequired,
  replies: PropTypes.number.isRequired,
  topic: PropTypes.shape({
    href: PropTypes.string.isRequired,
    title: PropTypes.string.isRequired,
  }).isRequired,
  views: PropTypes.number.isRequired,
};

export default LeaderboardRow;
