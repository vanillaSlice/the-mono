package mwhelan;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * @author Mike Lowe
 */
@RunWith(Parameterized.class)
public final class LeapYearTest {

  @Parameterized.Parameters
  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][]{
        {0, true},
        {4, true},
        {8, true},
        {1600, true},
        {1700, false},
        {1800, false},
        {1900, false},
        {1996, true},
        {2000, true},
        {2001, false},
        {2004, true},
        {2100, false},
        {2200, false},
        {2300, false}
    });
  }

  private final int year;
  private final boolean isLeapYear;

  public LeapYearTest(final int year, final boolean isLeapYear) {
    this.year = year;
    this.isLeapYear = isLeapYear;
  }

  @Test
  public void isLeapYear() {
    assertEquals(isLeapYear, LeapYear.isLeapYear(year));
  }

}
