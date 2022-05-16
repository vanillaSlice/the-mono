import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { Input } from 'reactstrap';

class Editor extends Component {
  constructor(props) {
    super(props);
    this.handleChange = this.handleChange.bind(this);
  }

  handleChange(event) {
    const { value } = event.target;
    const { onChange } = this.props;
    onChange(value);
  }

  render() {
    const { input } = this.props;

    return (
      <Input
        className="Editor mb-3"
        rows="30"
        cols="50"
        value={input}
        onChange={this.handleChange}
        type="textarea"
      />
    );
  }
}

Editor.propTypes = {
  onChange: PropTypes.func.isRequired,
  input: PropTypes.string.isRequired,
};

export default Editor;
