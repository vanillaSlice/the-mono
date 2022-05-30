package lowe.mike.timestampapp.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

/**
 * {@link TimestampResponse} unit tests.
 *
 * @author Mike Lowe
 */
public class TimestampResponseTest {

  @Test
  public void equalsAndHashCode() {
    EqualsVerifier.forClass(TimestampResponse.class)
        .usingGetClass()
        .verify();
  }
}
