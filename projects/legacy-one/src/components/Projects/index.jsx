import React from 'react';
import { Link } from 'react-router-dom';
import FontAwesome from 'react-fontawesome';

import projectDetails from './projects.json';

import './index.css';

const renderProjects = () => {
  const keys = Object.keys(projectDetails.projects);
  const values = Object.values(projectDetails.projects);

  return values.map((project, index) => {
    const name = keys[index];
    return (
      <div className="project" key={name}>
        <Link className="content" to={`/projects/${name}`}>
          <FontAwesome className="icon" name={project.icon} tag="i" />
          <h2>{project.displayName}</h2>
        </Link>
      </div>
    );
  });
};

const Projects = () => (
  <div className="Projects">
    <h1>Projects</h1>
    <p>Here are some bits and bobs I&apos;ve been working on lately in my spare time:</p>
    <div className="grid">
      {renderProjects()}
    </div>
    <p>
      I built this site using React, the source code can be found <a href="https://github.com/vanillaSlice/the-mono/tree/main/projects/legacy-one">here</a>.
    </p>
  </div>
);

export default Projects;
