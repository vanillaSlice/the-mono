import React from 'react';
import { Switch, Route } from 'react-router-dom';
import PropTypes from 'prop-types';

import Home from '../Home/';
import About from '../About/';
import Projects from '../Projects/';
import ProjectDetails from '../Projects/ProjectDetails';
import Contact from '../Contact/';

import './index.css';

const Content = (props) => {
  // only show content when ready
  if (!props.show) {
    return (
      <div className="Content" />
    );
  }

  return (
    <div className="Content">
      <div className="container">
        <Switch>
          <Route path="/about" component={About} />
          <Route path="/projects/:name" component={ProjectDetails} />
          <Route path="/projects" component={Projects} />
          <Route path="/contact" component={Contact} />
          <Route path="/" component={Home} />
        </Switch>
      </div>
    </div>
  );
};

Content.propTypes = {
  show: PropTypes.bool.isRequired,
};

export default Content;
