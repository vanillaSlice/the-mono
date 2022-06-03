import React from 'react';
import { shallow } from 'enzyme';
import GoogleMapReact from 'google-map-react';

import GoogleMap from '../GoogleMap';

describe('GoogleMap', () => {
  const lat = 10;
  const lng = 250;
  const googleMap = shallow(<GoogleMap lat={lat} lng={lng} />);

  it('renders map with latitude and longitude', () => {
    expect(googleMap.find(GoogleMapReact).props().center).toEqual({ lat, lng });
  });
});
