#!/usr/bin/env ruby

require_relative '../lib/prime_numbers'
require 'test/unit'
 
class TestPrimeNumbers < Test::Unit::TestCase

  def test_1_returns_false
    assert_equal(false, 1.is_prime?)
  end

  def test_2_returns_true
    assert_equal(true, 2.is_prime?)
  end

  def test_3_returns_true
    assert_equal(true, 3.is_prime?)
  end

  def test_4_returns_false
    assert_equal(false, 4.is_prime?)
  end

  def test_5_returns_true
    assert_equal(true, 5.is_prime?)
  end

  def test_6_returns_false
    assert_equal(false, 6.is_prime?)
  end

  def test_7_returns_true
    assert_equal(true, 7.is_prime?)
  end

  def test_8_returns_false
    assert_equal(false, 8.is_prime?)
  end

  def test_9_returns_false
    assert_equal(false, 9.is_prime?)
  end

  def test_10_returns_false
    assert_equal(false, 10.is_prime?)
  end

  def test_11_returns_true
    assert_equal(true, 11.is_prime?)
  end

  def test_197_returns_true
    assert_equal(true, 197.is_prime?)
  end

  def test_1223_returns_true
    assert_equal(true, 1223.is_prime?)
  end

  def test_1224_returns_false
    assert_equal(false, 1224.is_prime?)
  end

end
