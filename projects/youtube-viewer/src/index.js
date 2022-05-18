import { createElement } from 'react';
import { render } from 'react-dom';

import 'normalize.css/normalize.css';
import 'font-awesome/css/font-awesome.min.css';

import App from './components/App';
import registerServiceWorker from './registerServiceWorker';

const AppElement = createElement(App, null);
const rootElement = document.getElementById('root');

render(AppElement, rootElement);
registerServiceWorker();
