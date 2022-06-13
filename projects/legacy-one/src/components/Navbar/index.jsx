import React from 'react';
import { Link, NavLink } from 'react-router-dom';
import FontAwesome from 'react-fontawesome';
import PropTypes from 'prop-types';

import images from '../../images/';
import cv from '../../files/michael-lowe-cv.pdf';

import './index.css';

const Navbar = props => (
  <nav className={`Navbar ${props.collapsed ? 'collapsed' : 'expanded'}`}>
    <div className="header">
      <Link to="/" className="brand">
        <img className="logo" src={images.logo} alt="Mike Lowe" />
        <span className="name">
          <span className="first">Mike</span> <span className="second">Lowe</span>
        </span>
      </Link>
      <span className="job">
        <span className="first">Software</span> <span className="second">Developer</span>
      </span>
      <button
        type="button"
        className="toggle"
        onClick={props.onToggle}
      >
        <span className="sr-only">Toggle navigation</span>
        <span className="icon-bar top" />
        <span className="icon-bar middle" />
        <span className="icon-bar bottom" />
      </button>
    </div>
    <div className="menu">
      <div className="nav-links">
        <NavLink to="/about" activeClassName="active">
          About<span className="arrow" />
        </NavLink>
        <NavLink to="/projects" activeClassName="active">
          Projects<span className="arrow" />
        </NavLink>
        <a href="https://blog.mikelowe.xyz/">
          Blog<span className="arrow" />
        </a>
        <NavLink to="/contact" activeClassName="active">
          Contact<span className="arrow" />
        </NavLink>
        <a href={cv}>
          CV<span className="arrow" />
        </a>
      </div>
      <div className="social-links">
        <a href="https://github.com/vanillaSlice">
          <FontAwesome className="icon" name="github" tag="i" />
          <span className="sr-only">GitHub</span>
        </a>
        <a href="https://codepen.io/vanillaSlice/">
          <FontAwesome className="icon" name="codepen" tag="i" />
          <span className="sr-only">Codepen</span>
        </a>
        <a href="https://stackoverflow.com/users/8738474/vanillaslice">
          <FontAwesome className="icon" name="stack-overflow" tag="i" />
          <span className="sr-only">Stack Overflow</span>
        </a>
      </div>
    </div>
  </nav>
);

Navbar.propTypes = {
  collapsed: PropTypes.bool.isRequired,
  onToggle: PropTypes.func.isRequired,
};

export default Navbar;
