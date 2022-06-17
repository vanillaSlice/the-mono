#!/usr/bin/env ruby

require_relative '../lib/leap_year'
require 'test/unit'
 
class TestLeapYear < Test::Unit::TestCase

  def test_non_numeric_raises_ArgumentError
    assert_raise(ArgumentError) { is_leap_year?('100') }
  end

  def test_4_returns_true
    assert_equal(true, is_leap_year?(4))
  end

  def test_8_returns_true
    assert_equal(true, is_leap_year?(8))
  end

  def test_100_returns_false
    assert_equal(false, is_leap_year?(100))
  end

  def test_200_returns_false
    assert_equal(false, is_leap_year?(200))
  end

  def test_400_returns_true
    assert_equal(true, is_leap_year?(400))
  end

  def test_1995_returns_false
    assert_equal(false, is_leap_year?(1995))
  end

  def test_1996_returns_true
    assert_equal(true, is_leap_year?(1996))
  end

  def test_2000_returns_true
    assert_equal(true, is_leap_year?(2000))
  end

  def test_2100_returns_false
    assert_equal(false, is_leap_year?(2100))
  end

  def test_2103_returns_false
    assert_equal(false, is_leap_year?(2103))
  end

end
