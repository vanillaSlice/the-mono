import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import FontAwesome from 'react-fontawesome';
import PropTypes from 'prop-types';

import images from '../../images';
import projectDetails from './projects.json';

import './ProjectDetails.css';

const goToProjectsPageIfInvalidName = (props) => {
  if (!projectDetails.projects[props.match.params.name]) {
    props.history.push('/projects');
  }
};

class ProjectDetails extends Component {
  constructor(props) {
    super(props);

    const key = this.props.match.params.name;
    const project = projectDetails.projects[key];

    this.state = {
      key,
      project,
    };
  }

  componentWillMount() {
    goToProjectsPageIfInvalidName(this.props);
  }

  componentWillReceiveProps(nextProps) {
    goToProjectsPageIfInvalidName(nextProps);
  }

  render() {
    if (!this.state.project) {
      return <div className="ProjectDetails" />;
    }

    const { project } = this.state;

    return (
      <div className="ProjectDetails">
        <h1>{project.displayName}</h1>
        <div className="links">
          <div className="left">
            <FontAwesome className="icon" name="arrow-left" tag="i" />
            <Link to="/projects">
              Back to projects
            </Link>
          </div>
          <div className="right">
            {
              project.url &&
              <span>
                <FontAwesome className="icon" name="arrow-right" tag="i" />
                <a href={project.url}>Live</a>
              </span>
            }
            {
              project.repo &&
              <span>
                <FontAwesome className="icon" name="arrow-right" tag="i" />
                <a href={project.repo}>Repo</a>
              </span>
            }
          </div>
        </div>
        <img src={images[this.state.key]} alt={project.displayName} />
        <h2>Description...</h2>
        <p dangerouslySetInnerHTML={{ __html: project.description }} />
        <h2>Tech Used...</h2>
        <p dangerouslySetInnerHTML={{ __html: project.tech }} />
        <h2>What I Learned...</h2>
        <p dangerouslySetInnerHTML={{ __html: project.learned }} />
      </div>
    );
  }
}

ProjectDetails.propTypes = {
  match: PropTypes.objectOf(PropTypes.any).isRequired,
};

export default ProjectDetails;
