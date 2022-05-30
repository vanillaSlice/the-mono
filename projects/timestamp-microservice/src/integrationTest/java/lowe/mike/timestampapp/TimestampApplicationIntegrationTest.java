package lowe.mike.timestampapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * {@link TimestampApplication} integration tests.
 *
 * @author Mike Lowe
 */
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class TimestampApplicationIntegrationTest {

  @Autowired
  private Environment environment;

  @Autowired
  private TestRestTemplate template;

  @Test
  public void convert_returnsTimestampResponse() {
    ResponseEntity<String> response = template.getForEntity("/convert/1450137600", String.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("{\"unix\":1450137600,\"natural\":\"December 15, 2015\"}", response.getBody());
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
