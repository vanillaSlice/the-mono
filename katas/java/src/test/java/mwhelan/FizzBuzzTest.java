package mwhelan;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Mike Lowe
 */
public final class FizzBuzzTest {

  @Test
  public void evaluate_1_returns1() {
    assertEquals("1", FizzBuzz.evaluate(1));
  }

  @Test
  public void evaluate_2_returns2() {
    assertEquals("2", FizzBuzz.evaluate(2));
  }

  @Test
  public void evaluate_3_returnsFizz() {
    assertEquals("Fizz", FizzBuzz.evaluate(3));
  }

  @Test
  public void evaluate_5_returnsBuzz() {
    assertEquals("Buzz", FizzBuzz.evaluate(5));
  }

  @Test
  public void evaluate_6_returnsFizz() {
    assertEquals("Fizz", FizzBuzz.evaluate(6));
  }

  @Test
  public void evaluate_10_returnsBuzz() {
    assertEquals("Buzz", FizzBuzz.evaluate(10));
  }

  @Test
  public void evaluate_15_returnsFizzBuzz() {
    assertEquals("FizzBuzz", FizzBuzz.evaluate(15));
  }

  @Test
  public void evaluate_30_returnsFizzBuzz() {
    assertEquals("FizzBuzz", FizzBuzz.evaluate(30));
  }

}
