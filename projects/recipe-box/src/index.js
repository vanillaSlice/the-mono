import { createElement } from 'react';
import { render } from 'react-dom';

import App from './components/App';
import * as serviceWorker from './serviceWorker';

import 'normalize.css/normalize.css';
import 'font-awesome/css/font-awesome.min.css';

const AppElement = createElement(App);
const rootElement = document.getElementById('root');

render(AppElement, rootElement);

serviceWorker.register();
