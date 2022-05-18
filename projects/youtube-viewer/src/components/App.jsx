import React, { Component } from 'react';
import YTSearch from 'youtube-api-search';

import Footer from './Footer';
import Header from './Header';
import VideoDetails from './VideoDetails';
import VideoList from './VideoList';

import './App.css';

class App extends Component {
  constructor(props) {
    super(props);

    this.state = { videos: [] };

    this.handleSearch = this.handleSearch.bind(this);
    this.handleVideoSelect = this.handleVideoSelect.bind(this);
  }

  handleSearch(term) {
    YTSearch({ key: process.env.REACT_APP_API_KEY, term }, (videos) => {
      this.setState({
        videos,
        selectedVideo: videos[0],
      });
    });
  }

  handleVideoSelect(selectedVideo) {
    this.setState({ selectedVideo });
  }

  render() {
    const { selectedVideo, videos } = this.state;

    return (
      <div className="App">
        <Header onSearch={this.handleSearch} />
        <div className="container">
          {selectedVideo && <VideoDetails video={selectedVideo} />}
          <VideoList videos={videos} onVideoSelect={this.handleVideoSelect} />
        </div>
        <Footer />
      </div>
    );
  }
}

export default App;
