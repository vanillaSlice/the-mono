import { createElement } from 'react';
import { render } from 'react-dom';

import 'bootstrap/dist/css/bootstrap.min.css';

import App from './components/App';

const AppElement = createElement(App);
const rootElement = document.getElementById('root');

render(AppElement, rootElement);
