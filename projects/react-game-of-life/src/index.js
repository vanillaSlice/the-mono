import { createElement } from 'react';
import { render } from 'react-dom';

import App from './components/App';

import '../node_modules/normalize.css/normalize.css';

import './index.css';

render(createElement(App), document.getElementById('root'));
