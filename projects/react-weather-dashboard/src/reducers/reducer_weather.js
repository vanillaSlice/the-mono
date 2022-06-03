import _ from 'lodash';

import { FETCH_WEATHER } from '../actions';

export default function (state = [], action = {}) {
  switch (action.type) {
    case FETCH_WEATHER:
      return _.compact([...state, action.payload.data]);
    default:
      return state;
  }
}
