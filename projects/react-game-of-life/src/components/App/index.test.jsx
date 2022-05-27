import React from 'react';
import { mount } from 'enzyme';

import App from './index';

describe('App', () => {
  let wrapper;

  beforeEach(() => {
    wrapper = mount(<App />);
  });

  describe('cell click', () => {
    it('should change cell state', () => {
      const cellState = wrapper.state('cells')[0][0].props.alive;
      wrapper.find('.cell').at(0).simulate('click');
      expect(wrapper.state('cells')[0][0].props.alive).not.toBe(cellState);
    });
  });

  describe('play button click', () => {
    it('should set isPlaying to true', () => {
      expect(wrapper.state('isPlaying')).toBe(false);
      wrapper.find('#play').at(1).simulate('click');
      expect(wrapper.state('isPlaying')).toBe(true);
    });

    it('isPlaying should still be true when clicked twice', () => {
      expect(wrapper.state('isPlaying')).toBe(false);
      wrapper.find('#play').at(1).simulate('click');
      expect(wrapper.state('isPlaying')).toBe(true);
      wrapper.find('#play').at(1).simulate('click');
      expect(wrapper.state('isPlaying')).toBe(true);
    });
  });

  describe('pause button click', () => {
    it('should set isPlaying to false', () => {
      wrapper.find('#play').at(1).simulate('click');
      expect(wrapper.state('isPlaying')).toBe(true);
      wrapper.find('#pause').at(1).simulate('click');
      expect(wrapper.state('isPlaying')).toBe(false);
    });

    it('isPlaying should still be false when clicked twice', () => {
      expect(wrapper.state('isPlaying')).toBe(false);
      wrapper.find('#pause').at(1).simulate('click');
      expect(wrapper.state('isPlaying')).toBe(false);
    });
  });

  describe('reset button click', () => {
    it('should set isPlaying to false', () => {
      wrapper.find('#play').at(1).simulate('click');
      expect(wrapper.state('isPlaying')).toBe(true);
      wrapper.find('#reset').at(1).simulate('click');
      expect(wrapper.state('isPlaying')).toBe(false);
    });

    it('should set generation to 0', () => {
      wrapper.find('#play').at(1).simulate('click');
      wrapper.find('#reset').at(1).simulate('click');
      expect(wrapper.state('generation')).toBe(0);
    });
  });

  describe('clear button click', () => {
    it('should set isPlaying to false', () => {
      wrapper.find('#play').at(1).simulate('click');
      expect(wrapper.state('isPlaying')).toBe(true);
      wrapper.find('#clear').at(1).simulate('click');
      expect(wrapper.state('isPlaying')).toBe(false);
    });

    it('should set generation to 0', () => {
      wrapper.find('#play').at(1).simulate('click');
      wrapper.find('#clear').at(1).simulate('click');
      expect(wrapper.state('generation')).toBe(0);
    });
  });

  describe('speed button click', () => {
    it('should set speed', () => {
      expect(wrapper.state('speed')).toBe(500);
      wrapper.find('#fastest').at(1).simulate('change');
      expect(wrapper.state('speed')).toBe(100);
    });

    describe('when not playing', () => {
      it('should not change interval', () => {
        const interval = wrapper.state('interval');
        wrapper.find('#fastest').at(1).simulate('change');
        expect(wrapper.state('interval')).toBe(interval);
      });
    });

    describe('when playing', () => {
      it('should change interval', () => {
        const interval = wrapper.state('interval');
        wrapper.find('#play').at(1).simulate('click');
        wrapper.find('#fastest').at(1).simulate('change');
        expect(wrapper.state('interval')).not.toBe(interval);
      });
    });
  });

  describe('board size click', () => {
    it('should set generation to 0', () => {
      wrapper.find('#_40x20').at(1).simulate('change');
      expect(wrapper.state('generation')).toBe(0);
    });

    it('should change number of cells', () => {
      wrapper.find('#_40x20').at(1).simulate('change');
      const cells = wrapper.state('cells');
      expect(cells.length).toBe(20);
      expect(cells[0].length).toBe(40);
    });

    describe('when not playing', () => {
      it('should not change interval', () => {
        const interval = wrapper.state('interval');
        wrapper.find('#_40x20').at(1).simulate('change');
        expect(wrapper.state('interval')).toBe(interval);
      });
    });

    describe('when playing', () => {
      it('should change interval', () => {
        const interval = wrapper.state('interval');
        wrapper.find('#play').at(1).simulate('click');
        wrapper.find('#_40x20').at(1).simulate('change');
        expect(wrapper.state('interval')).not.toBe(interval);
      });
    });
  });
});
