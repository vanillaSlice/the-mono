import React from 'react';
import { Container, Navbar, NavbarBrand } from 'reactstrap';

import AppFooter from './AppFooter';
import SearchBar from '../containers/SearchBar';
import WeatherList from '../containers/WeatherList';

import './App.css';

export default () => (
  <div className="App">
    <Navbar color="dark" dark expand="sm" fixed="top">
      <Container>
        <NavbarBrand href=".">React Weather Dashboard</NavbarBrand>
      </Container>
    </Navbar>
    <Container>
      <SearchBar />
      <WeatherList />
    </Container>
    <AppFooter />
  </div>
);
