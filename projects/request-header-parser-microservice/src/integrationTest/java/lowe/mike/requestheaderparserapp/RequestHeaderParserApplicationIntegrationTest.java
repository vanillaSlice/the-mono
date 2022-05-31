package lowe.mike.requestheaderparserapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import lowe.mike.requestheaderparserapp.model.ClientDetails;
import lowe.mike.requestheaderparserapp.service.RequestHeaderParserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * {@link RequestHeaderParserApplication} integration tests.
 *
 * @author Mike Lowe
 */
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class RequestHeaderParserApplicationIntegrationTest {

  @Autowired
  private Environment environment;

  @Autowired
  private TestRestTemplate template;

  @MockBean
  private RequestHeaderParserService service;

  private static final String IP_ADDRESS = "127.0.0.1";
  private static final String LANGUAGE = "en";
  private static final String SOFTWARE = "Macintosh; Intel Mac OS X 10_13_4";

  @BeforeEach
  public void setUp() {
    when(service.parse(any())).thenReturn(new ClientDetails(IP_ADDRESS, LANGUAGE, SOFTWARE));
  }

  @Test
  public void parse_returnsClientDetails() {
    ResponseEntity<String> response = template.getForEntity("/parse", String.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("{"
        + "\"ipAddress\":\"" + IP_ADDRESS + "\","
        + "\"language\":\"" + LANGUAGE + "\","
        + "\"software\":\"" + SOFTWARE + "\""
        + "}", response.getBody());
  }

  @Test
  public void swaggerUI_returnsSwaggerDocs() {
    ResponseEntity<String> response = template.getForEntity("/swagger-ui.html", String.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void home_redirectsToSwaggerDocs() {
    ResponseEntity<String> response = template.getForEntity("/", String.class);
    assertEquals(HttpStatus.FOUND, response.getStatusCode());
    assertEquals("/swagger-ui.html", response.getHeaders().getLocation().getPath());
  }

  @Test
  public void notFound404_returnsErrorResponse() {
    ResponseEntity<String> response = template.getForEntity("/not-found", String.class);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("{\"status\":404,\"message\":\"Not Found\"}", response.getBody());
  }
}
