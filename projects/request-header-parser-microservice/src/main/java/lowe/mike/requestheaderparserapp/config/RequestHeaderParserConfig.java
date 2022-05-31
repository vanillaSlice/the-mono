package lowe.mike.requestheaderparserapp.config;

import lowe.mike.requestheaderparserapp.service.DefaultHeaderParserService;
import lowe.mike.requestheaderparserapp.service.RequestHeaderParserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Application config.
 *
 * @author Mike Lowe
 */
@Configuration
public class RequestHeaderParserConfig {

  @Bean
  public RequestHeaderParserService requestHeaderParserService() {
    return new DefaultHeaderParserService();
  }
}
