import React, { Component } from 'react';
import axios from 'axios';
import {
  Col,
  Container,
  Row,
  Table,
} from 'reactstrap';
import moment from 'moment';

import LeaderboardHeader from './LeaderboardHeader';
import LeaderboardRow from './LeaderboardRow';

const FCC_LATEST_URL = 'https://www.freecodecamp.org/forum/latest.json';

class Leaderboard extends Component {
  constructor(props) {
    super(props);

    this.state = {
      topics: [],
      users: [],
    };

    this.getCamperData = this.getCamperData.bind(this);
    this.topicToRow = this.topicToRow.bind(this);
  }

  componentDidMount() {
    this.getCamperData();
  }

  getCamperData() {
    axios.get(FCC_LATEST_URL)
      .then((res) => {
        this.setState(() => ({
          topics: res.data.topic_list.topics,
          users: res.data.users,
        }));
      })
      .catch(error => window.console.error(error));
  }

  topicToRow(topic, index) {
    const posters = topic.posters.map((poster) => {
      const { users } = this.state;
      const user = users.find(u => u.id === poster.user_id);
      return {
        avatar: `https://www.freecodecamp.org${user.avatar_template}`.replace('{size}', '128'),
        href: `https://www.freecodecamp.org/forum/u/${user.username}`,
        id: user.id,
        username: user.username,
      };
    });

    return (
      <LeaderboardRow
        activity={moment(topic.last_posted_at).fromNow()}
        key={index}
        number={index + 1}
        posters={posters}
        replies={topic.posts_count - 1}
        topic={{
          title: topic.title,
          href: `https://www.freecodecamp.org/forum/t/${topic.slug}/${topic.id}`,
        }}
        views={topic.views}
      />
    );
  }

  render() {
    const { topics } = this.state;

    return (
      <Container className="Leaderboard mt-4 mb-2">
        <Row>
          <Col>
            <Table bordered responsive striped>
              <LeaderboardHeader />
              <tbody>{topics.map((topic, index) => this.topicToRow(topic, index))}</tbody>
            </Table>
          </Col>
        </Row>
      </Container>
    );
  }
}

export default Leaderboard;
