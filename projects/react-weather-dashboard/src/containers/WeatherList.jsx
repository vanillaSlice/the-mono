import React, { Component } from 'react';
import { Table } from 'reactstrap';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import _ from 'lodash';

import GoogleMap from '../components/GoogleMap';
import Graph from '../components/Graph';

import './WeatherList.css';

export class WeatherList extends Component {
  renderWeather() {
    const { weather } = this.props;
    return weather.map((w) => {
      const { city, list } = w;
      const { name } = city;
      const { lat, lon } = city.coord;
      const temperatures = list.map(item => item.main.temp);
      const avgTemperature = _.round(_.mean(temperatures), 2);
      const pressures = list.map(item => item.main.pressure);
      const avgPressure = _.round(_.mean(pressures), 2);
      const humidities = list.map(item => item.main.humidity);
      const avgHumidity = _.round(_.mean(humidities), 2);

      return (
        <tr key={name}>
          <td>
            <GoogleMap lat={lat} lng={lon} />
          </td>
          <td>
            <Graph data={temperatures} colour="#4ec9b0" />
            <span className="average">
              avg -&nbsp;
              {avgTemperature}
              &deg;F
            </span>
          </td>
          <td>
            <Graph data={pressures} colour="#ce8c55" />
            <span className="average">
              avg -&nbsp;
              {avgPressure}
              mbar
            </span>
          </td>
          <td>
            <Graph data={humidities} colour="#9cdcf7" />
            <span className="average">
              avg -&nbsp;
              {avgHumidity}
              %
            </span>
          </td>
        </tr>
      );
    });
  }

  render() {
    return (
      <Table className="WeatherList" striped responsive>
        <thead>
          <tr>
            <th>City</th>
            <th>Temperature (&deg;F)</th>
            <th>Pressure (mbar)</th>
            <th>Humidity (%)</th>
          </tr>
        </thead>
        <tbody>
          {this.renderWeather()}
        </tbody>
      </Table>
    );
  }
}

WeatherList.propTypes = {
  weather: PropTypes.arrayOf(PropTypes.object).isRequired,
};

function mapStateToProps({ weather }) {
  return { weather };
}

export default connect(mapStateToProps)(WeatherList);
