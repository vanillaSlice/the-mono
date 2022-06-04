import { createElement } from 'react';
import { render } from 'react-dom';

import App from './components/App';
import * as serviceWorker from './serviceWorker';

import 'bootstrap/dist/css/bootstrap.css';

import './index.scss';

const AppElement = createElement(App);
const rootElement = document.getElementById('root');

render(AppElement, rootElement);

serviceWorker.register();
