package lowe.mike.timestampapp.service;

import lowe.mike.timestampapp.model.TimestampResponse;

/**
 * Interface that takes a date string and returns a {@link TimestampResponse}.
 *
 * @author Mike Lowe
 */
public interface TimestampService {

  /**
   * Takes a date string and returns a {@link TimestampResponse}.
   *
   * @param date a date string
   * @return the {@link TimestampResponse}
   */
  TimestampResponse convert(String date);
}
