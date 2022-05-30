package lowe.mike.timestampapp.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

/**
 * {@link ErrorResponse} unit tests.
 *
 * @author Mike Lowe
 */
public class ErrorResponseTest {

  @Test
  public void equalsAndHashCode() {
    EqualsVerifier.forClass(ErrorResponse.class)
        .usingGetClass()
        .verify();
  }
}
