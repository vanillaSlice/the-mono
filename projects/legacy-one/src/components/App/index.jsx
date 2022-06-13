import React, { Component } from 'react';
import { BrowserRouter as Router } from 'react-router-dom';

import ScrollToTop from './ScrollToTop';
import Navbar from '../Navbar/';
import Content from '../Content/';

import './index.css';

const SECRET_CODE = 'ArrowUpArrowUpArrowDownArrowDownArrowLeftArrowRightArrowLeftArrowRightba';

class App extends Component {
  constructor(props) {
    super(props);

    this.state = {
      pressed: [],
      showContent: false,
      navbarCollapsed: true,
    };

    this.handleKeyUp = this.handleKeyUp.bind(this);
    this.handleAnimationEnd = this.handleAnimationEnd.bind(this);
    this.handleNavbarToggle = this.handleNavbarToggle.bind(this);
    this.collapseNavbar = this.collapseNavbar.bind(this);
  }

  componentDidMount() {
    document.addEventListener('keyup', this.handleKeyUp);
    this.appElement.addEventListener('animationend', this.handleAnimationEnd);
  }

  componentWillUnmount() {
    document.removeEventListener('keyup', this.handleKeyUp);
    this.appElement.removeEventListener('animationend', this.handleAnimationEnd);
  }

  handleKeyUp(event) {
    // show nicolas cage if secret code is entered
    this.setState((prevState) => {
      let pressed = [...prevState.pressed, event.key];

      // remove uncessary key presses
      pressed.splice(-SECRET_CODE.length - 1, pressed.length - SECRET_CODE.length);

      if (pressed.join('').includes(SECRET_CODE)) {
        window.Cagealicious.add();
        pressed = [];
      }

      return { pressed };
    });
  }

  handleAnimationEnd() {
    this.setState({ showContent: true });
  }

  handleNavbarToggle() {
    this.setState(prevState => ({ navbarCollapsed: !prevState.navbarCollapsed }));
  }

  collapseNavbar() {
    this.setState({ navbarCollapsed: true });
  }

  render() {
    return (
      <Router>
        <ScrollToTop onUpdate={this.collapseNavbar}>
          <div
            className="App"
            ref={(appElement) => { this.appElement = appElement; }}
          >
            <Navbar collapsed={this.state.navbarCollapsed} onToggle={this.handleNavbarToggle} />
            <Content show={this.state.showContent} />
          </div>
        </ScrollToTop>
      </Router>
    );
  }
}

export default App;
