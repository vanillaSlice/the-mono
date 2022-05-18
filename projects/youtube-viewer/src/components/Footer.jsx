import React from 'react';

import { version } from '../../package.json';

import './Footer.css';

const Footer = () => (
  <footer className="Footer">
    <div className="text-right text-muted">
      <small className="small">
        v
        { version }
        &nbsp;|&nbsp;
        <a className="link" href="https://github.com/vanillaSlice/the-mono/tree/main/projects/youtube-viewer">GitHub</a>
        &nbsp;| Made with 🎧 by&nbsp;
        <a className="link" href="https://mikelowe.xyz">Mike</a>
      </small>
    </div>
  </footer>
);

export default Footer;
