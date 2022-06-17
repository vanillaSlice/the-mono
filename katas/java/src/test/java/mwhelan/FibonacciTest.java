package mwhelan;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Mike Lowe
 */
public final class FibonacciTest {

  @Test(expected = IllegalArgumentException.class)
  public void calculate_minus1_throwsIllegalArgumentException() {
    Fibonacci.calculate(-1);
  }

  @Test
  public void calculate_0_returns0() {
    assertEquals(0, Fibonacci.calculate(0));
  }

  @Test
  public void calculate_1_returns1() {
    assertEquals(1, Fibonacci.calculate(1));
  }

  @Test
  public void calculate_2_returns1() {
    assertEquals(1, Fibonacci.calculate(2));
  }

  @Test
  public void calculate_3_returns2() {
    assertEquals(2, Fibonacci.calculate(3));
  }

  @Test
  public void calculate_4_returns3() {
    assertEquals(3, Fibonacci.calculate(4));
  }

  @Test
  public void calculate_5_returns5() {
    assertEquals(5, Fibonacci.calculate(5));
  }

  @Test
  public void calculate_6_returns8() {
    assertEquals(8, Fibonacci.calculate(6));
  }

  @Test
  public void calculate_7_returns13() {
    assertEquals(13, Fibonacci.calculate(7));
  }

  @Test
  public void calculate_8_returns21() {
    assertEquals(21, Fibonacci.calculate(8));
  }

  @Test
  public void calculate_9_returns34() {
    assertEquals(34, Fibonacci.calculate(9));
  }

  @Test
  public void calculate_30_returns832040() {
    assertEquals(832040, Fibonacci.calculate(30));
  }

}
