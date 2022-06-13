import { createElement } from 'react';
import { render } from 'react-dom';

import '../node_modules/normalize.css/normalize.css';
import '../node_modules/font-awesome/css/font-awesome.min.css';

import App from './components/App/';

import './index.css';

const AppElement = createElement(App);
const rootElement = document.getElementById('root');

render(AppElement, rootElement);
