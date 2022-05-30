package lowe.mike.timestampapp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * {@link TimestampController} unit tests.
 *
 * @author Mike Lowe
 */
public class TimestampControllerTest {

  @Test
  public void constructor_nullTimestampService_throwsNullPointerException() {
    NullPointerException exception = assertThrows(NullPointerException.class,
        () -> new TimestampController(null));
    assertEquals("timestamp service is null", exception.getMessage());
  }
}
