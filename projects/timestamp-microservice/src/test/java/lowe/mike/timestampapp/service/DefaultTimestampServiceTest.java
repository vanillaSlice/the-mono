package lowe.mike.timestampapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import lowe.mike.timestampapp.model.TimestampResponse;
import org.junit.jupiter.api.Test;

/**
 * {@link DefaultTimestampService} unit tests.
 *
 * @author Mike Lowe
 */
public class DefaultTimestampServiceTest {

  private final DateTimeFormatter formatter =
      DateTimeFormatter.ofPattern("MMMM dd, yyyy").withZone(ZoneId.systemDefault());
  private final TimestampService service = new DefaultTimestampService(formatter);

  @Test
  public void constructor_nullFormatter_throwsNullPointerException() {
    NullPointerException exception = assertThrows(NullPointerException.class,
        () -> new DefaultTimestampService(null));
    assertEquals("formatter is null", exception.getMessage());
  }

  @Test
  public void convert_withNull_returnsEmptyTimestampResponse() {
    TimestampResponse expected = TimestampResponse.EMPTY;
    TimestampResponse actual = service.convert(null);

    assertEquals(expected, actual);
  }

  @Test
  public void convert_withValidUnixTimestamp_returnsTimestampResponse() {
    TimestampResponse expected = new TimestampResponse(1450137600L, "December 15, 2015");
    TimestampResponse actual = service.convert("1450137600");

    assertEquals(expected, actual);
  }

  @Test
  public void convert_withInvalidUnixTimestamp_returnsEmptyTimestampResponse() {
    TimestampResponse expected = TimestampResponse.EMPTY;
    TimestampResponse actual = service.convert("14501376001450137600");

    assertEquals(expected, actual);
  }

  @Test
  public void convert_withValidNaturalDate_returnsTimestampResponse() {
    TimestampResponse expected = new TimestampResponse(1450137600L, "December 15, 2015");
    TimestampResponse actual = service.convert("December 15, 2015");

    assertEquals(expected, actual);
  }

  @Test
  public void convert_withInvalidNaturalDate_returnsEmptyTimestampResponse() {
    TimestampResponse expected = TimestampResponse.EMPTY;
    TimestampResponse actual = service.convert("December 155 2015");

    assertEquals(expected, actual);
  }
}
