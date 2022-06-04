/* eslint-disable */

import { configure } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import axios from 'axios';
import MockAdapter from 'axios-mock-adapter';

import testData from './test-data.json';

configure({ adapter: new Adapter() });

new MockAdapter(axios)
  .onGet('https://cors-anywhere.herokuapp.com/https://www.freecodecamp.org/forum/latest.json')
  .reply(200, testData);
