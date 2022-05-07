package lowe.mike.leaderelection.election;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.leader.Candidate;
import org.springframework.integration.support.leader.LockRegistryLeaderInitiator;
import org.springframework.integration.support.locks.LockRegistry;

/**
 * Leader election config.
 *
 * @author Mike Lowe
 */
@Configuration
public class LeaderElectionConfig {

  private static final Logger logger = LoggerFactory.getLogger(LeaderElectionConfig.class);

  /**
   * Leader initiator.
   */
  @Bean
  public LockRegistryLeaderInitiator leaderInitiator(LockRegistry lockRegistry,
      Candidate candidate) {
    logger.info("Creating LockRegistryLeaderInitiator");

    return new LockRegistryLeaderInitiator(lockRegistry, candidate);
  }
}
