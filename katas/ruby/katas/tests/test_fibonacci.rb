#!/usr/bin/env ruby

require_relative '../lib/fibonacci'
require 'test/unit'
 
class TestFibonacci < Test::Unit::TestCase
 
  def test_negative_raises_ArgumentError
    assert_raise(ArgumentError) { fibonacci(-1) }
  end

  def test_non_numeric_raises_ArgumentError
    assert_raise(ArgumentError) { fibonacci('100') }
  end

  def test_0_returns_0
    assert_equal(0, fibonacci(0))
  end

  def test_1_returns_1
    assert_equal(1, fibonacci(1))
  end

  def test_2_returns_1
    assert_equal(1, fibonacci(2))
  end

  def test_3_returns2
    assert_equal(2, fibonacci(3))
  end

  def test_4_returns_3
    assert_equal(3, fibonacci(4))
  end
 
  def test_40_returns_102334155
    assert_equal(102334155, fibonacci(40))
  end

  def test_100_returns_354224848179261915075
    assert_equal(354224848179261915075, fibonacci(100))
  end

end
