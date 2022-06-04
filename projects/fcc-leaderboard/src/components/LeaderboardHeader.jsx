import React from 'react';

const LeaderboardHeader = () => (
  <thead className="LeaderboardHeader">
    <tr>
      <th className="number align-middle">#</th>
      <th className="topic align-middle">Topic</th>
      <th className="posters align-middle">Posters</th>
      <th className="replies align-middle">Replies</th>
      <th className="views align-middle">Views</th>
      <th className="activity align-middle">Activity</th>
    </tr>
  </thead>
);

export default LeaderboardHeader;
