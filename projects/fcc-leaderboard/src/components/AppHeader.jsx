import React from 'react';
import { Container, Navbar, NavbarBrand } from 'reactstrap';

import logo from './freecodecamp_logo.svg';

import './AppHeader.scss';

const AppHeader = () => (
  <Navbar className="AppHeader">
    <Container>
      <NavbarBrand href="https://www.freecodecamp.org/">
        <img src={logo} alt="freeCodeCamp logo" title="freeCodeCamp logo" />
      </NavbarBrand>
    </Container>
  </Navbar>
);

export default AppHeader;
