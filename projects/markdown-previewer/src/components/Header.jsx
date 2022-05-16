import React from 'react';
import { Container, Navbar, NavbarBrand } from 'reactstrap';

export default () => (
  <Navbar color="dark" dark>
    <Container>
      <NavbarBrand href=".">Markdown Previewer</NavbarBrand>
    </Container>
  </Navbar>
);
