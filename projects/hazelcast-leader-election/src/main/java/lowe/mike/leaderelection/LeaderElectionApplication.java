package lowe.mike.leaderelection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for Spring Boot.
 *
 * @author Mike Lowe
 */
@SpringBootApplication
public class LeaderElectionApplication {

  public static void main(String[] args) {
    SpringApplication.run(LeaderElectionApplication.class, args);
  }
}
