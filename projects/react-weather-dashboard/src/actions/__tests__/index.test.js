import { FETCH_WEATHER, fetchWeather } from '../index';

jest.mock('axios');

describe('actions', () => {
  describe('#fetchWeather()', () => {
    it('should create an action to fetch weather', () => {
      const expectedResponse = {
        city: {
          name: 'Manchester',
        },
      };

      const action = fetchWeather(expectedResponse.city.name);

      expect(action.type).toBe(FETCH_WEATHER);

      action.payload.then(res => expect(res).toEqual(expectedResponse));
    });
  });
});
