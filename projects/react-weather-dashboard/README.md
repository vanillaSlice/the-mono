# React Weather Dashboard

[![Latest Release](https://img.shields.io/github/release/vanillaSlice/ReactWeatherDashboard.svg)](https://github.com/vanillaSlice/ReactWeatherDashboard/releases/latest)
[![Build Status](https://img.shields.io/travis/com/vanillaSlice/ReactWeatherDashboard/master.svg)](https://travis-ci.com/vanillaSlice/ReactWeatherDashboard)
[![Coverage Status](https://img.shields.io/coveralls/github/vanillaSlice/ReactWeatherDashboard/master.svg)](https://coveralls.io/github/vanillaSlice/ReactWeatherDashboard?branch=master)
[![License](https://img.shields.io/github/license/vanillaSlice/ReactWeatherDashboard.svg)](LICENSE)

A simple weather dashboard written using [React](https://reactjs.org/) and [Redux](https://redux.js.org/).
A deployed version can be viewed [here](http://reactweatherdashboard.mikelowe.xyz/).

## Screenshot

![Screenshot](/images/screenshot-1.png)

## Getting Started

### Prerequisites

* [npm](https://www.npmjs.com/)

### API Keys

Create a copy of the file `.env.local-example` and call it `.env.local`. We'll add the API keys to this file.

#### OpenWeatherMap API Key

This app requires an API key for OpenWeatherMap. Go [here](https://openweathermap.org/) to find out how to get one.
Set the `REACT_APP_OPEN_WEATHER_MAP_API_KEY` variable in `.env.local`.

#### Google Maps API Key

This app requires an API key for the Google Maps JavaScript API. Go
[here](https://developers.google.com/maps/documentation/javascript/get-api-key) to find out how to get one.
Set the `REACT_APP_GOOGLE_MAPS_API_KEY` variable in `.env.local`.

### Installing Dependencies

From your terminal/command prompt run:

```
npm install
```

### Running

From your terminal/command prompt run:

```
npm start
```

Point your browser to [localhost:3000](http://localhost:3000).

## Technology Used

For those of you that are interested, the technology used in this project includes:

* [Bootstrap 4](https://getbootstrap.com/docs/4.0/getting-started/introduction/)
* [React](https://reactjs.org/)
* [Redux](https://redux.js.org/)
* [Jest](https://jestjs.io/) (Testing)

## Useful Links

Resources useful for the completion of this project:

* [Create React App](https://github.com/facebook/create-react-app) (React starter kit)
* [OpenWeatherMap](https://openweathermap.org/) (used for weather data)
* [Google Maps JavaScript API](https://developers.google.com/maps/documentation/javascript/get-api-key)
(used for displaying maps)

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
