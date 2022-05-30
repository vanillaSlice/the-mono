package lowe.mike.timestampapp.config;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import lowe.mike.timestampapp.service.DefaultTimestampService;
import lowe.mike.timestampapp.service.TimestampService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Application config.
 *
 * @author Mike Lowe
 */
@Configuration
public class TimestampConfig {

  @Bean
  public DateTimeFormatter dateTimeFormatter() {
    return DateTimeFormatter.ofPattern("MMMM dd, yyyy").withZone(ZoneId.systemDefault());
  }

  @Bean
  public TimestampService timestampService() {
    return new DefaultTimestampService(dateTimeFormatter());
  }
}
