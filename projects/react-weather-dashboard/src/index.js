import { createElement } from 'react';
import { render } from 'react-dom';
import { Provider } from 'react-redux';
import { applyMiddleware, createStore } from 'redux';
import promiseMiddleware from 'redux-promise';

import 'bootstrap/dist/css/bootstrap.min.css';
import 'font-awesome/css/font-awesome.min.css';

import App from './components/App';
import reducers from './reducers';

const store = createStore(reducers, applyMiddleware(promiseMiddleware));
const rootElement = document.getElementById('root');
const ProviderElement = createElement(Provider, { store }, createElement(App));

render(ProviderElement, rootElement);
