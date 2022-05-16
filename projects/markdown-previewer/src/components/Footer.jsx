import React from 'react';
import { Container } from 'reactstrap';

import { version } from '../../package.json';

const AppFooter = () => (
  <footer className="Footer mb-3">
    <Container className="text-right text-muted">
      <small>
        v
        { version }
        &nbsp;|&nbsp;
        <a href="https://github.com/vanillaSlice/MarkdownPreviewer">GitHub</a>
        &nbsp;| Made with 🎧 by&nbsp;
        <a href="https://mikelowe.xyz">Mike</a>
      </small>
    </Container>
  </footer>
);

export default AppFooter;
