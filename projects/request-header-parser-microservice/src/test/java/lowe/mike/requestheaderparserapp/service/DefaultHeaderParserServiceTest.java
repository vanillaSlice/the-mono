package lowe.mike.requestheaderparserapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import lowe.mike.requestheaderparserapp.model.ClientDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

/**
 * {@link DefaultHeaderParserService} unit tests.
 *
 * @author Mike Lowe
 */
public class DefaultHeaderParserServiceTest {

  private static final String IP_ADDRESS = "127.0.0.1";
  private static final String LOCALE = "en";
  private static final String USER_AGENT = "Macintosh; Intel Mac OS X 10_13_4";

  private final RequestHeaderParserService service = new DefaultHeaderParserService();
  private final HttpServletRequest request = mock(HttpServletRequest.class);

  /**
   * Test setup.
   */
  @BeforeEach
  public void setUp() {
    when(request.getRemoteAddr()).thenReturn(IP_ADDRESS);
    when(request.getLocale()).thenReturn(new Locale(LOCALE));
    when(request.getHeader(HttpHeaders.USER_AGENT)).thenReturn(USER_AGENT);
  }

  @Test
  public void parse_nullRequest_throwsNullPointerException() {
    NullPointerException exception = assertThrows(NullPointerException.class,
        () -> service.parse(null));
    assertEquals("request is null", exception.getMessage());
  }

  @Test
  public void parse_validRequest_returnsClientDetails() {
    ClientDetails actualResponse = service.parse(request);
    ClientDetails expectedResponse = new ClientDetails(IP_ADDRESS, LOCALE, USER_AGENT);

    assertEquals(expectedResponse, actualResponse);
  }
}
