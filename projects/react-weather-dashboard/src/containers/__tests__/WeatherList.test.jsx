import React from 'react';
import { mount } from 'enzyme';

import { WeatherList } from '../WeatherList';

const testData = [
  {
    city: {
      name: 'Liverpool',
      coord: {
        lat: 53.4106,
        lon: -2.978,
      },
    },
    list: [
      {
        main: {
          temp: 289.6,
          pressure: 1024.69,
          humidity: 89,
        },
      },
      {
        main: {
          temp: 290,
          pressure: 1000,
          humidity: 70,
        },
      },
    ],
  },
  {
    city: {
      name: 'Manchester',
      coord: {
        lat: 53.4809,
        lon: -2.237,
      },
    },
    list: [
      {
        main: {
          temp: 279.6,
          pressure: 900.69,
          humidity: 91,
        },
      },
      {
        main: {
          temp: 267,
          pressure: 900,
          humidity: 80,
        },
      },
    ],
  },
];

describe('weather list', () => {
  const weatherList = mount(<WeatherList weather={testData} />);

  it('renders weather table', () => {
    expect(weatherList.find('table').length).toBe(1);
    expect(weatherList.find('tbody').find('tr').length).toBe(testData.length);
  });
});
