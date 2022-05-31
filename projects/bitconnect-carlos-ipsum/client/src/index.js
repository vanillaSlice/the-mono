import { createElement } from 'react';
import { render } from 'react-dom';

import '../node_modules/papercss/dist/paper.min.css';

import App from './App';

const AppElement = createElement(App);
const rootElement = document.getElementById('root');

render(AppElement, rootElement);
