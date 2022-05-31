package lowe.mike.requestheaderparserapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Entry point for Spring Boot.
 *
 * @author Mike Lowe
 */
@SpringBootApplication
@EnableSwagger2
public class RequestHeaderParserApplication {

  public static void main(String[] args) {
    SpringApplication.run(RequestHeaderParserApplication.class, args);
  }
}
