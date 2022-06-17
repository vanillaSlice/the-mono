#!/usr/bin/env ruby

require_relative '../lib/fizz_buzz'
require 'test/unit'
 
class TestFizzBuzz < Test::Unit::TestCase

  def test_non_numeric_raises_ArgumentError
    assert_raise(ArgumentError) { fizz_buzz('100') }
  end

  def test_1_returns_1
    assert_equal('1', fizz_buzz(1))
  end

  def test_2_returns_2
    assert_equal('2', fizz_buzz(2))
  end

  def test_3_returns_Fizz
    assert_equal('Fizz', fizz_buzz(3))
  end

  def test_4_returns_4
    assert_equal('4', fizz_buzz(4))
  end

  def test_5_returns_Buzz
    assert_equal('Buzz', fizz_buzz(5))
  end

  def test_6_returns_Fizz
    assert_equal('Fizz', fizz_buzz(6))
  end

  def test_7_returns_7
    assert_equal('7', fizz_buzz(7))
  end

  def test_8_returns_8
    assert_equal('8', fizz_buzz(8))
  end

  def test_9_returns_Fizz
    assert_equal('Fizz', fizz_buzz(9))
  end

  def test_10_returns_Buzz
    assert_equal('Buzz', fizz_buzz(10))
  end

  def test_11_returns_11
    assert_equal('11', fizz_buzz(11))
  end

  def test_12_returns_Fizz
    assert_equal('Fizz', fizz_buzz(12))
  end

  def test_13_returns_13
    assert_equal('13', fizz_buzz(13))
  end

  def test_14_returns_14
    assert_equal('14', fizz_buzz(14))
  end

  def test_15_returns_FizzBuzz
    assert_equal('FizzBuzz', fizz_buzz(15))
  end

  def test_16_returns_16
    assert_equal('16', fizz_buzz(16))
  end

  def test_30_returns_FizzBuzz
    assert_equal('FizzBuzz', fizz_buzz(30))
  end

end
