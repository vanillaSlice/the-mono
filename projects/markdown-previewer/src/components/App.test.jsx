import React from 'react';
import { mount } from 'enzyme';

import App from './App';

describe('App', () => {
  it('renders markdown', () => {
    const wrapper = mount(<App />);
    const editor = wrapper.find('textarea.Editor');
    const preview = wrapper.find('.Preview .card-body');

    editor.simulate('change', { target: { value: '# Heading 1\n## Heading 2\nParagraph' } });
    expect(preview.text()).toBe('Heading 1\nHeading 2\nParagraph\n');

    editor.simulate('change', { target: { value: '# Updated' } });
    expect(preview.text()).toBe('Updated\n');
  });
});
