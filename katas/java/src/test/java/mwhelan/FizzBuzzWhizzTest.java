package mwhelan;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Mike Lowe
 */
public final class FizzBuzzWhizzTest {

  @Test
  public void evaluate_1_returns1() {
    assertEquals("1", FizzBuzzWhizz.evaluate(1));
  }

  @Test
  public void evaluate_2_returnsWhizz() {
    assertEquals("Whizz", FizzBuzzWhizz.evaluate(2));
  }

  @Test
  public void evaluate_3_returnsFizzWhizz() {
    assertEquals("FizzWhizz", FizzBuzzWhizz.evaluate(3));
  }

  @Test
  public void evaluate_4_returns4() {
    assertEquals("4", FizzBuzzWhizz.evaluate(4));
  }

  @Test
  public void evaluate_5_returnsBuzzWhizz() {
    assertEquals("BuzzWhizz", FizzBuzzWhizz.evaluate(5));
  }

  @Test
  public void evaluate_6_returnsFizz() {
    assertEquals("Fizz", FizzBuzzWhizz.evaluate(6));
  }

  @Test
  public void evaluate_7_returnsWhizz() {
    assertEquals("Whizz", FizzBuzzWhizz.evaluate(7));
  }

  @Test
  public void evaluate_8_returns8() {
    assertEquals("8", FizzBuzzWhizz.evaluate(8));
  }

  @Test
  public void evaluate_9_returnsFizz() {
    assertEquals("Fizz", FizzBuzzWhizz.evaluate(9));
  }

  @Test
  public void evaluate_10_returnsBuzz() {
    assertEquals("Buzz", FizzBuzzWhizz.evaluate(10));
  }

  @Test
  public void evaluate_11_returnsWhizz() {
    assertEquals("Whizz", FizzBuzzWhizz.evaluate(11));
  }

  @Test
  public void evaluate_15_returnsFizzBuzz() {
    assertEquals("FizzBuzz", FizzBuzzWhizz.evaluate(15));
  }

  @Test
  public void evaluate_20_returnsBuzz() {
    assertEquals("Buzz", FizzBuzzWhizz.evaluate(20));
  }

  @Test
  public void evaluate_30_returnsFizzBuzz() {
    assertEquals("FizzBuzz", FizzBuzzWhizz.evaluate(30));
  }

}
