package lowe.mike.requestheaderparserapp.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

/**
 * {@link ClientDetails} unit tests.
 *
 * @author Mike Lowe
 */
public class ClientDetailsTest {

  private static final String IP_ADDRESS = "127.0.0.1";
  private static final String LANGUAGE = "en";
  private static final String SOFTWARE = "Macintosh; Intel Mac OS X 10_13_4";

  @Test
  public void constructor_nullIpAddress_throwsNullPointerException() {
    NullPointerException exception = assertThrows(NullPointerException.class,
        () -> new ClientDetails(null, LANGUAGE, SOFTWARE));
    assertEquals("ip address is null", exception.getMessage());
  }

  @Test
  public void constructor_nullLanguage_throwsNullPointerException() {
    NullPointerException exception = assertThrows(NullPointerException.class,
        () -> new ClientDetails(IP_ADDRESS, null, SOFTWARE));
    assertEquals("language is null", exception.getMessage());
  }

  @Test
  public void constructor_nullSoftware_throwsNullPointerException() {
    NullPointerException exception = assertThrows(NullPointerException.class,
        () -> new ClientDetails(IP_ADDRESS, LANGUAGE, null));
    assertEquals("software is null", exception.getMessage());
  }

  @Test
  public void equalsAndHashCode() {
    EqualsVerifier.forClass(ClientDetails.class)
        .usingGetClass()
        .verify();
  }
}
