import WeatherReducer from '../reducer_weather';
import { FETCH_WEATHER } from '../../actions';

describe('weather reducer', () => {
  it('should return the initial state', () => {
    expect(WeatherReducer()).toEqual([]);
  });

  it('should handle FETCH_WEATHER', () => {
    const action = {
      type: FETCH_WEATHER,
      payload: {
        data: 'test-data',
      },
    };
    expect(WeatherReducer([], action)).toEqual(['test-data']);
  });

  it('should ignore other actions', () => {
    const action = { type: 'other' };
    expect(WeatherReducer([], action)).toEqual([]);
  });
});
