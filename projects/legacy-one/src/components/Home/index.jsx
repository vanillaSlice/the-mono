import React, { Component } from 'react';
import Typed from 'typed.js';

import './index.css';

const options = {
  strings: [
    '^2500Hi! ^125My name is <strong>Mike</strong>.&nbsp;',
    'I&apos;m a software developer based in <strong>Manchester</strong>.&nbsp;',
    'Have a look around.&nbsp;<br />^250Check out my <a href="./projects"><span>work</span></a>.&nbsp;<br />^250Feel free to get <a href="./contact">in touch</a>.&nbsp;<br />^500:)&nbsp;'],
  typeSpeed: 40,
  backDelay: 1000,
  backSpeed: 20,
};

class Home extends Component {
  constructor(props) {
    super(props);

    this.state = {};

    this.initTyped = this.initTyped.bind(this);
    this.destroyTyped = this.destroyTyped.bind(this);
  }

  componentDidMount() {
    this.initTyped();
  }

  componentWillUnmount() {
    this.destroyTyped();
  }

  initTyped() {
    this.setState({ typed: new Typed(this.typedElement, options) });
  }

  destroyTyped() {
    this.state.typed.destroy();
  }

  render() {
    return (
      <div className="Home">
        <p className="message">
          <span ref={(typedElement) => { this.typedElement = typedElement; }} />
        </p>
      </div>
    );
  }
}

export default Home;
