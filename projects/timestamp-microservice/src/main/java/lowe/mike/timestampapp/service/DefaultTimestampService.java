package lowe.mike.timestampapp.service;

import static java.util.Objects.requireNonNull;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import lowe.mike.timestampapp.model.TimestampResponse;

/**
 * Default implementation of {@link TimestampService} that takes a date string and returns a {@link
 * TimestampResponse}.
 *
 * @author Mike Lowe
 */
public class DefaultTimestampService implements TimestampService {

  private final DateTimeFormatter formatter;

  /**
   * Creates a new {@code DefaultTimestampService}.
   *
   * @param formatter the {@link DateTimeFormatter}
   * @throws NullPointerException if {@code formatter} is {@code null}
   */
  public DefaultTimestampService(DateTimeFormatter formatter) {
    this.formatter = requireNonNull(formatter, "formatter is null");
  }

  @Override
  public TimestampResponse convert(String s) {
    if (s == null) {
      return TimestampResponse.EMPTY;
    }
    return isLong(s) ? parseUnix(s) : parseNatural(s);
  }

  private boolean isLong(String s) {
    try {
      Long.parseLong(s);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  private TimestampResponse parseUnix(String s) {
    long unix = Long.parseLong(s);
    Instant instant = Instant.ofEpochSecond(unix);
    String natural = formatter.format(instant);
    return new TimestampResponse(unix, natural);
  }

  private TimestampResponse parseNatural(String natural) {
    try {
      LocalDate localDate = LocalDate.parse(natural, formatter);
      long unix = localDate.atStartOfDay().toEpochSecond(ZoneOffset.UTC);
      return new TimestampResponse(unix, natural);
    } catch (DateTimeParseException e) {
      return TimestampResponse.EMPTY;
    }
  }
}
