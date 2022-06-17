#!/usr/bin/env ruby

require_relative '../lib/fizz_buzz_whizz'
require 'test/unit'
 
class TestFizzBuzzWhizz < Test::Unit::TestCase

  def test_non_numeric_raises_ArgumentError
    assert_raise(ArgumentError) { fizz_buzz_whizz('100') }
  end

  def test_1_returns_1
    assert_equal('1', fizz_buzz_whizz(1))
  end

  def test_2_returns_Whizz
    assert_equal('Whizz', fizz_buzz_whizz(2))
  end

  def test_3_returns_FizzWhizz
    assert_equal('FizzWhizz', fizz_buzz_whizz(3))
  end

  def test_4_returns_4
    assert_equal('4', fizz_buzz_whizz(4))
  end

  def test_5_returns_BuzzWhizz
    assert_equal('BuzzWhizz', fizz_buzz_whizz(5))
  end

  def test_6_returns_Fizz
    assert_equal('Fizz', fizz_buzz_whizz(6))
  end

  def test_7_returns_Whizz
    assert_equal('Whizz', fizz_buzz_whizz(7))
  end

  def test_8_returns_8
    assert_equal('8', fizz_buzz_whizz(8))
  end

  def test_9_returns_Fizz
    assert_equal('Fizz', fizz_buzz_whizz(9))
  end

  def test_10_returns_Buzz
    assert_equal('Buzz', fizz_buzz_whizz(10))
  end

  def test_11_returns_Whizz
    assert_equal('Whizz', fizz_buzz_whizz(11))
  end

  def test_12_returns_Fizz
    assert_equal('Fizz', fizz_buzz_whizz(12))
  end

  def test_13_returns_Whizz
    assert_equal('Whizz', fizz_buzz_whizz(13))
  end

  def test_14_returns_14
    assert_equal('14', fizz_buzz_whizz(14))
  end

  def test_15_returns_FizzBuzz
    assert_equal('FizzBuzz', fizz_buzz_whizz(15))
  end

  def test_16_returns_16
    assert_equal('16', fizz_buzz_whizz(16))
  end

  def test_30_returns_FizzBuzz
    assert_equal('FizzBuzz', fizz_buzz_whizz(30))
  end

end
