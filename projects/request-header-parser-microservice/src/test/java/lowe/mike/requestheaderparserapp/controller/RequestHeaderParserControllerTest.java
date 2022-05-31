package lowe.mike.requestheaderparserapp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * {@link RequestHeaderParserController} unit tests.
 *
 * @author Mike Lowe
 */
public class RequestHeaderParserControllerTest {

  @Test
  public void constructor_nullRequestHeaderParserService_throwsNullPointerException() {
    NullPointerException exception = assertThrows(NullPointerException.class,
        () -> new RequestHeaderParserController(null));
    assertEquals("request header parser service is null", exception.getMessage());
  }
}
