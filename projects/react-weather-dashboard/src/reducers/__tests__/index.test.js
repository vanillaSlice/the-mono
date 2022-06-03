import rootReducer from '../index';

describe('root reducer', () => {
  it('stores weather state', () => {
    expect(rootReducer().weather).toEqual([]);
  });
});
