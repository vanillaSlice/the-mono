import React from 'react';

import { version } from '../../../package.json';

import './index.css';

const Footer = () => (
  <footer className="footer">
    <div className="footer__content">
      <small>
        v
        <span className="footer__version">{ version }</span>
        &nbsp;|&nbsp;
        <a href="https://github.com/vanillaSlice/RecipeBox">GitHub</a>
        &nbsp;| Made with 🎧 by&nbsp;
        <a href="https://mikelowe.xyz">Mike</a>
      </small>
    </div>
  </footer>
);

export default Footer;
