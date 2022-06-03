import React from 'react';
import { Sparklines, SparklinesLine, SparklinesReferenceLine } from 'react-sparklines';
import PropTypes from 'prop-types';

import './Graph.css';

const Graph = (props) => {
  const { colour, data } = props;
  return (
    <div className="Graph">
      <Sparklines data={data}>
        <SparklinesLine color={colour} />
        <SparklinesReferenceLine
          style={{ stroke: '#f44747', strokeDasharray: '2, 2', strokeOpacity: 0.75 }}
          type="mean"
        />
      </Sparklines>
    </div>
  );
};

Graph.propTypes = {
  colour: PropTypes.string.isRequired,
  data: PropTypes.arrayOf(PropTypes.number).isRequired,
};

export default Graph;
