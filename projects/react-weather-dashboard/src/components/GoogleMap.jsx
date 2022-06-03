import React from 'react';
import GoogleMapReact from 'google-map-react';
import PropTypes from 'prop-types';

const defaultZoom = 10;
const style = {
  height: '7.8125rem',
  margin: '0 auto',
  padding: 0,
  position: 'relative',
  width: '7.8125rem',
};

const GoogleMap = ({ lat, lng }) => (
  <GoogleMapReact
    bootstrapURLKeys={{ key: process.env.REACT_APP_GOOGLE_MAPS_API_KEY }}
    center={{ lat, lng }}
    style={style}
    zoom={defaultZoom}
  />
);

GoogleMap.propTypes = {
  lat: PropTypes.number.isRequired,
  lng: PropTypes.number.isRequired,
};

export default GoogleMap;
